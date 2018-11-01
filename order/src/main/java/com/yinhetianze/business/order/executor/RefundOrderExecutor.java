package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderBusiService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RefundOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        Map<String,Object> orderParment=new HashMap<>();
        orderParment.put("orderId",orderDto.getOrderId());
        List<Map<String, Object>> orderList = orderInfoServiceImpl.findOrder(orderParment);
        if (CommonUtil.isEmpty(orderList)) {
            throw new BusinessException("传入的订单号有误");
        }
        if(Integer.valueOf(orderList.get(0).get("orderStatus")+"")!=2){
            throw new BusinessException("已付款订单才可以退款");
        }
        Map<String,Object> map=new HashMap<>();
        //订单参数
        map.put("orderId",orderDto.getOrderId());
        map.put("orderStatus",7);
        //操作记录参数
        map.put("onceOrderStatus",orderList.get(0).get("orderStatus"));
        map.put("actorId",tokenUser.getId());
        //退款申请（修改订单状态，添加订单操作记录，添加售后记录）
        int i=orderBusiServiceImpl.addRefundOrder(map);
        if(i<3){
            throw new BusinessException("申请退款失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderDto=(OrderDto) model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(orderDto.getOrderId())){
            error.rejectNull("orderId",null,"订单ID");
        }
        return error;
    }
}
