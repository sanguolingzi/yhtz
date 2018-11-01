package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerBankrollInfoMapper;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerBankrollInfoServiceImpl implements CustomerBankrollInfoService {

    @Autowired
    private CustomerBankrollInfoMapper customerBankrollInfoMapper;


    @Override
    public BusiCustomerBankrollPojo selectOne(BusiCustomerBankrollPojo record) {
        record.setDelFlag((short)0);
        return customerBankrollInfoMapper.selectOne(record);
    }

    @Override
    public BusiCustomerBankrollPojo selectByCustomerId(Integer customerId) {
        return customerBankrollInfoMapper.selectByCustomerId(customerId);
    }

    @Override
    public BusiCustomerBankrollPojo selectBankrollInfo(Integer customerId) {
        BusiCustomerBankrollPojo busiCustomerBankrollPojo  = selectByCustomerId(customerId);
        BusiCustomerBankrollPojo temp = new BusiCustomerBankrollPojo();
        if(CommonUtil.isNotEmpty(busiCustomerBankrollPojo)){
            temp.setAmount(busiCustomerBankrollPojo.getAmount().movePointLeft(2));
            temp.setIntegral(busiCustomerBankrollPojo.getIntegral().movePointLeft(2));
            temp.setStarCoin(busiCustomerBankrollPojo.getStarCoin().movePointLeft(2));
            temp.setRewardAmount(busiCustomerBankrollPojo.getRewardAmount().movePointLeft(2));
        }
        return temp;
    }

    @Override
    public List<BusiCustomerBankrollPojo> select(BusiCustomerBankrollPojo record) {
        record.setDelFlag((short)0);
        return customerBankrollInfoMapper.select(record);
    }

    @Override
    public BusiCustomerBankrollPojo selectByGameId(Integer gameId) {
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
        busiCustomerBankrollPojo.setGameId(gameId);
        return selectOne(busiCustomerBankrollPojo);
    }
}