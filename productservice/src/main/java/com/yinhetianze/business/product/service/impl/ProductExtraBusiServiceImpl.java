package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductExtraBusiService;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductExtraBusiMapper;

@Service
public class ProductExtraBusiServiceImpl implements ProductExtraBusiService
{
    @Autowired
    private ProductExtraBusiMapper mapper;

    @Override
    public int addProductExtra(ProductExtraPojo pojo)
    {
        return mapper.insert(pojo);
    }

    @Override
    public int modifyProductExtra(ProductExtraPojo pojo)
    {
        return mapper.updateByPrimaryKeySelective(pojo);
    }

    @Override
    public int deleteProductExtra(ProductExtraPojo pojo)
    {
        return mapper.deleteByPrimaryKey(pojo);
    }
}