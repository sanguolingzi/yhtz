package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.service.info.CustomerFeedbackInfoService;
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
 * 消费者/会员 获取反馈详情
 */

@Component
public class GetCustomerFeedbackExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerFeedbackInfoService customerFeedbackInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerFeedbackModel busiCustomerFeedbackModel = (BusiCustomerFeedbackModel)model;
        String moduleName = busiCustomerFeedbackModel.getModelName();
        busiCustomerFeedbackModel = customerFeedbackInfoServiceImpl.selectFeedback(busiCustomerFeedbackModel.getId());
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerFeedbackModel.getToken());


        if("backSendRequest".equals(moduleName)){
            return  busiCustomerFeedbackModel;
        }else if(busiCustomerFeedbackModel.getCustomerId() !=tokenUser.getId() ){
            return null;
        }
        return  busiCustomerFeedbackModel;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerFeedbackModel busiCustomerFeedbackModel = (BusiCustomerFeedbackModel)model;
        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getId())){
            errorMessage.rejectNull("id",null,"反馈id");
            return  errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return  errorMessage;
        }

        return errorMessage;
    }

}
