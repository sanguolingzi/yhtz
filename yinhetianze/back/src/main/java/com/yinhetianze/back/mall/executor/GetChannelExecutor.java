package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.systemservice.mall.service.info.ChannelInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询频道
 */

@Component
public class GetChannelExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ChannelInfoService channelInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ChannelModel channelModel = (ChannelModel)model;
        PageHelper.startPage(channelModel.getCurrentPage(),channelModel.getPageSize());
        PageInfo pageInfo = new PageInfo(channelInfoServiceImpl.selectChannelList(channelModel));
        return pageInfo;
    }
}
