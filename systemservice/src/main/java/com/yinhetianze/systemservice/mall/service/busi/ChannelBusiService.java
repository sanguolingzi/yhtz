package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.ChannelPojo;

public interface ChannelBusiService
{
    int insertSelective(ChannelPojo channelPojo);

    int deleteBatch(String ids)  throws BusinessException;

    int updateByPrimaryKeySelective(ChannelPojo channelPojo);

    int addInfo(ChannelModel channelModel) throws BusinessException;

    int updateInfo(ChannelModel channelModel) throws BusinessException;
}