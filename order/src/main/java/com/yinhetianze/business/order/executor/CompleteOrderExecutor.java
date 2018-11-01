package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
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
public class CompleteOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        Map<String,Object> orderParment=new HashMap<>();
        orderParment.put("orderId",orderDto.getOrderId());
        List<Map<String,Object>> orderList=orderInfoServiceImpl.findOrder(orderParment);
        if(CommonUtil.isEmpty(orderList)){
            throw new BusinessException("传入的订单号有误");
        }
        if(Integer.valueOf(orderList.get(0).get("orderStatus")+"")!=3){
            throw new BusinessException("已发货的订单才可以收货");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("orderId",orderDto.getOrderId());
        if(Integer.valueOf(orderList.get(0).get("isGameOrder")+"")<2){
            map.put("orderStatus",4);
        }else{
            map.put("orderStatus",6);
        }
        map.put("completeTime",new Date());
        int i=orderBusiServiceImpl.updateOrder(map);
        if(i!=1){
            throw new BusinessException("更改订单状态失败");
        }
        //操作记录
        map.put("onceOrderStatus",orderList.get(0).get("orderStatus"));
        map.put("actorId",tokenUser.getId());
        orderBusiServiceImpl.addOrderLog(map);

       //通知
        /*List<Map<String,Object>> orderDetail=orderInfoServiceImpl.findOrderDetail(orderDto.getOrderId(),null);
        Map<String,Object> jsonMap=new HashMap<>();
        BusiMessageDetailPojo busiMessageDetailPojo=new BusiMessageDetailPojo();
        busiMessageDetailPojo.setmType((short)0);
        busiMessageDetailPojo.setmTitle("订单收货通知");
        jsonMap.put("state","订单收货");
        jsonMap.put("orderNo",orderList.get(0).get("ordersNo"));
        jsonMap.put("companyName",orderList.get(0).get("companyName"));
        jsonMap.put("logisticNo",orderList.get(0).get("expressNo"));
        jsonMap.put("prodUrl",orderDetail.get(0).get("photoUrl"));
        jsonMap.put("orderId",orderDto.getOrderId());
        busiMessageDetailPojo.setmContent(CommonUtil.objectToJsonString(jsonMap));
        busiMessageDetailPojo.setMessageId(tokenUser.getId());
        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);*/
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
