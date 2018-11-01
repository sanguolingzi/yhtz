package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;

import java.util.List;
import java.util.Map;

public interface ProductDetailInfoService
{
    List<Map<String, Object>> getProductDetail(Map<String, Object> params);


    ProductDetailPojo selectProductDetailPojo(ProductDetailPojo productDetailPojo);

    List<ProductDetailPojo> productDetailList(ProductDetailPojo productDetailPojo);

    List<Map<String,Object>> getShopProductDetail(Map<String, Object> detailParam);
}