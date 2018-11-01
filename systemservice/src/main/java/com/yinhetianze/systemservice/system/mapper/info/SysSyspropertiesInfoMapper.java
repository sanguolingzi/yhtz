package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.systemservice.system.model.BusiSysSyspropertiesModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;

import java.util.List;

public interface SysSyspropertiesInfoMapper extends InfoMapper<BusiSysSyspropertiesPojo> {
    List<BusiSysSyspropertiesModel> selectPropertiesList(BusiSysSyspropertiesModel busiSysSyspropertiesModel);
    int sysPropertiesid(BusiSysSyspropertiesModel busiSysSyspropertiesModel);
}