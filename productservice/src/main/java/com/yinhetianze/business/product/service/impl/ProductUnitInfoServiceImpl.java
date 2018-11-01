package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductUnitInfoMapper;
import com.yinhetianze.business.product.service.ProductUnitInfoService;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUnitInfoServiceImpl implements ProductUnitInfoService {

    @Autowired
    private ProductUnitInfoMapper mapper;

    @Override
    public ProductUnitPojo selectOne(ProductUnitPojo productUnitPojo) {
        return mapper.selectOne(productUnitPojo);
    }
}
