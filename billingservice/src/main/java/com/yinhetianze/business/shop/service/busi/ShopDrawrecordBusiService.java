package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import com.yinhetianze.pojo.shop.BusiShopDrawrecordPojo;

public interface ShopDrawrecordBusiService
{
    int insert(BusiShopDrawrecordPojo record);

    int insertSelective(BusiShopDrawrecordPojo record);

    int updateByPrimaryKeySelective(BusiShopDrawrecordPojo record);

    int updateByPrimaryKey(BusiShopDrawrecordPojo record);

    int AddShopDrawrecordInfo(BusiShopBankrollPojo busiShopBankrollPojo,
                              BusiShopDrawrecordPojo busiShopDrawrecordPojo) throws BusinessException;
}