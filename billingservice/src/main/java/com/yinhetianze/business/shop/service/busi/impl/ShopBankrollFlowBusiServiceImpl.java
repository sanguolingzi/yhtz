package com.yinhetianze.business.shop.service.busi.impl;

import com.yinhetianze.business.shop.mapper.busi.ShopBankrollFlowBusiMapper;
import com.yinhetianze.business.shop.service.busi.ShopBankrollFlowBusiService;
import com.yinhetianze.pojo.shop.BusiShopBankrollFlowPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ShopBankrollFlowBusiServiceImpl implements ShopBankrollFlowBusiService {
    @Autowired
    private ShopBankrollFlowBusiMapper shopBankrollFlowBusiMapper;

    @Override
    public int insert(BusiShopBankrollFlowPojo record) {
        return shopBankrollFlowBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiShopBankrollFlowPojo record) {
        return shopBankrollFlowBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiShopBankrollFlowPojo record) {
        return shopBankrollFlowBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiShopBankrollFlowPojo record) {
        return shopBankrollFlowBusiMapper.updateByPrimaryKey(record);
    }
}