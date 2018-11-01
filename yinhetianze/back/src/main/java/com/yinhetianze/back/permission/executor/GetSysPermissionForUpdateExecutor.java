package com.yinhetianze.back.permission.executor;

import com.yinhetianze.systemservice.menu.service.info.SysPerMenuInfoService;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionPageModel;
import com.yinhetianze.systemservice.permission.service.info.SysPermissionInfoService;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysPerMenuPojo;
import com.yinhetianze.pojo.back.SysMenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色 修改列表 权限菜单构造成如下格式
 *  [
     {
         privId: '0',
         privName:'小权限',
         listMenu: [
             {
                 menuName:'菜单名称',
                 childrenMenu:[
                     {menuName:'小菜单名称'},
                     {menuName:'小菜单名称'},
                     {menuName:'小菜单名称'},
                ]
             }
         ]
     }
 ]
 */
@Component
public class GetSysPermissionForUpdateExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysPerMenuInfoService sysPerMenuInfoServiceImpl;

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;

    @Autowired
    private SysPermissionInfoService sysPermissionInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiSysPermissionPageModel busiSysPermissionPageModel = new BusiSysPermissionPageModel();
        List<BusiSysPermissionModel> permissionPojoList = sysPermissionInfoServiceImpl.selectSysPermissionList(busiSysPermissionPageModel);
        List<Map<String,Object>> returnList = new ArrayList<>();
        //外层 查找权限集合
        for(BusiSysPermissionModel busiSysPermissionModel:permissionPojoList){
            Map<String, Object>  permissionMap = new HashMap<>();
            permissionMap.put("permId",busiSysPermissionModel.getId());
            permissionMap.put("permName",busiSysPermissionModel.getPermName());

            List<Map<String,Object>> listMenu = new ArrayList<>();
            BusiSysPerMenuPojo busiSysPerMenuPojo = new BusiSysPerMenuPojo();
            busiSysPerMenuPojo.setPermissionId(busiSysPermissionModel.getId());
            List<BusiSysPerMenuPojo> sysPerMenuPojoList = sysPerMenuInfoServiceImpl.selectList(busiSysPerMenuPojo);
            if(CommonUtil.isNotEmpty(sysPerMenuPojoList)){
                List<SysMenuPojo> menuList = new ArrayList<>();
                for(BusiSysPerMenuPojo perMenu : sysPerMenuPojoList){
                    SysMenuPojo sysMenuPojo = new SysMenuPojo();
                    sysMenuPojo.setId(perMenu.getMenuId());
                    sysMenuPojo = sysMenuInfoServiceImpl.selectOne(sysMenuPojo);
                    if(sysMenuPojo == null)
                        continue;

                    menuList.add(sysMenuPojo);
                }

                listMenu = sysMenuInfoServiceImpl.GetSysMenuForUpdateList(menuList);
            }
            permissionMap.put("listMenu",listMenu);
            returnList.add(permissionMap);
        }
        return returnList;

    }

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
