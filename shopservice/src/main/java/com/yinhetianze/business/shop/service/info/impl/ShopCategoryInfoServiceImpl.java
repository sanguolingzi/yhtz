package com.yinhetianze.business.shop.service.info.impl;

import com.yinhetianze.business.shop.service.info.ShopCategoryInfoService;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shop.mapper.info.ShopCategoryInfoMapper;

import java.util.List;

@Service
public class ShopCategoryInfoServiceImpl implements ShopCategoryInfoService
{
    @Autowired
    private ShopCategoryInfoMapper mapper;

    @Override
    public List<ShopCategoryPojo> getShopCategory(ShopCategoryPojo shopCategoryPojo){
        shopCategoryPojo.setDelFlag((short)0);
        return mapper.select(shopCategoryPojo);
    }

    @Override
    public ShopCategoryPojo getOneShopCategory(ShopCategoryPojo shopCategoryPojo){
        return mapper.selectOne(shopCategoryPojo);
    }
}