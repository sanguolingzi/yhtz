package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerBankrollFlowInfoMapper;
import com.yinhetianze.business.customer.model.BusiAmountFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerBankrollFlowInfoServiceImpl implements CustomerBankrollFlowInfoService {

    @Autowired
    private CustomerBankrollFlowInfoMapper customerBankrollFlowInfoMapper;

    @Override
    public BusiCustomerBankrollFlowPojo selectOne(BusiCustomerBankrollFlowPojo record) {
        record.setDelFlag((short)0);
        return customerBankrollFlowInfoMapper.selectOne(record);
    }

    @Override
    public List<BusiCustomerBankrollFlowModel> selectList(BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel) {
        return customerBankrollFlowInfoMapper.selectList(busiCustomerBankrollFlowModel);
    }

    @Override
    public List<Map<String, Object>> selectConsumption(Map<String, Object> paraMap) {
        return customerBankrollFlowInfoMapper.selectConsumption(paraMap);
    }

    @Override
    public List<BusiAmountFlowModel> selectPersonal(Map<String,Object> paraMap) {
        return customerBankrollFlowInfoMapper.selectPersonal(paraMap);
    }

    @Override
    public List<BusiCustomerBankrollFlowModel> selectMarket(Map<String, Object> paraMap) {
        return customerBankrollFlowInfoMapper.selectMarket(paraMap);
    }
}