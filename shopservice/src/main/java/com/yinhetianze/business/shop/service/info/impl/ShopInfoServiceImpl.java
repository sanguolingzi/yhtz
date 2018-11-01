package com.yinhetianze.business.shop.service.info.impl;

import com.yinhetianze.business.shop.model.BusiShopPageModel;
import com.yinhetianze.business.shop.model.BusiShopSearchModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shop.mapper.info.ShopInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ShopInfoServiceImpl implements ShopInfoService
{
    @Autowired
    private ShopInfoMapper shopInfoMapper;

    @Override
    public BusiShopPojo selectOne(BusiShopPojo record) {
        record.setDelFlag((short)0);
        return shopInfoMapper.selectOne(record);
    }


    @Override
    public BusiShopPojo selectByCustomerId(Integer customerId) {
        BusiShopPojo record = new BusiShopPojo();
        record.setCustomerId(customerId);
        return selectOne(record);
    }

    @Override
    public List<BusiShopSearchModel> selectShopInfoList(BusiShopSearchModel busiShopSearchModel) {
        return shopInfoMapper.selectShopInfoList(busiShopSearchModel);
    }

    @Override
    public List<BusiShopPageModel> selectList(BusiShopPageModel busiShopPageModel) {
        return shopInfoMapper.selectList(busiShopPageModel);
    }

    @Override
    public List<Map> selectShopGuessList() {
        return shopInfoMapper.selectShopGuessList();
    }

    @Override
    public List<Map> selectSelfSupportShop(BusiShopPojo busiShopPojo) {
        return shopInfoMapper.selectSelfSupportShop(busiShopPojo);
    }
}