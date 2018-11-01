package com.yinhetianze.business.shop.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.shop.ShopGuessPojo;

import java.util.List;
import java.util.Map;

public interface ShopGuessInfoMapper extends InfoMapper<ShopGuessPojo> {
    List<Map> selectShopGuessList();
    List<Map> selectBackstageShopGuessList();
}