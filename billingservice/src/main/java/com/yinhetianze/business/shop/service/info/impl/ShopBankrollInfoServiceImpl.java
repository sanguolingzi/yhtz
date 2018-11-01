package com.yinhetianze.business.shop.service.info.impl;

import com.yinhetianze.business.shop.mapper.info.ShopBankrollInfoMapper;
import com.yinhetianze.business.shop.service.info.ShopBankrollInfoService;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopBankrollInfoServiceImpl implements ShopBankrollInfoService {
    @Autowired
    private ShopBankrollInfoMapper shopBankrollInfoMapper;

    @Override
    public BusiShopBankrollPojo selectByShopId(Integer id) {
        return shopBankrollInfoMapper.selectByShopId(id);
    }
}