package com.yinhetianze.business.customer.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;

public interface CustomerWechatBusiMapper extends BusiMapper<BusiCustomerWechatPojo> {

    int cancleBind(BusiCustomerWechatPojo busiCustomerWechatPojo);
}