package com.yinhetianze.business.product.mapper;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ProductPojo;

import java.util.List;
import java.util.Map;

public interface ProductInfoMapper extends InfoMapper<ProductPojo> {

    List<ProductPojo> getProductList(Map<String, Object> params);

    List<Map> getSearchGoods(ProductModel productModel);
    int getProductBrandId(ProductPojo productPojo);
    List<Map>getPcProductList(ProductPageModel productPageModel);
    List<Map>getMoBileProductList(ProductPageModel productPageModel);
    List<Map> selectProductList(ProductPojo productPojo);
    List<ProductPojo> putawayProduct(ProductPojo upProductPojo);
    Map findShopProduct(ProductPojo prodPojo);
    List<Map>selectSellPriceProductList();
}