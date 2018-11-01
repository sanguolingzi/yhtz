package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.service.busi.CustomerFeedbackBusiService;
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
 * 删除 消费者/会员 反馈
 */
@Component
public class DeleteCustomerFeedbackExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerFeedbackBusiService customerFeedbackBusiServiceImpl;

    @Autowired
    private CustomerFeedbackInfoService customerFeedbackInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerFeedbackModel busiCustomerFeedbackModel = (BusiCustomerFeedbackModel)model;
        customerFeedbackBusiServiceImpl.deleteInfo(busiCustomerFeedbackModel);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerFeedbackModel busiCustomerFeedbackModel = (BusiCustomerFeedbackModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getId())){
            errorMessage.rejectNull("id",null,"返回id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerFeedbackModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerFeedbackModel.getToken());
        BusiCustomerFeedbackModel dbEntity = customerFeedbackInfoServiceImpl.selectFeedback(busiCustomerFeedbackModel.getId());
        if(dbEntity == null
                || !(dbEntity.getCustomerId() == tokenUser.getId())){
            errorMessage.rejectErrorMessage("id","反馈信息异常","反馈信息异常");
            return errorMessage;
        }
        return errorMessage;
    }
}
