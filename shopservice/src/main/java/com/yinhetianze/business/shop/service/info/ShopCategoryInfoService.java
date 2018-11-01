package com.yinhetianze.business.shop.service.info;

import com.yinhetianze.pojo.shop.ShopCategoryPojo;

import java.util.List;

public interface ShopCategoryInfoService
{
    List<ShopCategoryPojo> getShopCategory(ShopCategoryPojo shopCategoryPojo);

    ShopCategoryPojo getOneShopCategory(ShopCategoryPojo shopCategoryPojo);
}