package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 退出登陆
 */

@Component
public class LoginOutExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserDetailsService redisUserDetails;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        /**
         * 删除token信息
         */
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;
        redisUserDetails.deleteUserDetails(busiCustomerModel.getToken());
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        ErrorMessage errorMessage  = new ErrorMessage();
        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        return null;
    }
}
