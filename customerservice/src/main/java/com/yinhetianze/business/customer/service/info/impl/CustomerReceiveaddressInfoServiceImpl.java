package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerReceiveaddressInfoMapper;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CustomerReceiveaddressInfoServiceImpl implements CustomerReceiveaddressInfoService
{
    @Autowired
    private CustomerReceiveaddressInfoMapper customerReceiveaddressInfoMapper;

    @Override
    public BusiCustomerReceiveaddressPojo selectOne(BusiCustomerReceiveaddressPojo record) {
        record.setDelFlag((short)0);
        return customerReceiveaddressInfoMapper.selectOne(record);
    }

    @Override
    public List<BusiCustomerReceiveaddressPojo> selectByCustomerId(Integer customerId) {
        return customerReceiveaddressInfoMapper.selectByCustomerId(customerId);
    }
}