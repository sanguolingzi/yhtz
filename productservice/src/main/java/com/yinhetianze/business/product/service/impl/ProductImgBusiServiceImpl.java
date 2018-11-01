package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductImgBusiMapper;
import com.yinhetianze.business.product.service.ProductImgBusiService;
import com.yinhetianze.pojo.product.ProductImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImgBusiServiceImpl implements ProductImgBusiService
{
    @Autowired
    private ProductImgBusiMapper mapper;

    @Override
    public int insertSelective(ProductImgPojo productImgPojo){
        return mapper.insertSelective(productImgPojo);
    }

    @Override
    public void updateProductImgList(ProductImgPojo productImgPojoParam){
       //mapper.updateProductImgList(productImgPojoParam);
        mapper.updateByPrimaryKeySelective(productImgPojoParam);
    }
}