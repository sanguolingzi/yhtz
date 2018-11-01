package com.yinhetianze.business.shophistory.service.busi.impl;

import com.yinhetianze.business.shophistory.mapper.busi.ShopHistoryBusiMapper;
import com.yinhetianze.business.shophistory.service.busi.ShopHistoryBusiService;
import com.yinhetianze.pojo.shop.BusiShopHistoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopHistoryBusiServiceImpl implements ShopHistoryBusiService
{
    @Autowired
    private ShopHistoryBusiMapper mapper;

    @Override
    public int addInfo(BusiShopHistoryPojo busiShopHistoryPojo) {
        return mapper.insertSelective(busiShopHistoryPojo);
    }
}