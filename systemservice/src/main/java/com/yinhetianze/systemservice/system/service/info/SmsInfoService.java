package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.SmsPojo;

import java.util.List;

public interface SmsInfoService
{
    List<SmsPojo> findAllSms(SmsPojo smsPojo);
}