package com.yinhetianze.business.exchange.service.impl;

import com.yinhetianze.business.exchange.mapper.info.ExchangeInfoMapper;
import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeInfoService;
import com.yinhetianze.pojo.order.ExchangePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeInfoServiceImpl implements ExchangeInfoService
{
    @Autowired
    private ExchangeInfoMapper mapper;

    @Override
    public ExchangePojo findById(ExchangePojo exchangePojo) {
        return mapper.selectOne(exchangePojo);
    }

    @Override
    public List<ExchangePojo> findExchange(ExchangeModel exchangeModel) {
        return mapper.findExchangeOrder(exchangeModel);
    }
}