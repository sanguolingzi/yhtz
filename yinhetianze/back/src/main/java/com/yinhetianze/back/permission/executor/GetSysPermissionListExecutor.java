package com.yinhetianze.back.permission.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionPageModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.systemservice.permission.service.info.SysPermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分页查询系统权限
 */
@Component
public class GetSysPermissionListExecutor extends AbstractRestBusiExecutor<PageInfo> {

    @Autowired
    private SysPermissionInfoService sysPermissionInfoServiceImpl;

    @Override
    protected PageInfo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysPermissionPageModel busiSysPermissionPageModel = (BusiSysPermissionPageModel)model;
        if(busiSysPermissionPageModel.getIsAll() == null){
            PageHelper.startPage(busiSysPermissionPageModel.getCurrentPage(),busiSysPermissionPageModel.getPageSize());
        }
        List<BusiSysPermissionModel> list = sysPermissionInfoServiceImpl.selectSysPermissionList(busiSysPermissionPageModel);
        PageInfo<BusiSysPermissionModel> pageInfo = new PageInfo<BusiSysPermissionModel>(list);
        return pageInfo;
    }
}
