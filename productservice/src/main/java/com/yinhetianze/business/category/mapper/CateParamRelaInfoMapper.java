package com.yinhetianze.business.category.mapper;

import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.category.CateParamRelaPojo;

import java.util.List;
import java.util.Map;

public interface CateParamRelaInfoMapper extends InfoMapper<CateParamRelaPojo> {
    List<Map> getCateParamRelaList(ParameterModel parameterModel);
    int getParameterId(CateParamRelaPojo cateParamRelaPojo);
}