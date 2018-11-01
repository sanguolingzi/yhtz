package com.yinhetianze.common.business.sys.area.service;

import com.yinhetianze.pojo.task.AreaPojo;

import java.util.List;
import java.util.Map;

public interface AreaInfoService
{
    List<AreaPojo> getAreaList(AreaPojo pojo);

    AreaPojo findArea(AreaPojo pojo);

    List<Map<String, Object>> getAreaMapList(Map<String, Object> params);
}