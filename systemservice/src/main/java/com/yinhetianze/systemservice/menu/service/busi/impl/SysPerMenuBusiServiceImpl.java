package com.yinhetianze.systemservice.menu.service.busi.impl;

import com.yinhetianze.systemservice.menu.mapper.busi.SysPerMenuBusiMapper;
import com.yinhetianze.systemservice.menu.model.BusiSysPerMenuModel;
import com.yinhetianze.systemservice.menu.service.busi.SysPerMenuBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysPerMenuBusiServiceImpl implements SysPerMenuBusiService
{
    @Autowired
    private SysPerMenuBusiMapper sysPerMenuBusiMapper;


    @Autowired
    private SysPerMenuInfoService sysPerMenuInfoServiceImpl;



    @Override
    public int addInfo(BusiSysPerMenuModel busiSysPerMenuModel) {

        String[] menuIds = busiSysPerMenuModel.getMenuIds().split("\\,");
        for(String id:menuIds){
            BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
            busiSysPerMenuPojo.setMenuId(Integer.parseInt(id));
            busiSysPerMenuPojo.setPermissionId(busiSysPerMenuModel.getPermissionId());
            sysPerMenuBusiMapper.insertSelective(busiSysPerMenuPojo);
        }
        return 1;
    }


    @Override
    public int updateInfo(BusiSysPerMenuModel busiSysPerMenuModel) throws BusinessException{


        BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
        busiSysPerMenuPojo.setPermissionId(busiSysPerMenuModel.getPermissionId());
        //查询用户已有的角色关系
        List<BusiSysPerMenuPojo> list =  sysPerMenuInfoServiceImpl.selectList(busiSysPerMenuPojo);;
        String menuIds  = ","+busiSysPerMenuModel.getMenuIds()+",";
        //toDelete 找出 数据库中有 而提交参数没有的  权限菜单信息   这些信息属于要删除的
        List<BusiSysPerMenuPojo> toDelete = list.stream().filter(sysPerMenuPojo->{
            if(menuIds.indexOf( ","+sysPerMenuPojo.getMenuId()+",")>=0){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        if(CommonUtil.isNotEmpty(toDelete)){
            toDelete.stream().forEach(sysPerMenuPojo->{
                deleteInfo(sysPerMenuPojo.getId());
            });
            list.removeAll(toDelete);
        }

        //toAdd 找出 数据库中没有 而提交参数有的  权限菜单信息   这些信息属于要新增的
        List<String> toAdd = Arrays.stream(busiSysPerMenuModel.getMenuIds().split("\\,")).filter(id->{
            if(list.isEmpty())
                return true;
            return !list.stream().anyMatch(sysPerMenuPojo->{
                return (sysPerMenuPojo.getMenuId() == Integer.parseInt(id));
            });
        }).collect(Collectors.toList());

        if(CommonUtil.isNotEmpty(toAdd)){
            for(String id:toAdd){
                BusiSysPerMenuPojo add = new BusiSysPerMenuPojo();
                add.setPermissionId(busiSysPerMenuModel.getPermissionId());
                add.setMenuId(Integer.parseInt(id));
                sysPerMenuBusiMapper.insertSelective(add);
            }
        }
        return 1;
    }

    @Override
    public int deleteInfo(Integer id){
        BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
        busiSysPerMenuPojo.setId(id);
        busiSysPerMenuPojo.setDelFlag((short)1);
        return sysPerMenuBusiMapper.updateByPrimaryKeySelective(busiSysPerMenuPojo);
    }
}