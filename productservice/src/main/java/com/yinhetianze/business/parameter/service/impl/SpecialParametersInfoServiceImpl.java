package com.yinhetianze.business.parameter.service.impl;

import com.yinhetianze.business.parameter.service.SpecialParametersInfoService;
import com.yinhetianze.pojo.product.SpecialParametersPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.parameter.mapper.SpecialParametersInfoMapper;

import java.util.List;

@Service
public class SpecialParametersInfoServiceImpl implements SpecialParametersInfoService
{
    @Autowired
    private SpecialParametersInfoMapper mapper;

    @Override
    public Object getProductParameterList(SpecialParametersPojo pojo){
        return mapper.select(pojo);
    }

    @Override
    public SpecialParametersPojo findProductParameter(SpecialParametersPojo paramPojo){
        return mapper.selectOne(paramPojo);
    }

    @Override
    public List<SpecialParametersPojo> findListProductParameter(SpecialParametersPojo paramPojo){
        return mapper.select(paramPojo);
    }
}