package com.yinhetianze.systemservice.user.service.busi.impl;

import com.yinhetianze.systemservice.role.service.busi.SysUserRoleBusiService;
import com.yinhetianze.systemservice.role.service.info.SysUserRoleInfoService;
import com.yinhetianze.systemservice.user.mapper.busi.SysOptorBusiMapper;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import com.yinhetianze.systemservice.user.service.busi.SysOptorBusiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysOptorBusiServiceImpl implements SysOptorBusiService
{
    @Autowired
    private SysOptorBusiMapper sysOptorBusiMapper;

    @Autowired
    private SysUserRoleBusiService sysUserRoleBusiServiceImpl;

    @Autowired
    private SysUserRoleInfoService sysUserRoleInfoServiceImpl;

    @Override
    public int addInfo(BusiSysOptorModel busiSysOptorModel)throws BusinessException {
        try {
            BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
            BeanUtils.copyProperties(busiSysOptorModel, busiSysOptorPojo);
            setDefaultLoginPassword(busiSysOptorPojo);
            sysOptorBusiMapper.insertSelective(busiSysOptorPojo);

            String[] roleIdsArr = busiSysOptorModel.getRoleIds().split("\\,");
            for (String role : roleIdsArr) {
                BusiSysUserRolePojo busiSysUserRolePojo = new BusiSysUserRolePojo();
                busiSysUserRolePojo.setOptorId(busiSysOptorPojo.getId());
                busiSysUserRolePojo.setRoleId(Integer.parseInt(role));
                sysUserRoleBusiServiceImpl.addInfo(busiSysUserRolePojo);
            }
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new BusinessException("该账号已存在!");
            }
        }
        return 1;
    }


    @Override
    public int updateInfo(BusiSysOptorModel busiSysOptorModel) throws BusinessException {
        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        BeanUtils.copyProperties(busiSysOptorModel,busiSysOptorPojo);
        busiSysOptorPojo.setUpdateTime(new Date());

        if(busiSysOptorModel.getResetPassword() == 0){
            setDefaultLoginPassword(busiSysOptorPojo);
        }
        sysOptorBusiMapper.updateByPrimaryKeySelective(busiSysOptorPojo);

        BusiSysUserRolePojo busiSysUserRolePojo = new BusiSysUserRolePojo();
        busiSysUserRolePojo.setOptorId(busiSysOptorPojo.getId());
        //查询用户已有的角色关系
        List<BusiSysUserRolePojo> list = sysUserRoleInfoServiceImpl.select(busiSysUserRolePojo);
        String roleIds  = ","+busiSysOptorModel.getRoleIds()+",";
        //toDelete 找出 数据库中有 而提交参数没有的  角色信息   这些信息属于要删除的
        List<BusiSysUserRolePojo> toDelete = list.stream().filter(userRolePojo->{
            if(roleIds.indexOf( ","+userRolePojo.getRoleId()+",")>=0){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        if(CommonUtil.isNotEmpty(toDelete)){
            toDelete.stream().forEach(userRolePojo->{
                sysUserRoleBusiServiceImpl.deleteInfo(userRolePojo.getId());
            });
            list.removeAll(toDelete);
        }

        //toAdd 找出 数据库中没有 而提交参数有的  角色信息   这些信息属于要新增的
        List<String> toAdd = Arrays.stream(busiSysOptorModel.getRoleIds().split("\\,")).filter(id->{
            if(list.isEmpty())
                return true;
            return !list.stream().anyMatch(userRolePojo->{
                return (userRolePojo.getRoleId() == Integer.parseInt(id));
            });
        }).collect(Collectors.toList());

        if(CommonUtil.isNotEmpty(toAdd)){
            for(String id:toAdd){
                BusiSysUserRolePojo add = new BusiSysUserRolePojo();
                add.setOptorId(busiSysOptorPojo.getId());
                add.setRoleId(Integer.parseInt(id));
                sysUserRoleBusiServiceImpl.addInfo(add);
            }
        }
        return 1;
    }

    @Override
    public int deleteInfo(Integer id) throws BusinessException {
        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        busiSysOptorPojo.setDelFlag((short)1);
        busiSysOptorPojo.setId(id);
        int result = sysOptorBusiMapper.updateByPrimaryKeySelective(busiSysOptorPojo);
        if(result <= 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        result = sysUserRoleBusiServiceImpl.deleteByCondition(id,null);
        //if(result <= 0){
        //    throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        //}
        return 1;
    }

    @Override
    public int updateByPrimaryKeySelective(BusiSysOptorPojo busiSysOptorPojo) {
        return this.sysOptorBusiMapper.updateByPrimaryKeySelective(busiSysOptorPojo);
    }

    /**
     * 设置默认密码
     * @param busiSysOptorPojo
     */
    private void setDefaultLoginPassword(BusiSysOptorPojo busiSysOptorPojo){
        busiSysOptorPojo.setLoginPassword(MD5CoderUtil.encode(MD5CoderUtil.encode("666666")));
    }

    @Override
    public int insertSelective(BusiSysOptorPojo busiSysOptorPojo) {
        busiSysOptorPojo.setLoginPassword(MD5CoderUtil.encode(MD5CoderUtil.encode(busiSysOptorPojo.getLoginPassword())));
        return sysOptorBusiMapper.insertSelective(busiSysOptorPojo);
    }
}