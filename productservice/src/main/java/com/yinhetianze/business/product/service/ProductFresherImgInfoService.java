package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductFresherImgPojo;

import java.util.List;

public interface ProductFresherImgInfoService
{
    List<ProductFresherImgPojo> selectProductFresherImgList(ProductFresherImgPojo productFresherImgPojo);
}