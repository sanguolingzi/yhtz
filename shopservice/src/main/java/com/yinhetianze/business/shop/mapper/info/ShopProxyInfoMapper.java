package com.yinhetianze.business.shop.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.ShopProxyPojo;

import java.util.List;
import java.util.Map;

public interface ShopProxyInfoMapper extends InfoMapper<ShopProxyPojo> {

    List<Map> selectShopProxyList(ShopProxyPojo shopProxyPojo);

    List<Map> getProductShopProxyList(ShopProxyPojo shopProxyPojo);
}