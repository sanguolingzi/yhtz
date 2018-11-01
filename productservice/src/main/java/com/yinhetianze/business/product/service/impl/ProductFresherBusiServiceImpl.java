package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductFresherBusiMapper;
import com.yinhetianze.business.product.service.ProductFresherBusiService;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductFresherBusiServiceImpl implements ProductFresherBusiService
{
    @Autowired
    private ProductFresherBusiMapper mapper;

    @Override
    public int addProductFresher(ProductFresherPojo productFresherPojo) {
        return mapper.insertSelective(productFresherPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ProductFresherPojo productFresherPojo) {
        return mapper.updateByPrimaryKeySelective(productFresherPojo);
    }

    @Override
    public int updateStorage(Map<String, Object> map) {
        return mapper.updateStorage(map);
    }
}