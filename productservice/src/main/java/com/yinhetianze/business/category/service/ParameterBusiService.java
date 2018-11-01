package com.yinhetianze.business.category.service;

import com.yinhetianze.business.category.model.CateParamRelaModel;
import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.pojo.category.ParameterPojo;

public interface ParameterBusiService
{
    int addParameterId(ParameterPojo parameterPojo);

    int updateParameter(ParameterPojo parameterPojo);
}