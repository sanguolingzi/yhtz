package com.yinhetianze.business.shop.service.info.impl;

import com.yinhetianze.business.shop.service.info.ShopGuessInfoService;
import com.yinhetianze.pojo.shop.ShopGuessPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shop.mapper.info.ShopGuessInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ShopGuessInfoServiceImpl implements ShopGuessInfoService
{
    @Autowired
    private ShopGuessInfoMapper mapper;

    @Override
    public List<Map> selectShopGuessList() {
        return mapper.selectShopGuessList();
    }

    @Override
    public List<Map> selectBackstageShopGuessList() {
        return mapper.selectBackstageShopGuessList();
    }

    @Override
    public ShopGuessPojo selectOne(ShopGuessPojo shopGuessPojo) {
        return mapper.selectOne(shopGuessPojo);
    }
}