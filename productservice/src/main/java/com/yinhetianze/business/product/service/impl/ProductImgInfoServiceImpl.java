package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.model.ProductImgModel;
import com.yinhetianze.business.product.service.ProductImgInfoService;
import com.yinhetianze.pojo.product.ProductImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductImgInfoMapper;

import java.util.List;

@Service
public class ProductImgInfoServiceImpl implements ProductImgInfoService
{
    @Autowired
    private ProductImgInfoMapper mapper;

    @Override
    public List<ProductImgPojo> selectProductImgList(ProductImgPojo productImgPojo){
        return mapper.select(productImgPojo);
    }
}