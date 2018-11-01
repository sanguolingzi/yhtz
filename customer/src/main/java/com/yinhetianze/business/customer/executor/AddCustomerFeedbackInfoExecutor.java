package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.service.busi.CustomerFeedbackBusiService;
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
 * 消费者/会员 新增反馈
 */

@Component
public class AddCustomerFeedbackInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerFeedbackBusiService customerFeedbackBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerFeedbackModel busiCustomerFeedbackModel = (BusiCustomerFeedbackModel)model;
        customerFeedbackBusiServiceImpl.addInfo(busiCustomerFeedbackModel);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {

        BusiCustomerFeedbackModel busiCustomerFeedbackModel = (BusiCustomerFeedbackModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getcType())){
            errorMessage.rejectNull("cType",null,"反馈类型");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getfDescription())){
            errorMessage.rejectNull("cDescription",null,"反馈描述");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiCustomerFeedbackModel.setCustomerId(tokenUser.getId());

        /**
         * 校验 邮箱格式
         */
        if(CommonUtil.isNotEmpty(busiCustomerFeedbackModel.getfEmail())){
            errorMessage.rejectEmail("email",busiCustomerFeedbackModel.getfEmail());
        }


        return errorMessage;
    }
}
