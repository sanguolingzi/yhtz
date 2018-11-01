package com.yinhetianze.business.message.service.busi;

import com.yinhetianze.pojo.message.BusiMessagePojo;

public interface MessageBusiService
{
    int addInfo(BusiMessagePojo busiMessagePojo);

    int updateInfo(BusiMessagePojo busiMessagePojo);
}