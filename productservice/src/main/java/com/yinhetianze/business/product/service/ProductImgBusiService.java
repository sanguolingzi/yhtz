package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductImgPojo;

public interface ProductImgBusiService
{
    int insertSelective(ProductImgPojo productImgPojo);

    void updateProductImgList(ProductImgPojo productImgPojoParam);
}