package com.yinhetianze.business.brand.mapper;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.brand.BrandPojo;

import java.util.List;
import java.util.Map;

public interface BrandInfoMapper extends InfoMapper<BrandPojo> {

    List<BrandPojo> getBrandList(Map<String, Object> param);

    List<Map> selectBrandList(ProductModel productModel);

}