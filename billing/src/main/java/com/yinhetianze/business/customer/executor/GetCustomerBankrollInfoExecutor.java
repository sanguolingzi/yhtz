package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 获取账户信息
 */

@Component
public class GetCustomerBankrollInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollModel busiCustomerBankrollModel = (BusiCustomerBankrollModel)model;
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(busiCustomerBankrollModel.getCustomerId());
        return busiCustomerBankrollPojo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        BusiCustomerBankrollModel busiCustomerBankrollModel = (BusiCustomerBankrollModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();

       if(CommonUtil.isEmpty(busiCustomerBankrollModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerBankrollModel.getToken());
        busiCustomerBankrollModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }

}
