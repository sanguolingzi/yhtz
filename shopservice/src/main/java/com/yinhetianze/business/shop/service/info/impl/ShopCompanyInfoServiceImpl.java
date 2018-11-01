package com.yinhetianze.business.shop.service.info.impl;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.model.BusiShopCompanyPageModel;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shop.mapper.info.ShopCompanyInfoMapper;

import java.util.List;

@Service
public class ShopCompanyInfoServiceImpl implements ShopCompanyInfoService {
    @Autowired
    private ShopCompanyInfoMapper shopCompanyInfoMapper;

    @Override
    public BusiShopCompanyPojo selectOne(BusiShopCompanyPojo record) {
        record.setDelFlag((short)0);
        return shopCompanyInfoMapper.selectOne(record);
    }

    @Override
    public List<BusiShopCompanyModel> selectList(BusiShopCompanyPageModel busiShopCompanyPageModel) {
        return shopCompanyInfoMapper.selectList(busiShopCompanyPageModel);
    }

    @Override
    public BusiShopCompanyPojo selectByCustomerId(Integer customerId) {
        BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setCustomerId(customerId);
        return selectOne(busiShopCompanyPojo);
    }

    @Override
    public BusiShopCompanyPojo selectByCondition(BusiShopCompanyPojo record) {
        return shopCompanyInfoMapper.selectOne(record);
    }
}