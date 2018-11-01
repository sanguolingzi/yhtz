package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;

public interface CustomerCollectorBusiService
{

    int updateByPrimaryKeySelective(BusiCustomerCollectorPojo record);

    int addInfo(BusiCustomerCollectorPojo record) throws BusinessException;

    int deleteInfo(BusiCustomerCollectorPojo record) throws BusinessException;
}