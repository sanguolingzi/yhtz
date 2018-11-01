package com.yinhetianze.business.category.mapper;

import com.yinhetianze.business.category.model.CateParamRelaModel;
import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.category.ParameterPojo;

public interface ParameterBusiMapper extends BusiMapper<ParameterPojo> {
    int addParameterId(ParameterPojo parameterPojo);
}