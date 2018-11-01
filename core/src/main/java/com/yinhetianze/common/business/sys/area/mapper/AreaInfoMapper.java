package com.yinhetianze.common.business.sys.area.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.task.AreaPojo;

import java.util.List;
import java.util.Map;

public interface AreaInfoMapper extends InfoMapper<AreaPojo> {

    List<Map<String, Object>> getAreaMapList(Map<String, Object> params);

}