package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.mapper.info.SysSyspropertiesInfoMapper;
import com.yinhetianze.systemservice.system.model.BusiSysSyspropertiesModel;
import com.yinhetianze.systemservice.system.service.info.SysSyspropertiesInfoService;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysSyspropertiesInfoServiceImpl implements SysSyspropertiesInfoService
{
    @Autowired
    private SysSyspropertiesInfoMapper mapper;

    @Override
    public List selectList(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo){
        return mapper.select(busiSysSyspropertiesPojo);
    }

    @Override
    public List<BusiSysSyspropertiesModel> selectPropertiesList(BusiSysSyspropertiesModel busiSysSyspropertiesModel) {
        return mapper.selectPropertiesList(busiSysSyspropertiesModel);
    }

    @Override
    public  BusiSysSyspropertiesPojo sysProperties(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo) {
        return  mapper.selectOne(busiSysSyspropertiesPojo);
    }

    @Override
    public int sysPropertiesid(BusiSysSyspropertiesModel busiSysSyspropertiesModel) {
        return mapper.sysPropertiesid(busiSysSyspropertiesModel);
    }


    public BusiSysSyspropertiesPojo selectProperties(BusiSysSyspropertiesPojo busiSysSyspropertiesPojo) {
        return mapper.selectOne(busiSysSyspropertiesPojo);
    }
}