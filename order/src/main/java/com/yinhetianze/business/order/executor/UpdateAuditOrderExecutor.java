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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateAuditOrderExecutor  extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto)model;

        Map<String,Object> auditOrder=orderInfoServiceImpl.findAuditOrder(orderDto.getAuditId());
        if(CommonUtil.isEmpty(auditOrder)){
            throw new BusinessException("传入的审核ID有误");
        }
        Map<String,Object> parament=new HashMap<>();
        parament.put("id",orderDto.getAuditId());
        parament.put("auditStatus",orderDto.getAuditStatus());
        parament.put("reason",orderDto.getReason());
        parament.put("onceAuditStatus",auditOrder.get("auditStatus"));
        parament.put("updateTime",new Date());
        parament.put("updateUser",orderDto.getCustomerId());
        parament.put("orderId",auditOrder.get("orderId"));
        int i=orderBusiServiceImpl.updateOrderAudit(parament);
        if(i!=1){
            throw new BusinessException("更新审核记录失败");
        }
        orderBusiServiceImpl.updateOrder(parament);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        OrderDto orderDto=(OrderDto)model;
        if(CommonUtil.isEmpty(orderDto.getCustomerId())){
            error.rejectNull("userId",null,"操作人ID");
        }
        if(CommonUtil.isEmpty(orderDto.getAuditId())){
            error.rejectNull("auditId",null,"审核记录ID");
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
