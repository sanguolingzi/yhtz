package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.mapper.info.CustomerEarningsInfoMapper;
import com.yinhetianze.business.customer.mapper.info.CustomerEarningsInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerEaringModel;
import com.yinhetianze.business.customer.service.info.CustomerEarningsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CustomerEarningsInfoServiceImpl implements CustomerEarningsInfoService
{
    @Autowired
    private CustomerEarningsInfoMapper mapper;

    @Override
    public Map<String, Object> selectDbCurrentTime() {
        return mapper.selectDbCurrentTime();
    }

    @Override
    public List<BusiCustomerEaringModel> selectEaringList(Map<String, Object> paraMap) {
        return mapper.selectEaringList(paraMap);
    }

    @Override
    public BigDecimal selectTotalEaring(Map<String, Object> paraMap) {
        Map<String,Object> map = mapper.selectTotalEaring(paraMap);
        if(map == null || map.get("amount") == null){
            return new BigDecimal("0");
        }
        BigDecimal earing = new BigDecimal(map.getOrDefault("amount","0").toString());
        return earing;
    }

    @Override
    public BigDecimal selectEaringByCondition(Map<String, Object> paraMap) {
        Map<String,Object> map = mapper.selectEaringByCondition(paraMap);
        if(map == null || map.get("amount") == null){
            return new BigDecimal("0");
        }
        BigDecimal earing = new BigDecimal(map.getOrDefault("amount","0").toString());
        return earing;
    }
}