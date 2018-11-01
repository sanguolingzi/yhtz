package com.yinhetianze.business.shopaudit.service.info.impl;

import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shopaudit.mapper.info.ShopAuditInfoMapper;

import java.util.List;

@Service
public class ShopAuditInfoServiceImpl implements ShopAuditInfoService
{
    @Autowired
    private ShopAuditInfoMapper shopAuditInfoMapper;

    @Override
    public BusiSysShopAuditPojo selectOne(BusiSysShopAuditPojo busiSysShopAuditPojo) {
        return shopAuditInfoMapper.selectOne(busiSysShopAuditPojo);
    }

    @Override
    public List<BusiSysShopAuditPojo> select(BusiSysShopAuditPojo busiSysShopAuditPojo) {
        return shopAuditInfoMapper.select(busiSysShopAuditPojo);
    }
}