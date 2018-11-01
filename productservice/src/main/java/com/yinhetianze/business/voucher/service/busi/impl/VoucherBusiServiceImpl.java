package com.yinhetianze.business.voucher.service.busi.impl;

import com.yinhetianze.business.voucher.service.busi.VoucherBusiService;
import com.yinhetianze.pojo.product.VoucherlPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.voucher.mapper.busi.VoucherBusiMapper;

import java.util.Map;

@Service
public class VoucherBusiServiceImpl implements VoucherBusiService
{
    @Autowired
    private VoucherBusiMapper mapper;

    @Override
    public int add(VoucherlPojo voucherlPojo) {
        return mapper.insertSelective(voucherlPojo);
    }

    @Override
    public int updateStatus(Map<String, Object> map) {
        return mapper.updateStatus(map);
    }
}