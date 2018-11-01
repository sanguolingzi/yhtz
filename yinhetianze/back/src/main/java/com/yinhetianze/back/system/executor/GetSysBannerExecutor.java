package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.service.info.SysBannerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询banner
 */

@Component
public class GetSysBannerExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysBannerInfoService sysBannerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysBannerModel sysBannerModel = (SysBannerModel)model;
        PageHelper.startPage(sysBannerModel.getCurrentPage(),sysBannerModel.getPageSize());
        PageInfo pageInfo = new PageInfo(sysBannerInfoServiceImpl.selectSysBannerList(sysBannerModel));
        return pageInfo;
    }
}
