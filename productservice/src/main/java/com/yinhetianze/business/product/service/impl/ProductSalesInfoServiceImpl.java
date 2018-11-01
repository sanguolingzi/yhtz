package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductSalesInfoService;
import com.yinhetianze.pojo.product.ProductSalesPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductSalesInfoMapper;

@Service
public class ProductSalesInfoServiceImpl implements ProductSalesInfoService
{
    @Autowired
    private ProductSalesInfoMapper mapper;

    @Override
    public ProductSalesPojo selectOne(ProductSalesPojo productSalesPojo) {
        return mapper.selectOne(productSalesPojo);
    }
}