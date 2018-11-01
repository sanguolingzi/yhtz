package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.business.category.service.CateParamRelaInfoService;
import com.yinhetianze.pojo.category.CateParamRelaPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.category.mapper.CateParamRelaInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class CateParamRelaInfoServiceImpl implements CateParamRelaInfoService
{
    @Autowired
    private CateParamRelaInfoMapper mapper;

    @Override
    public List<Map> getCateParamRelaList(ParameterModel parameterModel) {
        return mapper.getCateParamRelaList(parameterModel);
    }

    @Override
    public int getParameterId(CateParamRelaPojo cateParamRelaPojo) {
        return mapper.getParameterId(cateParamRelaPojo);
    }
}