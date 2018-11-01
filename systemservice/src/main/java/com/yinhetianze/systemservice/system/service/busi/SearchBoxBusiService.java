package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.SearchBoxPojo;

public interface SearchBoxBusiService
{
    int insertSelective(SearchBoxPojo searchBoxPojo);

    int updateByPrimaryKeySelective(SearchBoxPojo searchBoxPojo);

    int batchDelete(String ids) throws BusinessException;
}