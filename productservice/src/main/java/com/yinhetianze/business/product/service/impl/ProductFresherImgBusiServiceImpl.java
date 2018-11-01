package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductFresherImgBusiMapper;
import com.yinhetianze.business.product.mapper.ProductFresherImgInfoMapper;
import com.yinhetianze.business.product.service.ProductFresherImgBusiService;
import com.yinhetianze.business.product.service.ProductFresherImgInfoService;
import com.yinhetianze.pojo.product.ProductFresherImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFresherImgBusiServiceImpl implements ProductFresherImgBusiService
{
    @Autowired
    private ProductFresherImgBusiMapper mapper;

    @Override
    public int insertSelective(ProductFresherImgPojo productFresherImgPojo) {
        return mapper.insertSelective(productFresherImgPojo);
    }

    @Override
    public int updateProductFresherImgPojoList(ProductFresherImgPojo productFresherImgPojo) {
       return mapper.updateByPrimaryKeySelective(productFresherImgPojo);
    }
}