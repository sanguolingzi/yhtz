package com.yinhetianze.business.parameter.service.impl;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.parameter.mapper.ProductParameterInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductParameterInfoServiceImpl implements ProductParameterInfoService
{
    @Autowired
    private ProductParameterInfoMapper mapper;

    @Override
    public List<ProductParameterPojo> getProductParameterList(ProductParameterPojo pojo)
    {
        return mapper.select(pojo);
    }

    @Override
    public List<ProductParameterPojo> getProductParameterList(Map<String, Object> pojo)
    {
        return mapper.getParameterList(pojo);
    }

    @Override
    public ProductParameterPojo findProductParameter(ProductParameterPojo pojo)
    {
        return mapper.selectOne(pojo);
    }

    @Override
    public List<Map> getProductCategoryIdParameter(ProductParameterModel paramModel){
        return mapper.getProductCategoryIdParameter(paramModel);
    }
}