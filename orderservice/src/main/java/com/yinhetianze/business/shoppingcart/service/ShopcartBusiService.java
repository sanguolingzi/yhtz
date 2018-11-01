package com.yinhetianze.business.shoppingcart.service;

import java.util.List;
import java.util.Map;

public interface ShopcartBusiService {

    /**
     * 新增购物车
     * @param map
     * @return
     */
    int addShopcart(Map<String, Object> map);
    /**
     * 批量删除购物车数据
     * @param array
     */
    void deleteShopcart(Integer[] array);

    /**
     * 批量更新购物车信息
     * @param list
     */
    void modifyShopcart(List<Map<String, Object>> list);

    /**
     *根据ID修改商品数量
     */
    int updateById(Map<String, Object> map);
}
