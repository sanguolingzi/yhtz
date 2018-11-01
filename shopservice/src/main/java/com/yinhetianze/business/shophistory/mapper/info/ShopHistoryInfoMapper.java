package com.yinhetianze.business.shophistory.mapper.info;

import com.yinhetianze.business.shophistory.model.BusiShopHistoryModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.shop.BusiShopHistoryPojo;

public interface ShopHistoryInfoMapper extends InfoMapper<BusiShopHistoryPojo> {

    BusiShopHistoryPojo selectShopHis(BusiShopHistoryModel busiShopHistoryModel);
}