package com.yinhetianze.business.product.service;

import com.yinhetianze.business.product.model.SysProdauditModel;

import java.util.List;
import java.util.Map;

public interface SysProdauditInfoService
{
    List<Map> getShopProdaudit(SysProdauditModel sysProdauditModel);
}