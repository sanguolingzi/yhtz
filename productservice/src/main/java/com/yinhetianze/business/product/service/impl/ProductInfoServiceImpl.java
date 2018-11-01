package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductPageModel;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductInfoServiceImpl implements ProductInfoService
{
    @Autowired
    private ProductInfoMapper mapper;

    @Override
    public List<ProductPojo> getProductList(Map<String, Object> params)
    {
        return mapper.getProductList(params);
    }

    @Override
    public ProductPojo findProduct(ProductPojo pojo)
    {
        return mapper.selectOne(pojo);
    }

    @Override
    public List<Map> getSearchGoods(ProductModel productModel){
        return mapper.getSearchGoods(productModel);
    }

    @Override
    public int getProductBrandId(ProductPojo productPojo) {
        return mapper.getProductBrandId(productPojo);
    }

    @Override
    public List<Map> getPcProductList(ProductPageModel productPageModel) {
        return mapper.getPcProductList(productPageModel);
    }

    @Override
    public List<Map> selectProductList(ProductPojo productPojo) {
        return mapper.selectProductList(productPojo);
    }

    @Override
    public List<ProductPojo> putawayProduct(ProductPojo upProductPojo){
        return mapper.putawayProduct(upProductPojo);
    }

    @Override
    public List<Map> getMoBileProductList(ProductPageModel productPageModel) {
        return mapper.getMoBileProductList(productPageModel);
    }   
	
	@Override
    public Map findShopProduct(ProductPojo prodPojo){
        return mapper.findShopProduct(prodPojo);
    }

    @Override
    public List<Map> selectSellPriceProductList() {
        return mapper.selectSellPriceProductList();
    }
}