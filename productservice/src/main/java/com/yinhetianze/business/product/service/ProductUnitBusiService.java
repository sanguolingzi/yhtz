package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;

import java.util.Map;

public interface ProductUnitBusiService
{
    int insertSelective(ProductUnitPojo productUnitPojo);

    int insertProductUnitid(ProductUnitPojo productUnitPojo);

    int updateProductUnit(Map<String,Object> map);


}