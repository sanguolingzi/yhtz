package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.mapper.ParameterBusiMapper;
import com.yinhetianze.business.category.mapper.ParameterInfoMapper;
import com.yinhetianze.business.category.model.CateParamRelaModel;
import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.business.category.service.ParameterBusiService;
import com.yinhetianze.business.category.service.ParameterInfoService;
import com.yinhetianze.pojo.category.ParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterBusiServiceImpl implements ParameterBusiService
{
    @Autowired
    private ParameterBusiMapper mapper;

    @Override
    public int addParameterId(ParameterPojo parameterPojo) {
        return mapper.addParameterId(parameterPojo);
    }

    @Override
    public int updateParameter(ParameterPojo parameterPojo) {
        return mapper.updateByPrimaryKeySelective(parameterPojo);
    }
}