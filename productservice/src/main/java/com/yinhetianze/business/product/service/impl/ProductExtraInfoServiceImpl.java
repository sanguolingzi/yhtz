package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductExtraInfoService;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductExtraInfoMapper;

@Service
public class ProductExtraInfoServiceImpl implements ProductExtraInfoService
{
    @Autowired
    private ProductExtraInfoMapper mapper;

    @Override
    public ProductExtraPojo findProductExtra(ProductExtraPojo pojo)
    {
        return mapper.selectOne(pojo);
    }
}