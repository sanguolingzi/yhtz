package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuditOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto) model;
        Map<String,Object> orderParment=new HashMap<>();
        orderParment.put("orderId",orderDto.getOrderId());
        List<Map<String,Object>> list=orderInfoServiceImpl.findOrder(orderParment);
        if(CommonUtil.isEmpty(list)){
            throw new BusinessException("传入的订单ID有误");
        }
        Map<String,Object> parament=new HashMap<>();
        parament.put("createUser",orderDto.getUserId());
        parament.put("orderId",orderDto.getOrderId());
        parament.put("auditStatus",orderDto.getAuditStatus());
        parament.put("reason",orderDto.getReason());
        parament.put("onceAuditStatus",list.get(0).get("auditStatus"));
        int i=orderBusiServiceImpl.addOrderAudit(parament);
        if(i!=1){
            throw new BusinessException("添加订单审核记录失败");
        }
        orderBusiServiceImpl.updateOrder(parament);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderDto=(OrderDto) model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(orderDto.getUserId())){
            error.rejectNull("userId",null,"操作员ID");
        }
        if(CommonUtil.isEmpty(orderDto.getOrderId())){
            error.rejectNull("orderId",null,"订单ID");
        }
        if(CommonUtil.isEmpty(orderDto.getAuditStatus())){
            error.rejectNull("auditStatus",null,"审核状态");
        }
        if(orderDto.getAuditStatus()==1&&CommonUtil.isEmpty(orderDto.getReason())){
            error.rejectNull("reason",null,"审核不通过时，审核原因");
        }
        return error;
    }
}
