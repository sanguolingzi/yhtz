package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.TopSearchModel;
import com.yinhetianze.systemservice.system.service.info.TopSearchInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询热搜索词
 */

@Component
public class GetTopSearchExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private TopSearchInfoService topSearchInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TopSearchModel topSearchModel = (TopSearchModel)model;
        PageHelper.startPage(topSearchModel.getCurrentPage(),topSearchModel.getPageSize());
        PageInfo pageInfo = new PageInfo(topSearchInfoServiceImpl.selectTopSearchList(topSearchModel));
        return pageInfo;
    }
}
