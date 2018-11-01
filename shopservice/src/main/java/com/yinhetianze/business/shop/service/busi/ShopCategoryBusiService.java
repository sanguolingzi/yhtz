package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;

public interface ShopCategoryBusiService
{
    int addShopCategory(ShopCategoryPojo shopCategoryPojo);

    int UpdateShopCategory(ShopCategoryPojo shopCategoryPojo);

    int updateInfo(ShopCategoryModel shopCategoryModel) throws BusinessException;

    int deleteInfo(ShopCategoryPojo shopCategoryPojo);
}