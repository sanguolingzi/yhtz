package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.pojo.shop.BusiShopBankrollFlowPojo;

public interface ShopBankrollFlowBusiService
{
    int insert(BusiShopBankrollFlowPojo record);

    int insertSelective(BusiShopBankrollFlowPojo record);

    int updateByPrimaryKeySelective(BusiShopBankrollFlowPojo record);

    int updateByPrimaryKey(BusiShopBankrollFlowPojo record);
}