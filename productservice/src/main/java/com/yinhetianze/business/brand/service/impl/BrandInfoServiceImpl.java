package com.yinhetianze.business.brand.service.impl;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.pojo.brand.BrandPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.brand.mapper.BrandInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class BrandInfoServiceImpl implements BrandInfoService
{
    @Autowired
    private BrandInfoMapper mapper;

    @Override
    public List<BrandPojo> getBrandList(Map<String, Object> param)
    {
        return mapper.getBrandList(param);
    }

    @Override
    public BrandPojo findBrand(BrandPojo pojo)
    {
        return mapper.selectOne(pojo);
    }

    @Override
    public List<Map> selectBrandList(ProductModel productModel) {
        return mapper.selectBrandList(productModel);
    }
}