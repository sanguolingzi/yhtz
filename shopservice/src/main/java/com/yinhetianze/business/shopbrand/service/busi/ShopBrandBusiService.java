package com.yinhetianze.business.shopbrand.service.busi;

import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;

public interface ShopBrandBusiService
{
    int addInfo(BusiShopBrandModel busiShopBrandModel) throws BusinessException;

    int updateByPrimaryKeySelective(BusiShopBrandPojo busiShopBrandPojo);

    int updateInfo(BusiShopBrandModel busiShopBrandModel) throws BusinessException;
}