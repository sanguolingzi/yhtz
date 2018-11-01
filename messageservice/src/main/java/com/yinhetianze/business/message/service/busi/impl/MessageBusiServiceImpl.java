package com.yinhetianze.business.message.service.busi.impl;

import com.yinhetianze.business.message.mapper.busi.MessageBusiMapper;
import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageBusiServiceImpl implements MessageBusiService
{
    @Autowired
    private MessageBusiMapper busiMessageMapper;

    @Override
    public int addInfo(BusiMessagePojo busiMessagePojo) {
        return busiMessageMapper.insertSelective(busiMessagePojo);
    }

    @Override
    public int updateInfo(BusiMessagePojo busiMessagePojo) {
        return busiMessageMapper.updateByPrimaryKey(busiMessagePojo);
    }
}