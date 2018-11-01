package com.yinhetianze.business.settlement.service;

import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.pojo.order.SettlementPojo;

import java.util.List;

public interface SettlementInfoService
{
    SettlementPojo findById(SettlementPojo settlementPojo);

    List<SettlementPojo> findSettlements(SettlementModel settlementModel);

    List<SettlementPojo> selectSettlementsList(SettlementPojo settlementPojo);
}