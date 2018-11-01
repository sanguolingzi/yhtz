package com.yinhetianze.business.evaluate.service.impl;

import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateInfoService;
import com.yinhetianze.pojo.order.EvaluatePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.evaluate.mapper.EvaluateInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class EvaluateInfoServiceImpl implements EvaluateInfoService
{
    @Autowired
    private EvaluateInfoMapper mapper;


    @Override
    public List<EvaluateModel> findEvaluate(Map map) {
        return mapper.findEvaluate(map);
    }

    @Override
    public EvaluatePojo selectOne(EvaluatePojo evaluatePojo) {
        return mapper.selectOne(evaluatePojo);
    }
}