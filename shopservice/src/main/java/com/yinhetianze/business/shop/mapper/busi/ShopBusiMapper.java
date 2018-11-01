package com.yinhetianze.business.shop.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.shop.BusiShopPojo;

public interface ShopBusiMapper extends BusiMapper<BusiShopPojo> {
    int addShopVisitCount(Integer id);
}