package com.yinhetianze.business.evaluate.mapper;

import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.order.EvaluatePojo;

import java.util.List;
import java.util.Map;

public interface EvaluateInfoMapper extends InfoMapper<EvaluatePojo> {

    List<EvaluateModel> findEvaluate(Map<String,Object> map);
}