package com.yinhetianze.business.order.service.impl;

import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.business.order.model.GameDataModel;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderInfoServiceImpl implements OrderInfoService{

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<Map<String, Object>> findOrder(Map<String, Object> map) {
        return orderInfoMapper.findOrder(map);
    }

    @Override
    public List<Map<String, Object>> findOrderDetail(Integer orderId,Integer id) {
        return orderInfoMapper.findOrderDetail(orderId,id);
    }

    @Override
    public Map<String, Object> findAuditOrder(Integer id) {
        return orderInfoMapper.findAuditOrder(id);
    }

    @Override
    public Map<String, Object> findOrderTransRecord(String totalOrdersNo, Integer type) {
        return orderInfoMapper.findOrderTransRecord(totalOrdersNo,type);
    }

    @Override
    public List<Map<String, Object>> findBackOrder(Map<String, Object> map) {
        return orderInfoMapper.findBackOrder(map);
    }

    @Override
    public Map<String, Object> countOrderByCustomerCode(Integer customerId) {
        return orderInfoMapper.countOrderByCustomerCode(customerId);
    }

    @Override
    public Map<String, Object> countAmount(Integer customerId) {
        return orderInfoMapper.countAmount(customerId);
    }

    @Override
    public List<Map<String,Object>> getTakeDelivery(Map<String,Object> orderMap){
        return orderInfoMapper.getTakeDelivery(orderMap);
    }

    @Override
    public GameDataModel findThirdOrder(OrderDto orderModel){
        return orderInfoMapper.findThirdOrder(orderModel);
    }

    @Override
    public List<Map<String, Object>> settlementOrder(Map<String, Object> map) {
        return orderInfoMapper.settlementOrder(map);
    }

    @Override
    public List<Map<String, Object>> noSettlementOrder(Map<String, Object> map) {
        return orderInfoMapper.noSettlementOrder(map);
    }

    @Override
    public List<Map<String,Object>> getexpandThe(Map<String,Object> orderMap){
        return orderInfoMapper.getexpandThe(orderMap);
    }

    @Override
    public List<Map<String,Object>> autoSettlementOrder(){
        return orderInfoMapper.autoSettlementOrder();
    }

    @Override
    public List GetSettleOrder(OrderDto orderDto){
        return orderInfoMapper.GetSettleOrder(orderDto);
    }
}
