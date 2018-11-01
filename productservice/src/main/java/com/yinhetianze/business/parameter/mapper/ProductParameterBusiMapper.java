package com.yinhetianze.business.parameter.mapper;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ProductParameterPojo;

import java.util.List;
import java.util.Map;

public interface ProductParameterBusiMapper extends BusiMapper<ProductParameterPojo> {

    int deleteProductParameterRelaByParamId(Integer paramId);

    int deleteProductParameterRelaByCateId(Integer cateId);

    int bindProductCateParamRela(List<Map<String, Object>> relaList);

}