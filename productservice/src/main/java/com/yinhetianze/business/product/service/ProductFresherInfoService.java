package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.OneAreaPojo;
import com.yinhetianze.pojo.product.ProductFresherPojo;

import java.util.List;
import java.util.Map;

public interface ProductFresherInfoService
{
    ProductFresherPojo selectOne(ProductFresherPojo productFresherPojo);

    List<Map> selectProductFresherList(ProductFresherPojo productFresherPojo);

    List<Map> selectProductFresherDetails(ProductFresherPojo productFresherPojo);

    List<Map> selectBackstageProductFresherList(ProductFresherPojo productFresherPojo);
}