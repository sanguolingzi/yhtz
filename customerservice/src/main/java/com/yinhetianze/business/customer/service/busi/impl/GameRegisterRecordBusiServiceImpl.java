package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.service.busi.GameRegisterRecordBusiService;
import com.yinhetianze.pojo.customer.GameRegisterRecordPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.customer.mapper.busi.GameRegisterRecordBusiMapper;

@Service
public class GameRegisterRecordBusiServiceImpl implements GameRegisterRecordBusiService
{
    @Autowired
    private GameRegisterRecordBusiMapper mapper;

    @Override
    public int insertgameRegister(GameRegisterRecordPojo gameRegisterRecordPojo){
        return mapper.insertSelective(gameRegisterRecordPojo);
    }
}