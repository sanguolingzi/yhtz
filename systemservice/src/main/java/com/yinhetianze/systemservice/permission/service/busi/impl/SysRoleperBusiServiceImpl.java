package com.yinhetianze.systemservice.permission.service.busi.impl;

import com.yinhetianze.systemservice.permission.mapper.busi.SysRoleperBusiMapper;
import com.yinhetianze.systemservice.permission.model.BusiSysRoleperModel;
import com.yinhetianze.systemservice.permission.service.busi.SysRoleperBusiService;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import com.yinhetianze.systemservice.permission.service.info.SysRoleperInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysRoleperBusiServiceImpl implements SysRoleperBusiService
{
    @Autowired
    private SysRoleperBusiMapper sysRoleperBusiMapper;

    @Autowired
    private SysRoleperInfoService sysRoleperInfoServiceImpl;

    @Override
    public int updateInfo(BusiSysRoleperModel busiSysRoleperModel) {


        BusiSysRoleperPojo busiSysRoleperPojo = new BusiSysRoleperPojo();
        busiSysRoleperPojo.setRoleId(busiSysRoleperModel.getRoleId());
        //查询用户已有的角色权限关系
        List<BusiSysRoleperPojo> list =  sysRoleperInfoServiceImpl.select(busiSysRoleperPojo);;
        String permissionIds  = ","+busiSysRoleperModel.getPermissionIds()+",";
        //toDelete 找出 数据库中有 而提交参数没有的  角色权限信息   这些信息属于要删除的
        List<BusiSysRoleperPojo> toDelete = list.stream().filter(sysRoleperPojo->{
            if(permissionIds.indexOf( ","+sysRoleperPojo.getPermissionId()+",")>=0){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        if(CommonUtil.isNotEmpty(toDelete)){
            toDelete.stream().forEach(sysRoleperPojo->{
                deleteInfo(sysRoleperPojo.getId());
            });
            list.removeAll(toDelete);
        }

        //toAdd 找出 数据库中没有 而提交参数有的  角色权限信息   这些信息属于要新增的
        List<String> toAdd = Arrays.stream(busiSysRoleperModel.getPermissionIds().split("\\,")).filter(id->{
            if(list.isEmpty())
                return true;
            return !list.stream().anyMatch(sysRoleperPojo->{
                return (sysRoleperPojo.getPermissionId() == Integer.parseInt(id));
            });
        }).collect(Collectors.toList());

        if(CommonUtil.isNotEmpty(toAdd)){
            for(String id:toAdd){
                BusiSysRoleperPojo add = new BusiSysRoleperPojo();
                add.setRoleId(busiSysRoleperModel.getRoleId());
                add.setPermissionId(Integer.parseInt(id));
                sysRoleperBusiMapper.insertSelective(add);
            }
        }

        return 1;
    }

    @Override
    public int deleteInfo(Integer id) {
        BusiSysRoleperPojo busiSysRoleperPojo = new BusiSysRoleperPojo();
        busiSysRoleperPojo.setId(id);
        busiSysRoleperPojo.setDelFlag((short)1);
        return sysRoleperBusiMapper.updateByPrimaryKeySelective(busiSysRoleperPojo);
    }
}