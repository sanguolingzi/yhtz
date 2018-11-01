package com.yinhetianze.systemservice.thirdpart.service.busi.impl;

import com.yinhetianze.pojo.thirdpart.RewardGameRecordPojo;
import com.yinhetianze.systemservice.thirdpart.service.busi.RewardGameRecordBusiService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.thirdpart.mapper.busi.RewardGameRecordBusiMapper;

@Service
public class RewardGameRecordBusiServiceImpl implements RewardGameRecordBusiService
{
    @Autowired
    private RewardGameRecordBusiMapper mapper;

    @Override
    public void insterGameAmount(RewardGameRecordPojo rewardGameRecordPojo){
        mapper.insertSelective(rewardGameRecordPojo);
    }

    @Override
    public void updateRecord(RewardGameRecordPojo rewardGameRecordPojo){
        mapper.updateByPrimaryKeySelective(rewardGameRecordPojo);
    }
}