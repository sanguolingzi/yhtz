package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.systemservice.mall.service.info.MallFlashreportInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *查询商品快报
 */
@Component
public class GetMallFlashreportExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private MallFlashreportInfoService mallFlashreportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MallFlashreportModel mallFlashreportModel =(MallFlashreportModel)model;
        PageHelper.startPage(mallFlashreportModel.getCurrentPage(), mallFlashreportModel.getPageSize());
        PageInfo pageInfo=new PageInfo(mallFlashreportInfoServiceImpl.getBusiMallFlashreport(mallFlashreportModel));
        return pageInfo;
    }
}
