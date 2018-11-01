package com.yinhetianze.business.shop.service.info;

import com.yinhetianze.pojo.back.ShopProxyPojo;

import java.util.List;
import java.util.Map;

public interface ShopProxyInfoService
{
    List<Map> selectShopProxyList(ShopProxyPojo shopProxyPojo);
    ShopProxyPojo selectOne(ShopProxyPojo shopProxyPojo);

    /**
     * 添加商品时店铺查询使用
     * @param shopProxyPojo
     * @return
     */
    List<Map> getProductShopProxyList(ShopProxyPojo shopProxyPojo);
}