package com.yinhetianze.business.rebate.service.impl;

import com.yinhetianze.business.rebate.service.RebateBusiService;
import com.yinhetianze.pojo.order.RebatePojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.rebate.mapper.RebateBusiMapper;

@Service
public class RebateBusiServiceImpl implements RebateBusiService
{
    @Autowired
    private RebateBusiMapper mapper;

    @Override
    public int add(RebatePojo rebatePojo) {
        return mapper.insertSelective(rebatePojo);
    }
}