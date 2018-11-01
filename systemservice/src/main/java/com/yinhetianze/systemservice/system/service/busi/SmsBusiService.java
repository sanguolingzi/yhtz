package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.pojo.back.SmsPojo;

public interface SmsBusiService
{
    int addSms(SmsPojo smsPojo);

    int updateSms(SmsPojo smsPojo);

    int deleteSms(SmsPojo smsPojo);
}