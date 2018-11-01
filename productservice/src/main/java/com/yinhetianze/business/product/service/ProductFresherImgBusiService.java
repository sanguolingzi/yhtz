package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductFresherImgPojo;

public interface ProductFresherImgBusiService
{
    int insertSelective( ProductFresherImgPojo productFresherImgPojo);

    int updateProductFresherImgPojoList(ProductFresherImgPojo productFresherImgPojo);
}