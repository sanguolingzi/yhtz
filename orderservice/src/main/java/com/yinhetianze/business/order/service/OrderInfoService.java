package com.yinhetianze.business.order.service;


import com.yinhetianze.business.order.model.GameDataModel;
import com.yinhetianze.business.order.model.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderInfoService {

    /**
     * 查询我的订单
     * @return
     */
    List<Map<String,Object>> findOrder(Map<String, Object> map);

    /**
     * 查询商品详情
     * @param orderId
     * @return
     */
    List<Map<String,Object>> findOrderDetail(Integer orderId, Integer id);

    /**
     * 根据ID查询订单审核记录
     * @param id
     * @return
     */
    Map<String,Object> findAuditOrder(Integer id);

    /**
     * 订单付款记录
     * @param totalOrdersNo
     * @param type
     * @return
     */
    Map<String,Object> findOrderTransRecord(String totalOrdersNo, Integer type);

    /**
     * 后台查询订单信息
     * @param map
     * @return
     */
    List<Map<String,Object>> findBackOrder(Map<String, Object> map);

    /**
     * 用户每种订单的数量
     * @return
     */
    Map<String,Object> countOrderByCustomerCode(Integer customerId);

    /**
     *当前用户的消费金额和订单数量
     */
    Map<String,Object> countAmount(Integer customerId);

    /**
     * 获取所有满足自动收货条件的订单（自动收货定时器用）
     * @param orderMap
     * @return
     */
    List<Map<String,Object>> getTakeDelivery(Map<String, Object> orderMap);

    GameDataModel findThirdOrder(OrderDto orderModel);

    /**
     * 可结算的订单
     */
    List<Map<String,Object>> settlementOrder(Map<String, Object> map);

    /**
     * 不可结算的订单
     */
    List<Map<String,Object>> noSettlementOrder(Map<String, Object> map);

    /**
     * 查询所有满足推广赚的订单
     * @param orderMap
     * @return
     */
    List<Map<String,Object>> getexpandThe(Map<String, Object> orderMap);

    /**
     * 定时器查询可结算的订单
     * @return
     */
    List<Map<String,Object>> autoSettlementOrder();

    /**
     * 根据结算单id查询订单
     * @param orderDto
     * @return
     */
    List GetSettleOrder(OrderDto orderDto);
}
