package com.yinhetianze.business.evaluate.service.impl;

import com.yinhetianze.business.evaluate.mapper.EvaluateBusiMapper;
import com.yinhetianze.business.evaluate.model.EvaluateDto;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateBusiService;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.core.business.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = {BusinessException.class,RuntimeException.class})
public class EvaluateBusiServiceImpl implements EvaluateBusiService{

    @Autowired
    private EvaluateBusiMapper evaluateBusiMapper;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Override
    public int addEvaluate(EvaluateDto evaluateDto,Integer customerId) throws BusinessException{
        int i=evaluateBusiMapper.addEvaluate(evaluateDto.getEvaluateList());
        if(i<1){
            throw new BusinessException("添加评论失败");
        }
        Map<String,Object> order=new HashMap<>();
        order.put("orderId",evaluateDto.getOrderId());
        if(evaluateDto.getAddEvaluate()==0){  //不是追评
            order.put("orderStatus",5);
            order.put("onceOrderStatus",4);
            order.put("evaluateTime",new Date());
        }else{
            order.put("orderStatus",6);
            order.put("onceOrderStatus",5);
        }
        int j=orderBusiServiceImpl.updateOrder(order);
        if(j!=1){
            throw new BusinessException("更改订单状态失败");
        }
        //操作记录
        order.put("actorId",customerId);
        orderBusiServiceImpl.addOrderLog(order);
        return i;
    }

    @Override
    public int updateEvaluate(Map<String, Object> map) {
        return evaluateBusiMapper.updateEvaluate(map);
    }

    @Override
    public int modifyEvaluate(Integer[] evaluateIds) {
        return evaluateBusiMapper.modifyEvaluate(evaluateIds);
    }
}
