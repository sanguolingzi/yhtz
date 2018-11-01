package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductPageModel;
import com.yinhetianze.pojo.product.ProductPojo;

import java.util.List;
import java.util.Map;

public interface ProductInfoService
{
    List<ProductPojo> getProductList(Map<String, Object> params);

    ProductPojo findProduct(ProductPojo pojo);

    List<Map> getSearchGoods(ProductModel productModel);

    int getProductBrandId(ProductPojo productPojo);

    List<Map>getPcProductList(ProductPageModel productPageModel);

    List<Map> selectProductList(ProductPojo productPojo);

    List<ProductPojo> putawayProduct(ProductPojo upProductPojo);

 	List<Map>getMoBileProductList(ProductPageModel productPageModel);

  	Map findShopProduct(ProductPojo prodPojo);

    List<Map>selectSellPriceProductList();

}