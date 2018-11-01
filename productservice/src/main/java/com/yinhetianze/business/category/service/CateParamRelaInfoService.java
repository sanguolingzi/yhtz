package com.yinhetianze.business.category.service;

import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.pojo.category.CateParamRelaPojo;

import java.util.List;
import java.util.Map;

public interface CateParamRelaInfoService
{
    List<Map> getCateParamRelaList(ParameterModel parameterModel);
    int getParameterId(CateParamRelaPojo cateParamRelaPojo);
}