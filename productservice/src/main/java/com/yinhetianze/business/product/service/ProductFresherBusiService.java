package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductFresherPojo;

import java.util.Map;

public interface ProductFresherBusiService
{
    int addProductFresher(ProductFresherPojo productFresherPojo);

    int updateByPrimaryKeySelective(ProductFresherPojo productFresherPojo);

    int updateStorage(Map<String,Object> map);
}