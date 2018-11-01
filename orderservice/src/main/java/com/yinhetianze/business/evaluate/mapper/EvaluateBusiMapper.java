package com.yinhetianze.business.evaluate.mapper;

import com.yinhetianze.business.evaluate.model.EvaluateModel;

import java.util.List;
import java.util.Map;

public interface EvaluateBusiMapper {

    /**
     * 添加评论
     * @param list
     * @return
     */
    int addEvaluate(List<EvaluateModel> list);

    /**
     * 更新商品评价
     * @param map
     * @return
     */
    int updateEvaluate(Map<String, Object> map);



    /**
     * 商品自动评价（定时任务）
     */
    int autoEvaluate(EvaluateModel evaluateModel);

    /**
     * 批量更新商品评价隐藏
     * @param evaluateIds
     * @return
     */
    int modifyEvaluate(Integer[] evaluateIds);
}
