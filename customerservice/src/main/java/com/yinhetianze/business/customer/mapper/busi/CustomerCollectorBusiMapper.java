package com.yinhetianze.business.customer.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.customer.BusiCustomerCollectorPojo;

public interface CustomerCollectorBusiMapper extends BusiMapper<BusiCustomerCollectorPojo> {
    int deleteInfo(BusiCustomerCollectorPojo record);
}