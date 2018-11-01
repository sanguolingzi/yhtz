package com.yinhetianze.business.shoppingcart.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShopcartInfoMapper {

    /**
     * 根据ID查询购物车
     * @param id
     * @return
     */
    Map<String,Object> findShopcartById(@Param("id") Integer id);

    /**
     * 根据customerId查询购物车信息
     */
    List<Map<String,Object>> findShopcartByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 根据用户编码和产品ID查询购物车信息
     */
    Map<String,Object> findShopcart(@Param("customerId") Integer customerId, @Param("prodId") Integer prodDetailId, @Param("prodSku") String prodSku);

    /**
     *一个用户购物车所有商品数量
     */
    Integer countNumberByCustomerId(@Param("customerId") Integer customerId);

    /**
     *一个用户购物车所有商品数量（除了正在被修改的）
     */
    Integer countNumber(@Param("customerId") Integer customerId, @Param("list") List<Map<String, Object>> list);

    /**
     *根据ID数组查询购物车信息
     */
    List<Map<String,Object>> findShopcartByIds(int[] ids);
}
