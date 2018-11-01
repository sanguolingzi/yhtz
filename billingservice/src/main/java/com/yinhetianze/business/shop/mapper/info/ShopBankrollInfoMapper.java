package com.yinhetianze.business.shop.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;

public interface ShopBankrollInfoMapper extends InfoMapper<BusiShopBankrollPojo> {

    BusiShopBankrollPojo selectByShopId(Integer id);
}