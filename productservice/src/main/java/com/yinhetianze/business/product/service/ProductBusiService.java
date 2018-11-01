package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;

import java.util.List;
import java.util.Map;

public interface ProductBusiService
{
    int addProduct(ProductPojo pojo);

    int modifyProduct(ProductPojo pojo);

    int deleteProduct(ProductPojo pojo);

    int addProductDetails(ProductPojo prodPojo);

    int updateTotalStorage(ProductPojo prodPojo);

    int addProductSales(Map map);
}