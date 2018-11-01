package com.yinhetianze.business.shop.service.info;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.model.BusiShopCompanyPageModel;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import java.util.List;

public interface ShopCompanyInfoService
{
    BusiShopCompanyPojo selectOne(BusiShopCompanyPojo record);

    BusiShopCompanyPojo selectByCustomerId(Integer customerId);

    List<BusiShopCompanyModel>  selectList(BusiShopCompanyPageModel busiShopCompanyPageModel);

    BusiShopCompanyPojo selectByCondition(BusiShopCompanyPojo record);
}