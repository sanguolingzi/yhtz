package com.yinhetianze.business.product.service;


import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import java.util.List;
import java.util.Map;
public interface ProductFresherRewardInfoService
{
    ProductFresherRewardPojo selectOne(ProductFresherRewardPojo productFresherRewardPojo);
    List<ProductFresherRewardPojo>selectProductFresherRewardId(Map map);
    ProductFresherRewardPojo selectFresherReward(ProductFresherRewardPojo productFresherRewardPojo);
}