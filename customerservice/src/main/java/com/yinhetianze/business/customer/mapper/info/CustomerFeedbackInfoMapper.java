package com.yinhetianze.business.customer.mapper.info;

import com.yinhetianze.business.customer.model.BusiCustomerFeedbackModel;
import com.yinhetianze.business.customer.model.BusiCustomerFeedbackPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.customer.BusiCustomerFeedbackPojo;

import java.util.List;

public interface CustomerFeedbackInfoMapper extends InfoMapper<BusiCustomerFeedbackPojo> {

    List<BusiCustomerFeedbackModel> selectList(BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel);

    List<BusiCustomerFeedbackModel> selectListForManage(BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel);
}