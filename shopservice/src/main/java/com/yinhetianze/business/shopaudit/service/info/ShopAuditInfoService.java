package com.yinhetianze.business.shopaudit.service.info;

import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;

import java.util.List;

public interface ShopAuditInfoService
{
    BusiSysShopAuditPojo selectOne(BusiSysShopAuditPojo busiSysShopAuditPojo);

    List<BusiSysShopAuditPojo> select(BusiSysShopAuditPojo busiSysShopAuditPojo);
}