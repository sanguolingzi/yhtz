package com.yinhetianze.business.shopbrand.service.info;

import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;

import java.util.List;

public interface ShopBrandInfoService
{
    List<BusiShopBrandModel> selectList(BusiShopBrandModel busiShopBrandModel);

    BusiShopBrandPojo selectOne(BusiShopBrandPojo busiShopBrandPojo);
}