package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.systemservice.mall.service.info.ChannelInfoService;
import com.yinhetianze.pojo.back.ChannelPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.ChannelInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ChannelInfoServiceImpl implements ChannelInfoService
{
    @Autowired
    private ChannelInfoMapper mapper;

    @Override
    public List selectList(ChannelPojo channelPojo){
        return mapper.select(channelPojo);
    }

    @Override
    public List<ChannelModel> selectChannelList(ChannelModel channelModel) {
        return mapper.selectChannelList(channelModel);
    }

    @Override
    public ChannelPojo selectOne(ChannelPojo channelPojo) {
        channelPojo.setDelFlag((short)0);
        return mapper.selectOne(channelPojo);
    }

    @Override
    public List<Map> selectForMobileIndex(ChannelModel channelModel) {
        return mapper.selectForMobileIndex(channelModel);
    }
}