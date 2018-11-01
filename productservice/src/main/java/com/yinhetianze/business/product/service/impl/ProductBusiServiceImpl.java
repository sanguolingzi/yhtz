package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductBusiMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductBusiServiceImpl implements ProductBusiService
{
    @Autowired
    private ProductBusiMapper mapper;

    @Override
    public int addProduct(ProductPojo pojo)
    {
        return mapper.insertSelective(pojo);
    }

    @Override
    public int modifyProduct(ProductPojo pojo)
    {
        return mapper.updateByPrimaryKeySelective(pojo);
    }

    @Override
    public int deleteProduct(ProductPojo pojo)
    {
        return mapper.deleteProduct(pojo.getId());
    }

    @Override
    public int addProductDetails(ProductPojo prodPojo){
        return mapper.insert(prodPojo);
    }

    @Override
    public int updateTotalStorage(ProductPojo prodPojo){
        return mapper.updateByPrimaryKeySelective(prodPojo);
    }

    @Override
    public int addProductSales(Map map){
        return mapper.addProductSales(map);
    }
}