package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.SearchBoxModel;
import com.yinhetianze.systemservice.system.service.info.SearchBoxInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询搜索词
 */

@Component
public class GetSearchBoxExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SearchBoxInfoService searchBoxInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SearchBoxModel searchBoxModel = (SearchBoxModel)model;
        PageHelper.startPage(searchBoxModel.getCurrentPage(),searchBoxModel.getPageSize());
        PageInfo pageInfo = new PageInfo(searchBoxInfoServiceImpl.selectModelList(searchBoxModel));
        return pageInfo;
    }
}
