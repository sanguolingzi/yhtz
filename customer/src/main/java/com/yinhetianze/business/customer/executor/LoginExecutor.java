package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 消费者/会员 登录
 */

@Component
public class LoginExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;
        Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);
        return userInfo;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        ErrorMessage errorMessage  = new ErrorMessage();
        if(CommonUtil.isEmpty(busiCustomerModel.getPhone())){
            errorMessage.rejectNull("phone",null,"手机号");
            return errorMessage;
        }

        if(!busiCustomerModel.getPhone().matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("recommendPhone","BC0017","手机号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerModel.getLoginPassword())){
            errorMessage.rejectNull("password",null,"密码");
            return errorMessage;
        }

        //BusiCustomerPojo busiCustomerPojo = busiCustomerInfoServiceImpl.selectByPhone(busiCustomerModel.getPhone());
        //if(busiCustomerPojo == null){
        //    errorMessage.rejectError("info","BC0007","");
        //    return errorMessage;
        //}

        //if(!CustomerUtil.checkPassword(busiCustomerPojo.getLoginPassword(),busiCustomerModel.getLoginPassword())){
        //    errorMessage.rejectError("info","BC0007","");
        //    return errorMessage;
        //}

        return null;
    }
}
