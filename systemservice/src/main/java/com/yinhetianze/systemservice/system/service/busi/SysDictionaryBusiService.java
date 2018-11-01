package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.SysDictionaryPojo;

public interface SysDictionaryBusiService
{
    int insertSelective(SysDictionaryPojo sysDictionaryPojo);

    int updateByPrimaryKeySelective(SysDictionaryPojo sysDictionaryPojo);

    int deleteInfo(String ids) throws BusinessException;
}