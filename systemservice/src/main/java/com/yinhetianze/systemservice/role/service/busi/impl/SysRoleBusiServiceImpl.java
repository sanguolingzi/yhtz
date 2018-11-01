package com.yinhetianze.systemservice.role.service.busi.impl;

import com.yinhetianze.systemservice.permission.service.busi.SysRoleperBusiService;
import com.yinhetianze.systemservice.permission.service.info.SysRoleperInfoService;
import com.yinhetianze.systemservice.role.mapper.busi.SysRoleBusiMapper;
import com.yinhetianze.systemservice.role.model.BusiSysRoleModel;
import com.yinhetianze.systemservice.role.service.busi.SysRoleBusiService;
import com.yinhetianze.systemservice.role.service.busi.SysUserRoleBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysRolePojo;
import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import com.yinhetianze.systemservice.role.service.info.SysUserRoleInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysRoleBusiServiceImpl implements SysRoleBusiService
{
    @Autowired
    private SysRoleBusiMapper sysRoleBusiMapper;

    @Autowired
    private SysRoleperInfoService sysRoleperInfoServiceImpl;

    @Autowired
    private SysRoleperBusiService sysRoleperBusiServiceImpl;

    @Autowired
    private SysUserRoleInfoService sysUserRoleInfoServiceImpl;

    @Autowired
    private SysUserRoleBusiService sysUserRoleBusiServiceImpl;

    @Override
    public int addInfo(BusiSysRoleModel busiSysRoleModel) {
        BusiSysRolePojo busiSysRolePojo = new BusiSysRolePojo();
        BeanUtils.copyProperties(busiSysRoleModel,busiSysRolePojo);
        sysRoleBusiMapper.insertSelective(busiSysRolePojo);
        return 1;
    }

    @Override
    public int updateInfo(BusiSysRoleModel busiSysRoleModel) throws BusinessException {
        BusiSysRolePojo busiSysRolePojo = new BusiSysRolePojo();
        BeanUtils.copyProperties(busiSysRoleModel,busiSysRolePojo);
        busiSysRolePojo.setUpdateTime(new Date());
        sysRoleBusiMapper.updateByPrimaryKeySelective(busiSysRolePojo);
        return 1;
    }

    @Override
    public int deleteInfo(Integer id) {
        BusiSysRolePojo busiSysRolePojo = new BusiSysRolePojo();
        busiSysRolePojo.setDelFlag((short)1);
        busiSysRolePojo.setId(id);
        busiSysRolePojo.setUpdateTime(new Date());
        int result = sysRoleBusiMapper.updateByPrimaryKeySelective(busiSysRolePojo);
        if(result <= 0)
            return 0;

        BusiSysRoleperPojo busiSysRoleperPojo = new BusiSysRoleperPojo();
        busiSysRoleperPojo.setId(id);
        busiSysRoleperPojo.setDelFlag((short)0);
        List<BusiSysRoleperPojo> list = sysRoleperInfoServiceImpl.select(busiSysRoleperPojo);
        if(CommonUtil.isNotEmpty(list)){
            for(BusiSysRoleperPojo pojo:list){
                sysRoleperBusiServiceImpl.deleteInfo(pojo.getId());
            }
        }

        BusiSysUserRolePojo busiSysUserRolePojo = new BusiSysUserRolePojo();
        busiSysUserRolePojo.setRoleId(id);
        busiSysUserRolePojo.setDelFlag((short)0);
        List<BusiSysUserRolePojo> userRoleList = sysUserRoleInfoServiceImpl.select(busiSysUserRolePojo);
        if(CommonUtil.isNotEmpty(userRoleList)){
            for(BusiSysUserRolePojo pojo:userRoleList){
                sysUserRoleBusiServiceImpl.deleteInfo(pojo.getId());
            }
        }

        return 1;
    }
}