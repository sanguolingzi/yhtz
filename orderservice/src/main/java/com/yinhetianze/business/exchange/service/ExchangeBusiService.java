package com.yinhetianze.business.exchange.service;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.order.ExchangePojo;

public interface ExchangeBusiService
{
    /**
     * 添加退款记录
     * @param exchangePojo
     * @return
     */
    int addExchange(ExchangePojo exchangePojo) throws BusinessException;

    /**
     * 更新退款记录
     */
    int updateExchange(ExchangePojo exchangePojo);

    /**
     * 取消售后
     */
    int cancelExchange(ExchangePojo exchangePojo,Integer customerId) throws BusinessException;

    /**
     * 商家审核售后记录
     */
    int checkExchange(ExchangePojo exchangePojo,Integer customerId) throws BusinessException;

    /**
     * 买家发货
     * @param exchangePojo
     * @param customerId
     * @return
     */
    int deliveryExchange(ExchangePojo exchangePojo,Integer customerId);

    /**
     * 卖家收货
     * @param exchangePojo
     * @param customerId
     * @return
     */
    int collectExchange(ExchangePojo exchangePojo,Integer customerId);

    /**
     * 商家打款
     */
    int moneyExchange(ExchangePojo exchangePojo,Integer customerId) throws BusinessException;
}