package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import com.yinhetianze.systemservice.permission.service.info.SysRoleperInfoService;
import com.yinhetianze.systemservice.role.service.info.SysUserRoleInfoService;
import com.yinhetianze.systemservice.system.mapper.info.SysMenuInfoMapper;
import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.pojo.back.BusiSysRoleperPojo;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import com.yinhetianze.pojo.back.SysMenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuInfoServiceImpl implements SysMenuInfoService
{
    @Autowired
    private SysMenuInfoMapper mapper;

    @Autowired
    private SysUserRoleInfoService sysUserRoleInfoServiceImpl;

    @Autowired
    private SysRoleperInfoService sysRoleperInfoServiceImpl;

    @Autowired
    private SysPerMenuInfoService sysPerMenuInfoServiceImpl;
    @Override
    public List selectList(SysMenuPojo sysMenuPojo){
        return mapper.select(sysMenuPojo);
    }

    @Override
    public SysMenuPojo selectOne(SysMenuPojo sysMenuPojo) {
        sysMenuPojo.setDelFlag((short)0);
        return mapper.selectOne(sysMenuPojo);
    }

    @Override
    public List<SysMenuModel> selectMenuList(SysMenuModel sysMenuModel) {
        return mapper.selectMenuList(sysMenuModel);
    }

    @Override
    public List<Map<String, Object>> GetSysMenuForUpdateList(List<SysMenuPojo> list) {
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        for(SysMenuPojo menu : list) {

            if (menu.getParentId() == -1) {//父菜单 m == null 需要初始化参数
                Map<String, Object> m = getMap(menu.getId(), returnList);
                if (m == null) {
                    m = new HashMap<>();
                    m.put("menuId", menu.getId());
                    m.put("menuName", menu.getMenuName());
                    m.put("childrenMenu", new ArrayList<Map<String, Object>>());
                    returnList.add(m);
                } else {
                    m.put("menuName", menu.getMenuName());
                    //m.put("childrenMenu", new ArrayList<Map<String, Object>>());
                }
            } else {//子菜单 首先找自己的父菜单有没有数据 有的话 则初始化父菜单除名称以外的数据 否则 初始化自己的数据
                Map<String, Object> m = getMap(menu.getParentId(), returnList);
                List<Map<String, Object>> childrenMenu = null;
                if (m == null) {
                    m = new HashMap<>();
                    m.put("menuId", menu.getParentId());
                    childrenMenu = new ArrayList<Map<String, Object>>();
                    m.put("childrenMenu",childrenMenu);
                    returnList.add(m);
                }else{
                    childrenMenu = (List<Map<String, Object>>)m.get("childrenMenu");
                }
                Map<String,Object> child = new  HashMap<>();
                child.put("menuId",menu.getId());
                child.put("menuName",menu.getMenuName());
                child.put("parentId",menu.getParentId());
                childrenMenu.add(child);
            }
        }
        return returnList;
    }


    @Override
    public List<Map<String, Object>> selectOptorMenuList(Integer userId) {
        List<Map<String,Object>> menuList = new ArrayList<>();

        BusiSysUserRolePojo busiSysUserRolePojo = new BusiSysUserRolePojo();
        busiSysUserRolePojo.setOptorId(userId);
        busiSysUserRolePojo.setDelFlag((short)0);

        //查找用户角色
        List<BusiSysUserRolePojo> userRoleList = sysUserRoleInfoServiceImpl.select(busiSysUserRolePojo);
        if(CommonUtil.isEmpty(userRoleList))
            return menuList;

        for(BusiSysUserRolePojo userRole : userRoleList){


            BusiSysRoleperPojo busiSysRoleperPojo = new BusiSysRoleperPojo();
            busiSysRoleperPojo.setRoleId(userRole.getRoleId());
            busiSysRoleperPojo.setDelFlag((short)0);

            //查找角色权限
            List<BusiSysRoleperPojo> rolePerList = sysRoleperInfoServiceImpl.select(busiSysRoleperPojo);
            if(CommonUtil.isEmpty(rolePerList))
                continue;


            for(BusiSysRoleperPojo rolePer : rolePerList){

                BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
                busiSysPerMenuPojo.setPermissionId(rolePer.getPermissionId());
                busiSysPerMenuPojo.setDelFlag((short)0);

                //查找权限菜单
                List<BusiSysPerMenuPojo> perMenuList = sysPerMenuInfoServiceImpl.selectList(busiSysPerMenuPojo);
                if(CommonUtil.isEmpty(perMenuList))
                    continue;

                for(BusiSysPerMenuPojo perMenu : perMenuList){


                    SysMenuPojo sysMenuPojo = new SysMenuPojo();
                    sysMenuPojo.setId(perMenu.getMenuId());
                    sysMenuPojo.setDelFlag((short)0);
                    sysMenuPojo = mapper.selectOne(sysMenuPojo);

                    if(sysMenuPojo == null)
                        continue;


                    if(sysMenuPojo.getParentId() == -1){

                        Map<String,Object> parent = getMap(sysMenuPojo.getId(),menuList);
                        if(parent == null){
                            parent = new HashMap<>();
                            parent.put("menuId",sysMenuPojo.getId());
                            parent.put("subMenu",new ArrayList<Map<String,Object>>());
                            menuList.add(parent);
                        }
                        //在菜单重复的时候 这段代码重复执行
                        parent.put("title",sysMenuPojo.getMenuName());
                        parent.put("link",sysMenuPojo.getMenuLink());
                        parent.put("active",sysMenuPojo.getIsWork());

                    }else{
                        Map<String,Object> parent = getMap(sysMenuPojo.getParentId(),menuList);
                        if(parent == null){
                            parent = new HashMap<>();
                            parent.put("menuId",sysMenuPojo.getParentId());
                            List<Map<String,Object>> subMenu = new ArrayList<Map<String,Object>>();
                            Map<String,Object> child = new HashMap<>();
                            child.put("menuId",sysMenuPojo.getId());
                            child.put("title",sysMenuPojo.getMenuName());
                            child.put("link",sysMenuPojo.getMenuLink());
                            child.put("active",sysMenuPojo.getIsWork());
                            subMenu.add(child);
                            parent.put("subMenu",subMenu);
                            menuList.add(parent);
                        }else{
                            List<Map<String,Object>> subMenu =  (List<Map<String,Object>>)parent.get("subMenu");

                            //多个角色下 菜单可能会有重复 需要处理这种情况
                            if(getMap(sysMenuPojo.getId(),subMenu) == null){
                                Map<String,Object> child = new HashMap<>();
                                child.put("menuId",sysMenuPojo.getId());
                                child.put("title",sysMenuPojo.getMenuName());
                                child.put("link",sysMenuPojo.getMenuLink());
                                child.put("active",sysMenuPojo.getIsWork());
                                subMenu.add(child);
                            }
                        }

                    }

                }
            }

        }
        return menuList;
    }



    /**
     * 从list中 根据 Id 找到的map
     * @param id
     * @param list  数据集合
     * @return
     */
    private Map<String,Object> getMap(Integer id,List<Map<String,Object>> list){
        if(list == null || list.isEmpty())
            return null;


        for(Map<String,Object> m : list){
            if(Integer.parseInt(m.get("menuId").toString()) == id){
                return m;
            }
        }
        return null;
    }
}