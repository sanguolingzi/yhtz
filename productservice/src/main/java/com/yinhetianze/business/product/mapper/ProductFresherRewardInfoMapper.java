package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;

import java.util.List;
import java.util.Map;

public interface ProductFresherRewardInfoMapper extends InfoMapper<ProductFresherRewardPojo> {
    List<ProductFresherRewardPojo>selectProductFresherRewardId(Map map);
}