package com.yinhetianze.business.voucher.service.info.impl;

import com.yinhetianze.business.voucher.model.VoucherlModel;
import com.yinhetianze.business.voucher.service.info.VoucherInfoService;
import com.yinhetianze.pojo.product.VoucherlPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.voucher.mapper.info.VoucherInfoMapper;

import java.util.List;

@Service
public class VoucherInfoServiceImpl implements VoucherInfoService
{
    @Autowired
    private VoucherInfoMapper mapper;

    @Override
    public VoucherlPojo selectOne(VoucherlPojo voucherlPojo) {
        return mapper.selectOne(voucherlPojo);
    }

    @Override
    public List<VoucherlPojo> getVoucherList(VoucherlPojo voucherlPojo){
        return mapper.select(voucherlPojo);
    }
}