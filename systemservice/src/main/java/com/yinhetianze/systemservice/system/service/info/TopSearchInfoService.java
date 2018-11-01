package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.systemservice.system.model.TopSearchModel;
import com.yinhetianze.pojo.back.TopSearchPojo;

import java.util.List;
import java.util.Map;

public interface TopSearchInfoService
{
    List selectList(TopSearchPojo topSearchPojo);

    List<TopSearchModel> selectTopSearchList(TopSearchModel topSearchModel);

    List<Map> getPhoneTopSearch(TopSearchModel topSearchModel);
}