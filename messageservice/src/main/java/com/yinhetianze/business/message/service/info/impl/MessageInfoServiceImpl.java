package com.yinhetianze.business.message.service.info.impl;

import com.yinhetianze.business.message.service.info.MessageInfoService;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.message.mapper.info.MessageInfoMapper;

@Service
public class MessageInfoServiceImpl implements MessageInfoService
{
    @Autowired
    private MessageInfoMapper busiMessageInfoMapper;

    @Override
    public BusiMessagePojo selectOne(BusiMessagePojo busiMessagePojo) {
        busiMessagePojo.setDelFlag((short)0);
        return busiMessageInfoMapper.selectOne(busiMessagePojo);
    }
}