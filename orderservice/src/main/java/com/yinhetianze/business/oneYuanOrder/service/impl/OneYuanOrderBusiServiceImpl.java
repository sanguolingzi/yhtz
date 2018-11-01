package com.yinhetianze.business.oneYuanOrder.service.impl;

import com.yinhetianze.business.oneYuanOrder.service.OneYuanOrderBusiService;
import com.yinhetianze.pojo.order.OneYuanOrderPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.oneYuanOrder.mapper.oneYuanOrderBusiMapper;

@Service
public class OneYuanOrderBusiServiceImpl implements OneYuanOrderBusiService
{
    @Autowired
    private oneYuanOrderBusiMapper mapper;

    @Override
    public int add(OneYuanOrderPojo oneYuanOrderPojo) {
        return mapper.insertSelective(oneYuanOrderPojo);
    }
}