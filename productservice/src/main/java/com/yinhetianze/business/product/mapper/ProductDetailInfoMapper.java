package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ProductDetailPojo;

import java.util.List;
import java.util.Map;

public interface ProductDetailInfoMapper extends InfoMapper<ProductDetailPojo>
{

    List<Map<String, Object>> getProductDetailList(Map<String, Object> params);

    List<Map<String,Object>> getShopProductDetail(Map<String, Object> detailParam);
}