package com.yinhetianze.business.shoppingcart.service;


import java.util.List;
import java.util.Map;

public interface ShopcartInfoService {

    /**
     * 根据ID查询购物车
     * @param id
     * @return
     */
    Map<String,Object> findShopcartById(Integer id);

    /**
     * 根据customerId查询购物车信息
     */
    List<Map<String,Object>> findShopcartByCustomerId(Integer customerId);

    /**
     * 根据用户编码和产品ID查询购物车信息
     */
    Map<String,Object> findShopcart(Integer customerId, Integer prodId, String prodSku);

    /**
     *一个用户购物车所有商品数量
     */
    Integer countNumberByCustomerId(Integer customerId);

    /**
     *一个用户购物车所有商品数量（除了正在被修改的）
     */
    Integer countNumber(Integer customerId, List<Map<String, Object>> list);

    /**
     *根据ID数组查询购物车信息
     */
    List<Map<String,Object>> findShopcartByIds(int[] ids);
}
