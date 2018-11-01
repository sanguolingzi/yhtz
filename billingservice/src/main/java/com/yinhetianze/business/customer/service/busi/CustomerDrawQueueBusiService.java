package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerDrawQueuePojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;

public interface CustomerDrawQueueBusiService
{
    int addInfo(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo);

    int updateForPaySuccess(Integer queueId,Integer drawRecordId);

    int updateByPrimaryKeySelective(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo);

    int updateForPayFailed(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo, BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException;
}