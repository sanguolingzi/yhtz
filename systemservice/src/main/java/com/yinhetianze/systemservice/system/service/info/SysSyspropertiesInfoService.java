package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.systemservice.system.model.BusiSysSyspropertiesModel;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;

import java.util.List;

public interface SysSyspropertiesInfoService
{
    List selectList(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo);

    List<BusiSysSyspropertiesModel> selectPropertiesList(BusiSysSyspropertiesModel busiSysSyspropertiesModel);

    BusiSysSyspropertiesPojo sysProperties(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo);

    int sysPropertiesid(BusiSysSyspropertiesModel busiSysSyspropertiesModel);

    BusiSysSyspropertiesPojo selectProperties(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo);
}