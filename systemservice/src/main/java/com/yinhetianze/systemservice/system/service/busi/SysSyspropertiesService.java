package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;

public interface SysSyspropertiesService
{
    int insertSelective(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo);

    int updateByPrimaryKeySelective(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo);

    int deleteInfo(String ids) throws BusinessException;
}