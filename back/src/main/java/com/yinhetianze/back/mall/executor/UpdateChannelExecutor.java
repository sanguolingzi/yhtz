package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ChannelPojo;
import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.systemservice.mall.service.busi.ChannelBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.systemservice.mall.service.info.ChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改频道信息
 */

@Component
public class UpdateChannelExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private ChannelBusiService channelBusiServiceImpl;

    @Autowired
    private ChannelInfoService channelInfoServiceImpl;
    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = channelBusiServiceImpl.updateInfo((ChannelModel)model);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
       ChannelModel channelModel = (ChannelModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

       if(CommonUtil.isEmpty(channelModel.getId())){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
       }

       if(CommonUtil.isEmpty(channelModel.getChannelName())){
           errorMessage.rejectError("channelName",null,"频道名称");
           return errorMessage;
       }

       ChannelPojo channelPojo = new ChannelPojo();
       channelPojo.setChannelName(channelModel.getChannelName());
       channelPojo = channelInfoServiceImpl.selectOne(channelPojo);
       if(channelPojo != null
               && channelPojo.getId().intValue() != channelModel.getId().intValue()){
           errorMessage.rejectError("channelName","BC0055","频道名称","频道名称");
           return errorMessage;
       }

       return errorMessage;
    }
}
