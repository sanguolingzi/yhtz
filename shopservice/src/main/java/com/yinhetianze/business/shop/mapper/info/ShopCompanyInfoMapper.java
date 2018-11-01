package com.yinhetianze.business.shop.mapper.info;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.model.BusiShopCompanyPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;

import java.util.List;

public interface ShopCompanyInfoMapper extends InfoMapper<BusiShopCompanyPojo> {

    List<BusiShopCompanyModel> selectList(BusiShopCompanyPageModel busiShopCompanyPageModel);
}