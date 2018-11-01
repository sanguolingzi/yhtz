package com.yinhetianze.business.order.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderBusiMapper {

    /**
     *添加订单
     * @param map
     * @return
     */
    int addOrder(Map<String, Object> map);

    /**
     * 添加订单详情
     * @param list
     * @return
     */
    int addOrderDetail(List<Map<String, Object>> list);

    /**
     * 根据ID删除订单
     * @param orderId
     * @return
     */
    int deleteOrder(@Param("orderId") Integer orderId);

    /**
     * 更新订单信息
     * @param map
     * @return
     */
    int updateOrder(Map<String, Object> map);

    /**
     * 添加订单操作记录
     * @param map
     * @return
     */
    int addOrderLog(Map<String, Object> map);

    /**
     * 订单审核
     * @param map
     * @return
     */
    int addOrderAudit(Map<String, Object> map);

    /**
     * 更新订单审核
     * @param map
     * @return
     */
    int updateOrderAudit(Map<String, Object> map);

    /**
     * 订单付款记录
     * @param map
     * @return
     */
    int addOrderTransRecord(Map<String, Object> map);

    /**
     * 更改付款记录
     * @param map
     * @return
     */
    int updateTransRecord(Map<String, Object> map);

    /**
     * 更改订单详情记录
     * @param map
     * @return
     */
    int updateOrderDetail(Map<String, Object> map);

    /**
     * 更新销量
     */
    int updateSales(Map salesMap);

    /**
     * 更新订单返利状态
     * @param ordersId
     */
    int updateOrderDetailRebate(Integer ordersId);

    int updateOrderSettlementId(Map orderMap);
}
