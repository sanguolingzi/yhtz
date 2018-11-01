package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerFeedbackImgBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerFeedbackImgBusiService;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerFeedbackImgBusiServiceImpl implements CustomerFeedbackImgBusiService
{
    @Autowired
    private CustomerFeedbackImgBusiMapper customerFeedbackImgBusiMapper;

    @Override
    public int insertSelective(BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo) {
        return customerFeedbackImgBusiMapper.insertSelective(busiCustomerFeedbackImgPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo) {
        return customerFeedbackImgBusiMapper.updateByPrimaryKeySelective(busiCustomerFeedbackImgPojo);
    }
}