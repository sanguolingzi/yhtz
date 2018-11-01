package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.SysProdauditBusiMapper;
import com.yinhetianze.business.product.mapper.SysProdauditInfoMapper;
import com.yinhetianze.business.product.service.SysProdauditBusiService;
import com.yinhetianze.pojo.product.SysProdauditPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysProdauditBusiServiceImpl implements SysProdauditBusiService
{
    @Autowired
    private SysProdauditBusiMapper mapper;

    @Override
    public int addsysProdaudit(SysProdauditPojo sysProdauditPojo) {
        return mapper.insertSelective(sysProdauditPojo);
    }
}