package com.yinhetianze.business.shop.service.info;

import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;

public interface ShopBankrollInfoService
{
    BusiShopBankrollPojo selectByShopId(Integer id);
}