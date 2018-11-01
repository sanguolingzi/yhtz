package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductUnitBusiService;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductUnitBusiMapper;

import java.util.Map;

@Service
public class ProductUnitBusiServiceImpl implements ProductUnitBusiService
{
    @Autowired
    private ProductUnitBusiMapper mapper;

    @Override
    public int insertSelective(ProductUnitPojo productUnitPojo) {
        return mapper.insertSelective(productUnitPojo);
    }


    @Override
    public int insertProductUnitid(ProductUnitPojo productUnitPojo) {
        return mapper.addProductUnitid(productUnitPojo);
    }

    @Override
    public int updateProductUnit(Map<String,Object> map) {
        return mapper.updateProductUnit(map);
    }

}