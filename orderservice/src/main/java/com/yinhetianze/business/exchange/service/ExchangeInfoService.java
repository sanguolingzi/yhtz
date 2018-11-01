package com.yinhetianze.business.exchange.service;

import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.pojo.order.ExchangePojo;

import java.util.List;

public interface ExchangeInfoService
{
    /**
     * 根据售后单ID查询售后单
     * @param exchangePojo
     * @return
     */
    ExchangePojo findById(ExchangePojo exchangePojo);

    /**
     * 查询用户的售后单，或者商家的售后单
     * @param
     * @return
     */
    List<ExchangePojo> findExchange(ExchangeModel exchangeModel);
}