package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ProductFresherPojo;

import java.util.Map;

public interface ProductFresherBusiMapper extends BusiMapper<ProductFresherPojo> {

    int updateStorage(Map<String,Object> map);
}