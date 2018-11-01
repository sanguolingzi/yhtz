package com.yinhetianze.systemservice.thirdpart.service.info.impl;

import com.yinhetianze.pojo.thirdpart.RewardGameRecordPojo;
import com.yinhetianze.systemservice.thirdpart.service.info.RewardGameRecordInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.thirdpart.mapper.info.RewardGameRecordInfoMapper;

@Service
public class RewardGameRecordInfoServiceImpl implements RewardGameRecordInfoService
{
    @Autowired
    private RewardGameRecordInfoMapper mapper;

    @Override
    public RewardGameRecordPojo getOneRewardId(RewardGameRecordPojo rewardGameRecordPojo){
        return mapper.selectOne(rewardGameRecordPojo);
    }
}