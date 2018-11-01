package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ProductFresherPojo;

import java.util.List;
import java.util.Map;

public interface ProductFresherInfoMapper extends InfoMapper<ProductFresherPojo> {
    List<Map> selectProductFresherList(ProductFresherPojo productFresherPojo);

    List<Map> selectProductFresherDetails(ProductFresherPojo productFresherPojo);

    List<Map> selectBackstageProductFresherList(ProductFresherPojo productFresherPojo);
}