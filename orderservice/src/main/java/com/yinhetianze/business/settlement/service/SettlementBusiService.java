package com.yinhetianze.business.settlement.service;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.order.SettlementPojo;

public interface SettlementBusiService
{
    int addSettlement(SettlementPojo settlementPojo,Integer type);

    int checkSettlement(SettlementPojo settlementPojo);

    int paymentSettlement(SettlementPojo settlementPojo);

    void insertSelective(SettlementPojo settlementPojo) throws BusinessException;
}