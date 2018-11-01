package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;

import java.util.List;

public interface CustomerFeedbackImgInfoMapper extends InfoMapper<BusiCustomerFeedbackImgPojo> {
    List<BusiCustomerFeedbackImgPojo> selectByFeedbackId(Integer feedbackId);
}