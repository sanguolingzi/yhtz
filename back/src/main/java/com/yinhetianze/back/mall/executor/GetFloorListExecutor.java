package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GetFloorListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysFloorModel sysFloorModel = (SysFloorModel)model;
        PageHelper.startPage(sysFloorModel.getCurrentPage(),sysFloorModel.getPageSize());
        PageInfo pageInfo = new PageInfo(sysFloorInfoServiceImpl.getFloorList());
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
