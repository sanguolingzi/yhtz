package com.yinhetianze.systemservice.thirdpart.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.thirdpart.GameRecordPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;

import java.util.List;

public interface GameRecordInfoMapper extends InfoMapper<GameRecordPojo> {
    List<GameRecordPojo> selectGameRecord(GameRecordModel gameRecordModel);
}