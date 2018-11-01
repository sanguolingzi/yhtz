package com.yinhetianze.business.settlement.mapper;

import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.order.SettlementPojo;

import java.util.List;

public interface SettlementInfoMapper extends InfoMapper<SettlementPojo> {

    List<SettlementPojo> findSettlements(SettlementModel settlementModel);
    List<SettlementPojo> selectSettlementsList(SettlementPojo settlementPojo);

}