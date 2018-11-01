package com.yinhetianze.business.parameter.mapper;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ProductParameterPojo;

import java.util.List;
import java.util.Map;

public interface ProductParameterInfoMapper extends InfoMapper<ProductParameterPojo> {

    List<ProductParameterPojo> getParameterList(Map<String, Object> param);

    List<Map> getProductCategoryIdParameter(ProductParameterModel paramModel);
}