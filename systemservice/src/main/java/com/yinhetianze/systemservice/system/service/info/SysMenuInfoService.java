package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.pojo.back.SysMenuPojo;

import java.util.List;
import java.util.Map;

public interface SysMenuInfoService
{
    List selectList(SysMenuPojo sysMenuPojo);

    SysMenuPojo selectOne(SysMenuPojo sysMenuPojo);

    List<SysMenuModel> selectMenuList(SysMenuModel sysMenuModel);

    /**
     *  [
         {
             menuName:'大菜单',
             childrenMenu:[
                 { menuId:'1', menuName: '小菜单' },
                 { menuId:'4', menuName: '小菜单' },
                 { menuId:'3', menuName: '小菜单' },
                 { menuId:'2', menuName: '小菜单' },
             ]
         }
     ]
     * @param list
     * @return
     */
    List<Map<String,Object>> GetSysMenuForUpdateList(List<SysMenuPojo> list);

    List<Map<String,Object>> selectOptorMenuList(Integer userId);
}