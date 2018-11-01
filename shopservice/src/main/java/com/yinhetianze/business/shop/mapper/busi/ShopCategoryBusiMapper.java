package com.yinhetianze.business.shop.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;

public interface ShopCategoryBusiMapper extends BusiMapper<ShopCategoryPojo> {

    int updateByCondition(ShopCategoryPojo shopCategoryPojo);
}