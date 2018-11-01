package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.pojo.back.ClassifyPojo;

public interface ClassifyBusiService
{
    int insertSelective(ClassifyPojo classifyPojo);

    int updateByPrimaryKeySelective(ClassifyPojo classifyPojo);

    int delete(ClassifyPojo classifyPojo);
}