package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.TopSearchPojo;

public interface TopSearchBusiService
{
    int insertSelective(TopSearchPojo topSearchPojo);

    int updateByPrimaryKeySelective(TopSearchPojo topSearchPojo);

    int batchDelete(String ids) throws BusinessException;
}