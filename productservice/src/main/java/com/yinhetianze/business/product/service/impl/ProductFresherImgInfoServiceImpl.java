package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductFresherImgInfoService;
import com.yinhetianze.pojo.product.ProductFresherImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductFresherImgInfoMapper;

import java.util.List;

@Service
public class ProductFresherImgInfoServiceImpl implements ProductFresherImgInfoService
{
    @Autowired
    private ProductFresherImgInfoMapper mapper;

    @Override
    public List<ProductFresherImgPojo> selectProductFresherImgList(ProductFresherImgPojo productFresherImgPojo) {
        productFresherImgPojo.setDelFlag((short)0);
        return mapper.select(productFresherImgPojo);
    }
}