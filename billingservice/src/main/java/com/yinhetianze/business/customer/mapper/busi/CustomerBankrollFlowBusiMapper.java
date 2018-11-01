package com.yinhetianze.business.customer.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;

import java.util.Map;

public interface CustomerBankrollFlowBusiMapper extends BusiMapper<BusiCustomerBankrollFlowPojo> {

    int updateGameIdFlowToCustomer(Map<String,Object> paraMap);
}