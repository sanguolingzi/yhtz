package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.pojo.back.NoticePojo;

import java.util.List;
import java.util.Map;

public interface NoticeInfoService
{
    List selectList(NoticePojo noticePojo);

    NoticePojo selectOne(NoticePojo noticePojo);

    List<Map> selectForMobileIdex(NoticePojo noticePojo);
}