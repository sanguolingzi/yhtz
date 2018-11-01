package com.yinhetianze.business.shopbrand.service.info.impl;

import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.business.shopbrand.service.info.ShopBrandInfoService;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shopbrand.mapper.info.ShopBrandInfoMapper;

import java.util.List;

@Service
public class ShopBrandInfoServiceImpl implements ShopBrandInfoService
{
    @Autowired
    private ShopBrandInfoMapper shopBrandInfoMapper;

    @Override
    public List<BusiShopBrandModel> selectList(BusiShopBrandModel busiShopBrandModel) {
        return shopBrandInfoMapper.selectList(busiShopBrandModel);
    }

    @Override
    public BusiShopBrandPojo selectOne(BusiShopBrandPojo busiShopBrandPojo) {
        busiShopBrandPojo.setDelFlag((short)0);
        return shopBrandInfoMapper.selectOne(busiShopBrandPojo);
    }
}