package com.yinhetianze.business.message.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.message.BusiMessagePojo;

public interface MessageBusiMapper extends BusiMapper<BusiMessagePojo> {

    int updateMessageLastTime(BusiMessagePojo busiMessagePojo);
}