package com.yinhetianze.business.shop.mapper.info;

import com.yinhetianze.business.shop.model.BusiShopPageModel;
import com.yinhetianze.business.shop.model.BusiShopSearchModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import java.util.List;
import java.util.Map;


public interface ShopInfoMapper extends InfoMapper<BusiShopPojo> {
    List<BusiShopSearchModel> selectShopInfoList(BusiShopSearchModel busiShopSearchModel);

    /**
     * 后台 店铺审核列表
     * 限制条件 店铺关联的企业 需是审核通过状态
     * @param busiShopPageModel
     * @return
     */
    List<BusiShopPageModel> selectList(BusiShopPageModel busiShopPageModel);

    List<Map> selectShopGuessList();

    List<Map> selectSelfSupportShop(BusiShopPojo busiShopPojo);
}