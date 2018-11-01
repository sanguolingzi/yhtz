package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;

import java.math.BigDecimal;

public interface ShopBankrollBusiService
{
    int insert(BusiShopBankrollPojo record);

    int insertSelective(BusiShopBankrollPojo record);

    int updateByPrimaryKeySelective(BusiShopBankrollPojo record);

    int updateByPrimaryKey(BusiShopBankrollPojo record);

    int updateGoodsAmount(Integer id, BigDecimal goodsAmount, BigDecimal oldGoodsAmout);

    int add(BusiShopBankrollPojo record);
}