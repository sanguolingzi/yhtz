package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetParentSysMenuExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysMenuInfoService sysMenuInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysMenuModel sysMenuModel = (SysMenuModel)model;
        sysMenuModel.setParentId(-1);
        List<SysMenuModel> list = sysMenuInfoServiceImpl.selectMenuList(sysMenuModel);
        //定义顶级菜单
        sysMenuModel= new SysMenuModel();
        sysMenuModel.setId(-1);
        sysMenuModel.setMenuName("顶级菜单");

        List<SysMenuModel> returnList = new ArrayList<>();

        returnList.add(sysMenuModel);
        returnList.addAll(list);
        return returnList;
    }
}
