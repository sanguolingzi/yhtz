package com.yinhetianze.business.order.service;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.core.business.BusinessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderBusiService {

    /**
     * 添加订单
     * @param list
     * @return
     */
    int[] addOrder(List<Map<String, Object>> list) throws BusinessException;

    /**
     * 根据ID删除订单
     * @param orderId
     * @return
     */
    int deleteOrder(Integer orderId);

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
     * 支付完成后改变订单状态
     */
    void changeOrder(Integer customerId, Integer[] orderIds, BigDecimal amount, List<BusiCustomerBankrollFlowModel> list) throws BusinessException;

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
     * 取消订单并更新库存和星币
     * @param
     * @return
     */
    void cancelOrder(Map<String,Object> orderMap) throws BusinessException;

    /**
     * 更改订单详情记录
     * @param map
     * @return
     */
    int updateOrderDetail(Map<String, Object> map);

    /**
     * 申请退款add
     */
    int addRefundOrder(Map<String, Object> map) throws BusinessException;

    /**
     * 订单发货
     * @param map
     * @return
     * @throws BusinessException
     */
    int sendOrder(Map<String, Object> map)throws BusinessException;

    /**
     * 订单添加物流
     */
    int updateLogistics(Map<String, Object> map)throws BusinessException;

    /**
     * 会员和一元购订单发货
     */
    int oneYuanSendOrder(Map<String, Object> map,Integer userId)throws BusinessException;

    /**
     * 生成U币兑换订单
     */
    int convertOrderBuy(Map<String, Object> map)throws BusinessException;

    /**
     * 生成活动订单
     */
    int activityOrderBuy(Map<String, Object> map)throws BusinessException;


    /***************************************************************************************************************************/

    /**
     * 定时器取消未支付订单
     * @param orderMap
     * @param updateMap
     * @param orderLog
     * @param productDetail
     */
    void updateCancelNonPayment(Map orderMap, Map updateMap, Map orderLog, Map productDetail) throws BusinessException;

    /**
     * 定时器自动好评
     * @param evaluateModel
     * @param orderLog
     * @param updateMap
     */
    void goodComment(EvaluateModel evaluateModel, Map orderLog, Map updateMap) throws BusinessException;

    /**
     * 定时器自动收获
     * @param updateMap
     * @param orderLog
     */
    void takeDelivery(Map updateMap, Map orderLog) throws BusinessException;

    /**
     * 微信或者支付宝回调
     * @param list
     * @throws BusinessException
     */
    void payOrder(List<Map<String,Object>> list,Integer payType) throws BusinessException;

    /**
     * 更新销量
     */
    int updateSales(Map salesMap);
    /**
     * 定时器返推广赚
     * @param map
     */
    void updateAmount(Map<String, Object> map) throws BusinessException;    /**
     * 一元购下单
     * @param map
     * @return
     */
    int oneYuanBuy(Map<String,Object> map);

    /**
     * 更新订单表的结算单Id字段settlementId
     * @param orderMap
     */
    int updateOrderSettlementId(Map orderMap);

    /**
     * 兑换券订单生成
     * @param map
     * @return
     */
    int addVoucherOrder(Map<String,Object> map) throws BusinessException;
}

