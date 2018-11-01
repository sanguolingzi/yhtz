package com.yinhetianze.systemservice.thirdpart.service.info;

import com.yinhetianze.pojo.order.GameConsumeMessagePojo;
import com.yinhetianze.pojo.thirdpart.GameRecordPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;

import java.util.List;

public interface GameRecordInfoService{
    List<GameConsumeMessagePojo> selectGameRecord(GameRecordModel gameRecordModel);
    /**
     * 平台消费通知接口
     */
    String consumeMessage(GameBusinessModel gameBusinessModel);
}