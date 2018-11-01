package com.yinhetianze.business.shop.service.info.impl;

import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shop.mapper.info.ShopProxyInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ShopProxyInfoServiceImpl implements ShopProxyInfoService
{
    @Autowired
    private ShopProxyInfoMapper mapper;

    @Override
    public List<Map> selectShopProxyList(ShopProxyPojo shopProxyPojo) {
        return mapper.selectShopProxyList(shopProxyPojo);
    }

    @Override
    public ShopProxyPojo selectOne(ShopProxyPojo shopProxyPojo) {
        return mapper.selectOne(shopProxyPojo);
    }

    @Override
    public List<Map> getProductShopProxyList(ShopProxyPojo shopProxyPojo){
        return mapper.getProductShopProxyList(shopProxyPojo);
    }
}