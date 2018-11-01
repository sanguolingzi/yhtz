package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductImgPojo;

public interface ProductImgBusiMapper extends BusiMapper<ProductImgPojo> {
    void updateProductImgList(ProductImgPojo productImgPojoParam);
}