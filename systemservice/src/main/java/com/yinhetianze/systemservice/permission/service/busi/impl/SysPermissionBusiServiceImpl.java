package com.yinhetianze.systemservice.permission.service.busi.impl;

import com.yinhetianze.systemservice.menu.service.busi.SysPerMenuBusiService;
import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import com.yinhetianze.systemservice.permission.mapper.busi.SysPermissionBusiMapper;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.service.busi.SysPermissionBusiService;
import com.yinhetianze.systemservice.permission.service.busi.SysRoleperBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.pojo.back.BusiSysPermissionPojo;
import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import com.yinhetianze.systemservice.permission.service.info.SysRoleperInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysPermissionBusiServiceImpl implements SysPermissionBusiService
{
    @Autowired
    private SysPermissionBusiMapper sysPermissionBusiMapper;

    @Autowired
    private SysPerMenuBusiService sysPerMenuBusiServiceImpl;

    @Autowired
    private SysPerMenuInfoService sysPerMenuInfoServiceImpl;

    @Autowired
    private SysRoleperInfoService sysRoleperInfoServiceImpl;

    @Autowired
    private SysRoleperBusiService sysRoleperBusiServiceImpl;


    @Override
    public int addInfo(BusiSysPermissionModel busiSysPermissionModel) {
        BusiSysPermissionPojo busiSysPermissionPojo  = new BusiSysPermissionPojo();
        BeanUtils.copyProperties(busiSysPermissionModel,busiSysPermissionPojo);
        sysPermissionBusiMapper.insertSelective(busiSysPermissionPojo);
        return 1;
    }

    @Override
    public int updateInfo(BusiSysPermissionModel busiSysPermissionModel) throws BusinessException {
        BusiSysPermissionPojo busiSysPermissionPojo  = new BusiSysPermissionPojo();
        BeanUtils.copyProperties(busiSysPermissionModel,busiSysPermissionPojo);
        return sysPermissionBusiMapper.updateByPrimaryKeySelective(busiSysPermissionPojo);
    }

    @Override
    public int deleteInfo(Integer id) {
        BusiSysPermissionPojo busiSysPermissionPojo  = new BusiSysPermissionPojo();
        busiSysPermissionPojo.setDelFlag((short)1);
        busiSysPermissionPojo.setId(id);
        int result = sysPermissionBusiMapper.updateByPrimaryKeySelective(busiSysPermissionPojo);
        if(result <= 0){
            return 0;
        }
        BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
        busiSysPerMenuPojo.setPermissionId(id);
        busiSysPerMenuPojo.setDelFlag((short)0);
        List<BusiSysPerMenuPojo> list = sysPerMenuInfoServiceImpl.selectList(busiSysPerMenuPojo);

        //删除权限菜单
        if(CommonUtil.isNotEmpty(list)){
            for(BusiSysPerMenuPojo pojo: list){
                sysPerMenuBusiServiceImpl.deleteInfo(pojo.getId());
            }
        }

        BusiSysRoleperPojo busiSysRoleperPojo = new BusiSysRoleperPojo();
        busiSysRoleperPojo.setPermissionId(id);
        busiSysRoleperPojo.setDelFlag((short)0);
        List<BusiSysRoleperPojo> rolePerList = sysRoleperInfoServiceImpl.select(busiSysRoleperPojo);

        //删除角色权限
        if(CommonUtil.isNotEmpty(rolePerList)){
            for(BusiSysRoleperPojo pojo: rolePerList){
                sysRoleperBusiServiceImpl.deleteInfo(pojo.getId());
            }
        }


        return 1;
    }
}