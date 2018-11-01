package com.yinhetianze.business.message.executor;

import com.yinhetianze.business.message.model.BusiMessageDetailPageModel;
import com.yinhetianze.business.message.service.info.MessageDetailInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取 消息列表
 */

@Component
public class GetMessageDetailListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private MessageDetailInfoService messageDetailInfoServiceImpl;



    @Autowired
    private UserDetailsService redisUserDetails;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiMessageDetailPageModel busiMessageDetailPageModel = (BusiMessageDetailPageModel)model;
        return messageDetailInfoServiceImpl.selecMessagetPageInfo(busiMessageDetailPageModel);
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiMessageDetailPageModel busiMessageDetailPageModel = (BusiMessageDetailPageModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(busiMessageDetailPageModel.getmType())){
            errorMessage.rejectNull("mType",null,"消息类型");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(busiMessageDetailPageModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiMessageDetailPageModel.getToken());
        busiMessageDetailPageModel.setMessageId(tokenUser.getId());
        return errorMessage;
    }
}
