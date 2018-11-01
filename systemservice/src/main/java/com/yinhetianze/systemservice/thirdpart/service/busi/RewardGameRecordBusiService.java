package com.yinhetianze.systemservice.thirdpart.service.busi;

import com.yinhetianze.pojo.thirdpart.RewardGameRecordPojo;

public interface RewardGameRecordBusiService
{
    void insterGameAmount(RewardGameRecordPojo rewardGameRecordPojo);

    void updateRecord(RewardGameRecordPojo rewardGameRecordPojo);
}