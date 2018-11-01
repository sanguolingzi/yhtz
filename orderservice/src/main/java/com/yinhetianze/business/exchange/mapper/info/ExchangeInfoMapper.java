package com.yinhetianze.business.exchange.mapper.info;

import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.order.ExchangePojo;

import java.util.List;

public interface ExchangeInfoMapper extends InfoMapper<ExchangePojo> {

    List<ExchangePojo> findExchangeOrder(ExchangeModel exchangeModel);
}