package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateTaxExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderModel=(OrderDto) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser.getId())){
            throw new BusinessException("没有获取到用户信息");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderModel.getOrderId());
        map.put("taxType",orderModel.getTaxType());
        map.put("taxNo",orderModel.getTaxNo());
        map.put("taxCompany",orderModel.getTaxCompany());
        map.put("receiptType",orderModel.getReceiptType());
        int i=orderBusiServiceImpl.updateOrder(map);
        if(i<1){
            throw new BusinessException("修改发票失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderModel=(OrderDto) model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(orderModel.getOrderId())){
            errorMessage.rejectNull("orderId",null,"订单号");
        }
        if(CommonUtil.isEmpty(orderModel.getTaxType())){
            errorMessage.rejectNull("taxType",null,"发票类型");
        }
        if(orderModel.getTaxType()==2&&(CommonUtil.isEmpty(orderModel.getTaxNo())||CommonUtil.isEmpty(orderModel.getTaxCompany()))){
            errorMessage.rejectNull("taxNo",null,"公司发票的税号");
        }
        return errorMessage;
    }
}
