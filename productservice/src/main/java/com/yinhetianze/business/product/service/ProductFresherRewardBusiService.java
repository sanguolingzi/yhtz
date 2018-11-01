package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductFresherRewardPojo;

import java.util.Map;

public interface ProductFresherRewardBusiService
{
    int updateByPrimaryKeySelective(ProductFresherRewardPojo productFresherRewardPojo);

    /**
     * 下单后更新状态
     * @param map
     * @return
     */
    int updateStatus(Map<String,Object> map);


    int insertSelective(ProductFresherRewardPojo fresherRewardPojo);
}