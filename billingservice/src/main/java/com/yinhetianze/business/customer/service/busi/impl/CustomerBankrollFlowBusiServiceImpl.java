package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerBankrollFlowBusiMapper;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollFlowBusiService;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerBankrollFlowBusiServiceImpl implements CustomerBankrollFlowBusiService {

    @Autowired
    private CustomerBankrollFlowBusiMapper customerBankrollFlowBusiMapper;

    @Override
    public int insert(BusiCustomerBankrollFlowPojo record) {
        return customerBankrollFlowBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiCustomerBankrollFlowPojo record) {
        return customerBankrollFlowBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerBankrollFlowPojo record) {
        return customerBankrollFlowBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiCustomerBankrollFlowPojo record) {
        return customerBankrollFlowBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateGameIdFlowToCustomer(Map<String,Object> paraMap) {
        return customerBankrollFlowBusiMapper.updateGameIdFlowToCustomer(paraMap);
    }
}