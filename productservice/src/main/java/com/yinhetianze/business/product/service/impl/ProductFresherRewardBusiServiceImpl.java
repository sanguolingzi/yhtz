package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductFresherRewardBusiMapper;
import com.yinhetianze.business.product.mapper.ProductFresherRewardInfoMapper;
import com.yinhetianze.business.product.service.ProductFresherRewardBusiService;
import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductFresherRewardBusiServiceImpl implements ProductFresherRewardBusiService
{
    @Autowired
    private ProductFresherRewardBusiMapper mapper;

    @Override
    public int updateByPrimaryKeySelective(ProductFresherRewardPojo productFresherRewardPojo) {
        return mapper.updateByPrimaryKeySelective(productFresherRewardPojo);
    }

    @Override
    public int updateStatus(Map<String, Object> map) {
        return mapper.updateStatus(map);
    }

    @Override
    public int insertSelective(ProductFresherRewardPojo fresherRewardPojo){
        return mapper.insertSelective(fresherRewardPojo);
    }
}