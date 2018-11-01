package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;

public interface CustomerFeedbackImgBusiService
{
    int insertSelective(BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo);

    int updateByPrimaryKeySelective(BusiCustomerFeedbackImgPojo busiCustomerFeedbackImgPojo);
}