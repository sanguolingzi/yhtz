package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;

import java.util.Map;


public interface ProductUnitBusiMapper extends BusiMapper<ProductUnitPojo> {

    int addProductUnitid(ProductUnitPojo productUnitPojo);
    int updateProductUnit(Map<String,Object> map);
}