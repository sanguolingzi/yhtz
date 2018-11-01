package com.yinhetianze.business.shop.service.info;

import com.yinhetianze.business.shop.model.BusiShopPageModel;
import com.yinhetianze.business.shop.model.BusiShopSearchModel;
import com.yinhetianze.pojo.shop.BusiShopPojo;

import java.util.List;
import java.util.Map;

public interface ShopInfoService
{
    BusiShopPojo selectOne(BusiShopPojo record);

    BusiShopPojo selectByCustomerId(Integer customerId);

    List<BusiShopSearchModel> selectShopInfoList(BusiShopSearchModel busiShopSearchModel);

    List<BusiShopPageModel> selectList(BusiShopPageModel busiShopPageModel);

    List<Map> selectShopGuessList();

    List<Map> selectSelfSupportShop(BusiShopPojo busiShopPojo);
}