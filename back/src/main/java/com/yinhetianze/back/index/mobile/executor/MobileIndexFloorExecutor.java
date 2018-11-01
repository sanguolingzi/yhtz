package com.yinhetianze.back.index.mobile.executor;

import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.SysFloorPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MobileIndexFloorExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysFloorPojo sysFloorPojo = new SysFloorPojo();
        sysFloorPojo.setDelFlag((short)0);
        sysFloorPojo.setFloorType((short)0);
        return sysFloorInfoServiceImpl.selectForMobile(sysFloorPojo);
    }

}
