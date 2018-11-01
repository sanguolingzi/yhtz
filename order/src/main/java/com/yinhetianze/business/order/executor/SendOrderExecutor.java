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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SendOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;
    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto = (OrderDto) model;
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(model.getToken());
        if (CommonUtil.isEmpty(tokenUser.getId())) {
            throw new BusinessException("没有获取到用户信息");
        }
        //校验订单ID
        Map<String, Object> orderParment = new HashMap<>();
        orderParment.put("orderId", orderDto.getOrderId());
        List<Map<String, Object>> list = orderInfoServiceImpl.findOrder(orderParment);
        if (CommonUtil.isEmpty(list)) {
            throw new BusinessException("传入的订单ID有误");
        }
        if (Integer.valueOf(list.get(0).get("orderStatus") + "") != 2) {
            throw new BusinessException("该订单已发货或者未付款");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderDto.getOrderId());
        map.put("expressNo", orderDto.getExpressNo());
        map.put("companyName", orderDto.getCompanyName());
        map.put("deliveryTime", new Date());
        map.put("orderStatus", 3);
        int i = orderBusiServiceImpl.oneYuanSendOrder(map,tokenUser.getId());
        if (i != 1) {
            throw new BusinessException("订单发货失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error = new ErrorMessage();
        OrderDto orderDto = (OrderDto) model;
        if (CommonUtil.isEmpty(orderDto.getOrderId())) {
            error.rejectNull("orderId", null, "订单ID");
        }
        if (CommonUtil.isEmpty(orderDto.getExpressNo())) {
            error.rejectNull("expressNo", null, "快递单号");
        }
        if (CommonUtil.isEmpty(orderDto.getCompanyName())) {
            error.rejectNull("companyName", null, "快递公司名称");
        }
        return error;
    }
}
