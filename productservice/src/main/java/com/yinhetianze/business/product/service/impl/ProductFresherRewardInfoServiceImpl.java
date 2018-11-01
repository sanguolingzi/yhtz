package com.yinhetianze.business.product.service.impl;


import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductFresherRewardInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductFresherRewardInfoServiceImpl implements ProductFresherRewardInfoService
{
    @Autowired
    private ProductFresherRewardInfoMapper mapper;


    @Override
    public ProductFresherRewardPojo selectOne(ProductFresherRewardPojo productFresherRewardPojo) {
        return mapper.selectOne(productFresherRewardPojo);
    }

    @Override
    public List<ProductFresherRewardPojo> selectProductFresherRewardId(Map map) {
        return mapper.selectProductFresherRewardId(map);
    }

    @Override
    public ProductFresherRewardPojo selectFresherReward(ProductFresherRewardPojo productFresherRewardPojo){
        return mapper.selectOne(productFresherRewardPojo);
    }
}