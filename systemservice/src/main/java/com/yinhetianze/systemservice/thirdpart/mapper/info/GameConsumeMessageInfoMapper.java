package com.yinhetianze.systemservice.thirdpart.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.order.GameConsumeMessagePojo;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;

import java.util.List;

public interface GameConsumeMessageInfoMapper extends InfoMapper<GameConsumeMessagePojo>{

    List<GameConsumeMessagePojo> selectGameRecord(GameRecordModel gameRecordModel);
}