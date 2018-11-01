package com.yinhetianze.business.shop.service.busi.impl;

import com.yinhetianze.business.shop.mapper.busi.ShopBankrollBusiMapper;
import com.yinhetianze.business.shop.service.busi.ShopBankrollBusiService;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ShopBankrollBusiServiceImpl implements ShopBankrollBusiService {
    @Autowired
    private ShopBankrollBusiMapper shopBankrollBusiMapper;

    @Override
    public int insert(BusiShopBankrollPojo record) {
        return shopBankrollBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiShopBankrollPojo record) {
        return shopBankrollBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiShopBankrollPojo record) {
        return shopBankrollBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiShopBankrollPojo record) {
        return shopBankrollBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateGoodsAmount(Integer id, BigDecimal goodsAmount, BigDecimal oldGoodsAmout) {
        return shopBankrollBusiMapper.updateGoodsAmount(id,goodsAmount,oldGoodsAmout);
    }

    @Override
    public int add(BusiShopBankrollPojo record) {
        initData(record);
        return insertSelective(record);
    }


    public void initData(BusiShopBankrollPojo record){
        if(record.getGoodsAmount() != null){
            record.setGoodsAmount(new BigDecimal("0"));
        }
    }
}