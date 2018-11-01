package com.yinhetianze.business.evaluate.service;

import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.pojo.order.EvaluatePojo;

import java.util.List;
import java.util.Map;

public interface EvaluateInfoService {

    /**
     * 查询评价信息
     * @param
     * @return
     */
    List<EvaluateModel> findEvaluate(Map map);

    EvaluatePojo selectOne(EvaluatePojo evaluatePojo);
}
