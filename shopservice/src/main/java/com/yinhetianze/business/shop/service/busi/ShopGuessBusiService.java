package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.pojo.shop.ShopGuessPojo;

public interface ShopGuessBusiService
{
    int insertSelective(ShopGuessPojo shopGuessPojo);

    int updateByPrimaryKeySelective(ShopGuessPojo shopGuessPojo);
}