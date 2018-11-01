package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;

import java.util.List;
import java.util.Map;

public interface ProductBusiMapper extends BusiMapper<ProductPojo> {

    int deleteProduct(Integer productId);

    int updateStorage(Map<String,Object> map);

    int addProductSales(Map map);
}