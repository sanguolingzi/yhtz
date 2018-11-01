package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductDetailInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductDetailInfoServiceImpl implements ProductDetailInfoService
{
    @Autowired
    private ProductDetailInfoMapper mapper;

    @Override
    public List<Map<String, Object>> getProductDetail(Map<String, Object> params)
    {
        return mapper.getProductDetailList(params);
    }

    @Override
    public ProductDetailPojo selectProductDetailPojo(ProductDetailPojo productDetailPojo){
        return mapper.selectOne(productDetailPojo);
    }

    @Override
    public List<ProductDetailPojo> productDetailList(ProductDetailPojo productDetailPojo){
        return mapper.select(productDetailPojo);
    }

    @Override
    public List<Map<String,Object>> getShopProductDetail(Map<String,Object> detailParam){
        return mapper.getShopProductDetail(detailParam);
    }
}