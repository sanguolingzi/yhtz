package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.ChannelPojo;

import java.util.List;
import java.util.Map;

public interface ChannelInfoMapper extends InfoMapper<ChannelPojo> {
    List<ChannelModel> selectChannelList(ChannelModel channelModel);

    List<Map> selectForMobileIndex(ChannelModel channelModel);
}