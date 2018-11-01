package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductExtraPojo;

import java.util.Map;

public interface ProductExtraBusiService
{

    int addProductExtra(ProductExtraPojo pojo);

    int modifyProductExtra(ProductExtraPojo pojo);

    int deleteProductExtra(ProductExtraPojo pojo);

}