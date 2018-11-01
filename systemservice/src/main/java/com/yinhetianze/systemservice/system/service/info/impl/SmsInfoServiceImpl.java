package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.service.info.SmsInfoService;
import com.yinhetianze.pojo.back.SmsPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.SmsInfoMapper;

import java.util.List;

@Service
public class SmsInfoServiceImpl implements SmsInfoService
{
    @Autowired
    private SmsInfoMapper mapper;

    @Override
    public List<SmsPojo> findAllSms(SmsPojo smsPojo) {
        return mapper.select(smsPojo);
    }
}