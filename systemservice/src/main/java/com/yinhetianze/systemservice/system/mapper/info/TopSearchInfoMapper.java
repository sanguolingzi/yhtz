package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.systemservice.system.model.TopSearchModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.TopSearchPojo;

import java.util.List;
import java.util.Map;

public interface TopSearchInfoMapper extends InfoMapper<TopSearchPojo> {
    List<TopSearchModel> selectTopSearchList(TopSearchModel topSearchModel);

    List<Map> getPhoneTopSearch(TopSearchModel topSearchModel);
}