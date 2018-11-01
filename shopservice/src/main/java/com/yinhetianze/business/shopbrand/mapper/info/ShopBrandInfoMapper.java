package com.yinhetianze.business.shopbrand.mapper.info;

import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;

import java.util.List;

public interface ShopBrandInfoMapper extends InfoMapper<BusiShopBrandPojo> {

    List<BusiShopBrandModel> selectList(BusiShopBrandModel busiShopBrandModel);

}