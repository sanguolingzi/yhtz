package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaySuccessExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderModel=(OrderDto) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser.getId())){
            throw new BusinessException("没有获取到用户信息");
        }
        BigDecimal giveIntegral=new BigDecimal("0");
        for(int i=0;i<orderModel.getOrderIds().length;i++){
            Map<String,Object> orderParment=new HashMap<>();
            orderParment.put("orderId",orderModel.getOrderIds()[i]);
            List<Map<String,Object>> orderList=orderInfoServiceImpl.findOrder(orderParment);
            if(CommonUtil.isEmpty(orderList)){
                throw new BusinessException("传入的订单号有误");
            }
            if("1".equals(orderList.get(i).get("orderStatus")+"")){
                throw new BusinessException("支付失败");
            }
            giveIntegral=giveIntegral.add(new BigDecimal(orderList.get(0).get("giveIntegral")+""));
        }
        return giveIntegral;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderModel=(OrderDto) model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(orderModel.getOrderIds())) {
            error.rejectNull("orderIds",null,"订单ID数组");
        }
        return error;
    }
}
