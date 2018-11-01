package com.yinhetianze.business.shophistory.service.info.impl;

import com.yinhetianze.business.shophistory.service.info.ShopHistoryInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shophistory.mapper.info.ShopHistoryInfoMapper;

@Service
public class ShopHistoryInfoServiceImpl implements ShopHistoryInfoService
{
    @Autowired
    private ShopHistoryInfoMapper mapper;
}