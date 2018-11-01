package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerReceiveaddressModel;
import com.yinhetianze.business.customer.service.busi.CustomerReceiveaddressBusiService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置 默认收获地址
 */

@Component
public class UpdateDefaultAddressExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerReceiveaddressBusiService customerReceiveaddressBusiServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        BeanUtils.copyProperties(model,busiCustomerReceiveaddressPojo);
        customerReceiveaddressBusiServiceImpl.updateDefaultAddress(busiCustomerReceiveaddressPojo);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiCustomerReceiveaddressModel busiCustomerReceiveaddressModel = (BusiCustomerReceiveaddressModel)model;
        ErrorMessage errorMessage = new ErrorMessage();


        if(CommonUtil.isEmpty(busiCustomerReceiveaddressModel.getId())){
            errorMessage.rejectNull("id",null,"收货地址id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerReceiveaddressModel.getToken() )){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerReceiveaddressModel.getToken());
        busiCustomerReceiveaddressPojo.setCustomerId(tokenUser.getId());
        busiCustomerReceiveaddressPojo.setId(busiCustomerReceiveaddressModel.getId());

        busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
        if(busiCustomerReceiveaddressPojo == null){
            errorMessage.rejectErrorMessage("info","收获地址信息异常","收获地址信息异常");
            return errorMessage;
        }

        busiCustomerReceiveaddressModel.setCustomerId(tokenUser.getId());

        return errorMessage;
    }
}
