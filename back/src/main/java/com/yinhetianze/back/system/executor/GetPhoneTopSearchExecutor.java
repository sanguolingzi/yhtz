package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.TopSearchModel;
import com.yinhetianze.systemservice.system.service.info.TopSearchInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 查询热搜索词
 */

@Component
public class GetPhoneTopSearchExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private TopSearchInfoService topSearchInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TopSearchModel topSearchModel = (TopSearchModel)model;
        //设置查询状态0 设置是否显示状态0
        topSearchModel.setDelFlag((short)0);
        topSearchModel.setIsShow((short)0);
        List<Map> topSearchList =topSearchInfoServiceImpl.getPhoneTopSearch(topSearchModel);
        return topSearchList;
    }
}
