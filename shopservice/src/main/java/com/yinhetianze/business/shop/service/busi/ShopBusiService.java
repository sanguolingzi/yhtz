package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.business.shop.model.ShopCompanyPageModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.shop.BusiShopPojo;

public interface ShopBusiService
{
    int insert(BusiShopPojo record);

    int insertSelective(BusiShopPojo record);

    int updateByPrimaryKeySelective(BusiShopPojo record);

    int updateByPrimaryKey(BusiShopPojo record);

    int addShopInfo(BusiShopPojo record) throws BusinessException;

    int updateShopInfo(BusiShopPojo record) throws BusinessException;

    int addShopVisitCount(Integer id);

    int updateShopCompanyInfo(ShopCompanyPageModel shopCompanyPageModel) throws BusinessException;
}