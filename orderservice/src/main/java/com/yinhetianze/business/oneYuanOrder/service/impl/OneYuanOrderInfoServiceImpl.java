package com.yinhetianze.business.oneYuanOrder.service.impl;

import com.yinhetianze.business.oneYuanOrder.service.OneYuanOrderInfoService;
import com.yinhetianze.pojo.order.OneYuanOrderPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.oneYuanOrder.mapper.OneYuanOrderInfoMapper;

@Service
public class OneYuanOrderInfoServiceImpl implements OneYuanOrderInfoService
{
    @Autowired
    private OneYuanOrderInfoMapper mapper;

    @Override
    public OneYuanOrderPojo selectOne(OneYuanOrderPojo oneYuanOrderPojo) {
        return mapper.selectOne(oneYuanOrderPojo);
    }
}