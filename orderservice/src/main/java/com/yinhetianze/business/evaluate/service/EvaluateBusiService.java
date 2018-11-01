package com.yinhetianze.business.evaluate.service;

import com.yinhetianze.business.evaluate.model.EvaluateDto;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.core.business.BusinessException;

import java.util.List;
import java.util.Map;

public interface EvaluateBusiService {

    /**
     * 添加评论
     * @param evaluateDto
     * @return
     */
    int addEvaluate(EvaluateDto evaluateDto,Integer customerId) throws BusinessException;

    /**
     * 更新商品评价
     * @param map
     * @return
     */
    int updateEvaluate(Map<String, Object> map);

    /**
     * 批量更新商品评价隐藏
     * @param evaluateIds
     * @return
     */
    int modifyEvaluate(Integer[] evaluateIds);
}
