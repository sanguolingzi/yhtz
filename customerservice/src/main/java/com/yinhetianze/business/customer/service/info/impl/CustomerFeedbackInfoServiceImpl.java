package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerFeedbackInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackPageModel;
import com.yinhetianze.business.customer.service.info.CustomerFeedbackImgInfoService;
import com.yinhetianze.business.customer.service.info.CustomerFeedbackInfoService;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;


@Service
public class CustomerFeedbackInfoServiceImpl implements CustomerFeedbackInfoService
{
    @Autowired
    private CustomerFeedbackInfoMapper customerFeedbackInfoMapper;

    @Autowired
    private CustomerFeedbackImgInfoService customerFeedbackImgInfoServiceImpl;

    @Override
    public BusiCustomerFeedbackModel selectFeedback(Integer feedbackId) {
        BusiCustomerFeedbackPojo busiCustomerFeedbackPojo = new  BusiCustomerFeedbackPojo();
        busiCustomerFeedbackPojo.setId(feedbackId);
        busiCustomerFeedbackPojo.setDelFlag((short)0);
        busiCustomerFeedbackPojo  = customerFeedbackInfoMapper.selectOne(busiCustomerFeedbackPojo);

        if(busiCustomerFeedbackPojo == null)
            return null;
        BusiCustomerFeedbackModel busiCustomerFeedbackModel = new BusiCustomerFeedbackModel();
        BeanUtils.copyProperties(busiCustomerFeedbackPojo,busiCustomerFeedbackModel);
        List<BusiCustomerFeedbackImgPojo> list =customerFeedbackImgInfoServiceImpl.selectByFeedbackId(feedbackId);
        if(CommonUtil.isNotEmpty(list)){
            StringJoiner stringJoiner = new StringJoiner(",","","");
            for(BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo:list){
                              stringJoiner.add(busiCustomerFeedbackImgPojo.getImgUrl());
            }
            busiCustomerFeedbackModel.setImgUrls(stringJoiner.toString());
        }
        return busiCustomerFeedbackModel;
    }

    @Override
    public List<BusiCustomerFeedbackModel> selectList(BusiCustomerFeedbackPageModel busiCustomerFeedbackModel) {
        return customerFeedbackInfoMapper.selectList(busiCustomerFeedbackModel);
    }

    @Override
    public List<BusiCustomerFeedbackModel> selectListForManage(BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel) {
        return customerFeedbackInfoMapper.selectListForManage(busiCustomerFeedbackPageModel);
    }
}