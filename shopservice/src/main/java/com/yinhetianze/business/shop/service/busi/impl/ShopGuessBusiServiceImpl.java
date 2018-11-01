package com.yinhetianze.business.shop.service.busi.impl;

import com.yinhetianze.business.shop.mapper.busi.ShopGuessBusiMapper;
import com.yinhetianze.business.shop.mapper.info.ShopGuessInfoMapper;
import com.yinhetianze.business.shop.service.busi.ShopGuessBusiService;
import com.yinhetianze.business.shop.service.info.ShopGuessInfoService;
import com.yinhetianze.pojo.shop.ShopGuessPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShopGuessBusiServiceImpl implements ShopGuessBusiService
{
    @Autowired
    private ShopGuessBusiMapper mapper;

    @Override
    public int insertSelective(ShopGuessPojo shopGuessPojo) {
        return mapper.insertSelective(shopGuessPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopGuessPojo shopGuessPojo) {
        return mapper.updateByPrimaryKeySelective(shopGuessPojo);
    }
}