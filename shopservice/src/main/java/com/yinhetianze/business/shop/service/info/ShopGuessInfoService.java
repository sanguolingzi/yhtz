package com.yinhetianze.business.shop.service.info;

import com.yinhetianze.pojo.shop.ShopGuessPojo;

import java.util.List;
import java.util.Map;

public interface ShopGuessInfoService
{
    List<Map> selectShopGuessList();
    List<Map> selectBackstageShopGuessList();
    ShopGuessPojo selectOne(ShopGuessPojo shopGuessPojo);
}