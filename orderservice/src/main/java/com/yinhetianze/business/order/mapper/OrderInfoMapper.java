package com.yinhetianze.business.order.mapper;

import com.yinhetianze.business.order.model.GameDataModel;
import com.yinhetianze.business.order.model.OrderDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderInfoMapper {

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
    List<Map<String,Object>> findOrderDetail(@Param("orderId") Integer orderId, @Param("id") Integer id);

    /**
     * 根据ID查询订单审核记录
     * @param id
     * @return
     */
    Map<String,Object> findAuditOrder(@Param("id") Integer id);

    /**
     * 查询订单支付记录
     * @param totalOrdersNo
     * @param type
     * @return
     */
    Map<String,Object> findOrderTransRecord(@Param("totalOrdersNo") String totalOrdersNo, @Param("type") Integer type);

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
    Map<String,Object> countOrderByCustomerCode(@Param("customerId") Integer customerId);

    /**
     *当前用户的消费金额和订单数量
     */
    Map<String,Object> countAmount(@Param("customerId") Integer customerId);

    /**
     * 查询自动收货所有满足条件的订单
     * @param orderMap
     * @return
     */
    List<Map<String,Object>> getTakeDelivery(Map<String, Object> orderMap);

    /**
     * 自动评价订单
     * @param orderMap
     * @return
     */
    List<Map<String,Object>> getGoodComment(Map<String, Object> orderMap);

    /**
     * 取消未付款订单
     * @param orderMap
     * @return
     */
    List<Map<String,Object>> getCancelNonPayment(Map<String, Object> orderMap);

    /**
     * 订单返利定时器
     * @return
     */
    List<Map> rewardsOrder();

    /**
     * 第三方订单详情查询
     * @param orderModel
     * @return
     */
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
     * 查询所有满座条件的未返推广赚的订单
     * @param orderMap
     * @return
     */
    List<Map<String,Object>> getexpandThe(Map<String, Object> orderMap);

    /**
     * 定时器查询可自动结算的订单
     * @return
     */
    List<Map<String,Object>> autoSettlementOrder();

    /**
     * 根据结算单id查询订单
     * @return
     * @param orderDto
     */
    List GetSettleOrder(OrderDto orderDto);
}
