package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.ProductImgModel;
import com.yinhetianze.pojo.product.ProductImgPojo;

import java.util.List;

public interface ProductImgInfoService
{
    List<ProductImgPojo> selectProductImgList(ProductImgPojo productImgPojo);
}