package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GetOpenIdExecutor  extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        OrderDto orderDto=(OrderDto)model;
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
        busiCustomerWechatPojo.setCustomerId(tokenUser.getId());
        busiCustomerWechatPojo.setIdType(orderDto.getIdType());
        busiCustomerWechatPojo = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);
        if (CommonUtil.isEmpty(busiCustomerWechatPojo)) {
            return "fail";
        }else{
            if(CommonUtil.isEmpty(busiCustomerWechatPojo.getOpenId())){
                return "fail";
            }
            return "success";
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderDto=(OrderDto)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(orderDto.getIdType())){
            errorMessage.rejectNull("idType",null,"openId类型");
        }
        return errorMessage;
    }
}
