package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;

import java.util.Map;

public interface ProductFresherRewardBusiMapper extends BusiMapper<ProductFresherRewardPojo> {

    int updateStatus(Map<String,Object> map);
}