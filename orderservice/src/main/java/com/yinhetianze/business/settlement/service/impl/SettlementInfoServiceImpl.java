package com.yinhetianze.business.settlement.service.impl;

import com.yinhetianze.business.settlement.mapper.SettlementInfoMapper;
import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementInfoService;
import com.yinhetianze.pojo.order.SettlementPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SettlementInfoServiceImpl implements SettlementInfoService
{
    @Autowired
    private SettlementInfoMapper mapper;

    @Override
    public SettlementPojo findById(SettlementPojo settlementPojo) {
        return mapper.selectOne(settlementPojo);
    }

    @Override
    public List<SettlementPojo> findSettlements(SettlementModel settlementModel) {
        return mapper.findSettlements(settlementModel);
    }

    @Override
    public List<SettlementPojo> selectSettlementsList(SettlementPojo settlementPojo) {
        return mapper.selectSettlementsList(settlementPojo);
    }
}