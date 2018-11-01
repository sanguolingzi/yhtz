package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerWechatInfoMapper;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CustomerWechatInfoServiceImpl implements CustomerWechatInfoService
{


    @Autowired
    private CustomerWechatInfoMapper mapper;

    @Override
    public BusiCustomerWechatPojo select(BusiCustomerWechatPojo busiCustomerWechatPojo) {
        busiCustomerWechatPojo.setDelFlag((short)0);
        return mapper.selectOne(busiCustomerWechatPojo);
    }

    @Override
    public BusiCustomerWechatPojo selectByCustomerCode(String customerCode) {
        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
        busiCustomerWechatPojo.setCustomerCode(customerCode);
        return select(busiCustomerWechatPojo);
    }

    @Override
    public BusiCustomerWechatPojo selectByOpenId(String OpenId) {
        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
        busiCustomerWechatPojo.setOpenId(OpenId);
        return select(busiCustomerWechatPojo);
    }

    @Override
    public BusiCustomerWechatPojo selectByCustomerId(Integer customerId,Short idType) {
        BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
        busiCustomerWechatPojo.setCustomerId(customerId);
        busiCustomerWechatPojo.setIdType(idType);
        return select(busiCustomerWechatPojo);
    }

    @Override
    public List<BusiCustomerWechatPojo> getOneCustomer(BusiCustomerWechatPojo busiCustomerWechatPojo){
        return mapper.select(busiCustomerWechatPojo);
    }

}