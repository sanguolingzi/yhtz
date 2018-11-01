package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.model.SysProdauditModel;
import com.yinhetianze.business.product.service.SysProdauditInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.SysProdauditInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class SysProdauditInfoServiceImpl implements SysProdauditInfoService
{
    @Autowired
    private SysProdauditInfoMapper mapper;

    @Override
    public List<Map> getShopProdaudit(SysProdauditModel sysProdauditModel) {
        return mapper.getShopProdaudit(sysProdauditModel);
    }
}