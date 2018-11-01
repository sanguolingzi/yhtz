package com.yinhetianze.business.customer.service.info.impl;

import com.yinhetianze.business.customer.service.info.CustomerDrawQueueInfoService;
import com.yinhetianze.pojo.customer.BusiCustomerDrawQueuePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.customer.mapper.info.CustomerDrawQueueInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class CustomerDrawQueueInfoServiceImpl implements CustomerDrawQueueInfoService
{
    @Autowired
    private CustomerDrawQueueInfoMapper mapper;

    @Override
    public List<BusiCustomerDrawQueuePojo> selectList(Map paraMap) {
        return mapper.selectList(paraMap);
    }
}