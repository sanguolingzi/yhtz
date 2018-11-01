package com.yinhetianze.business.shopaudit.service.busi;

import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;

public interface ShopAuditBusiService
{
    int addInfo(BusiSysShopAuditPojo busiSysShopAudit);

    int updateInfo(BusiSysShopAuditPojo busiSysShopAudit);

    int updateInfo(BusiSysShopAuditModel busiSysShopAuditModel) throws BusinessException;

    int updateDoUpdate(Integer customerId,Integer relationId);
}