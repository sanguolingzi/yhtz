package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;

public interface CustomerWechatBusiService
{
    int insertSelective(BusiCustomerWechatPojo busiCustomerWechatPojo);

    int updateByPrimaryKeySelective(BusiCustomerWechatPojo busiCustomerWechatPojo);

    int wxRegeister(BusiRegeisterModel busiRegeisterModel) throws BusinessException;

    int cancleBind(BusiCustomerWechatPojo busiCustomerWechatPojo);
}