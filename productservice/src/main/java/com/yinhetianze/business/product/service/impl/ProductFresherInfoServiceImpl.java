package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductFresherInfoService;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductFresherInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductFresherInfoServiceImpl implements ProductFresherInfoService
{
    @Autowired
    private ProductFresherInfoMapper mapper;

    @Override
    public ProductFresherPojo selectOne(ProductFresherPojo productFresherPojo) {
        productFresherPojo.setDelFlag((short)0);
        return mapper.selectOne(productFresherPojo);
    }

    @Override
    public List<Map> selectProductFresherList(ProductFresherPojo productFresherPojo) {
        return mapper.selectProductFresherList(productFresherPojo);
    }

    @Override
    public List<Map> selectProductFresherDetails(ProductFresherPojo productFresherPojo) {
        return mapper.selectProductFresherDetails(productFresherPojo);
    }

    @Override
    public List<Map> selectBackstageProductFresherList(ProductFresherPojo productFresherPojo) {
        return mapper.selectBackstageProductFresherList(productFresherPojo);
    }
}