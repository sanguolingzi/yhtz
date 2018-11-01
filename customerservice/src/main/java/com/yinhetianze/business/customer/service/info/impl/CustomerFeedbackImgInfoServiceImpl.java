package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerFeedbackImgInfoMapper;
import com.yinhetianze.business.customer.service.info.CustomerFeedbackImgInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFeedbackImgInfoServiceImpl implements CustomerFeedbackImgInfoService
{
    @Autowired
    private CustomerFeedbackImgInfoMapper customerFeedbackImgInfoMapper;

    @Override
    public List<BusiCustomerFeedbackImgPojo> selectByFeedbackId(Integer feedbackId) {
        return customerFeedbackImgInfoMapper.selectByFeedbackId(feedbackId);
    }
}