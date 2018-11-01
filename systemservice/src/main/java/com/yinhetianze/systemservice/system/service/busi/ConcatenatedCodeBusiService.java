package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.pojo.back.ConcatenatedCodePojo;

public interface ConcatenatedCodeBusiService
{
    int  insertSelective(ConcatenatedCodePojo concatenatedCodePojo);
    int updateByPrimaryKeySelective(ConcatenatedCodePojo concatenatedCodePojo);
}