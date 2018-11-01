package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.service.busi.SmsBusiService;
import com.yinhetianze.pojo.back.SmsPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.busi.SmsBusiMapper;

@Service
public class SmsBusiServiceImpl implements SmsBusiService
{
    @Autowired
    private SmsBusiMapper mapper;

    @Override
    public int addSms(SmsPojo smsPojo) {
        return mapper.insertSelective(smsPojo);
    }

    @Override
    public int updateSms(SmsPojo smsPojo) {
        return mapper.updateByPrimaryKeySelective(smsPojo);
    }

    @Override
    public int deleteSms(SmsPojo smsPojo) {
        return mapper.deleteByPrimaryKey(smsPojo);
    }
}