package com.yinhetianze.business.shop.service.busi;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;

public interface ShopCompanyBusiService
{
    int insert(BusiShopCompanyPojo record);

    int insertSelective(BusiShopCompanyPojo record);

    int updateByPrimaryKeySelective(BusiShopCompanyPojo record);

    int updateByPrimaryKey(BusiShopCompanyPojo record);

    int addInfo(BusiShopCompanyModel busiShopCompanyModel)  throws BusinessException;

    int updateInfo(BusiShopCompanyModel busiShopCompanyModel) throws BusinessException;
}