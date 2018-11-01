package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.pojo.customer.BusiCustomerFeedbackImgPojo;
import java.util.List;

public interface CustomerFeedbackImgInfoService
{
    List<BusiCustomerFeedbackImgPojo>  selectByFeedbackId(Integer feedbackId);
}