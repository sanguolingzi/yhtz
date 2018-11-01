package com.yinhetianze.business.parameter.service.impl;

import com.yinhetianze.business.parameter.service.SpecialParametersBusiService;
import com.yinhetianze.pojo.product.SpecialParametersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinhetianze.business.parameter.mapper.SpecialParametersBusiMapper;

@Service
public class SpecialParametersBusiServiceImpl implements SpecialParametersBusiService
{
    @Autowired
    private SpecialParametersBusiMapper mapper;

    @Override
    public int addProductParameter(SpecialParametersPojo pojo){
        return mapper.insertSelective(pojo);
    }

    @Override
    public int modifyProductParameter(SpecialParametersPojo paramPojo){
        return mapper.updateByPrimaryKeySelective(paramPojo);
    }

    @Override
    public int deleteProductParameter(SpecialParametersPojo paramPojo){
        return mapper.deleteByPrimaryKey(paramPojo);
    }
}