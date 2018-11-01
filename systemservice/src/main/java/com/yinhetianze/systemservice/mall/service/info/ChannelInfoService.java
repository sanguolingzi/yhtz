package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.pojo.back.ChannelPojo;

import java.util.List;
import java.util.Map;

public interface ChannelInfoService
{
    List selectList(ChannelPojo channelPojo);

    List<ChannelModel> selectChannelList(ChannelModel channelModel);

    List<Map> selectForMobileIndex(ChannelModel channelModel);

    ChannelPojo selectOne(ChannelPojo channelPojo);

}