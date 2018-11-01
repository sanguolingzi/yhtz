package com.yinhetianze.business.exchange.service.impl;

import com.yinhetianze.business.exchange.mapper.busi.ExchangeOplogBusiMapper;
import com.yinhetianze.business.exchange.service.ExchangeOplogBusiService;
import com.yinhetianze.pojo.order.ExchangeOplog;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ExchangeOplogBusiServiceImpl implements ExchangeOplogBusiService
{
    @Autowired
    private ExchangeOplogBusiMapper mapper;

    @Override
    public int add(ExchangeOplog exchangeOplog) {
        return mapper.insertSelective(exchangeOplog);
    }
}