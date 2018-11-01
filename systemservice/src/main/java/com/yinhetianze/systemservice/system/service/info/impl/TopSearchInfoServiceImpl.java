package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.model.TopSearchModel;
import com.yinhetianze.systemservice.system.service.info.TopSearchInfoService;
import com.yinhetianze.pojo.back.TopSearchPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.TopSearchInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class TopSearchInfoServiceImpl implements TopSearchInfoService
{
    @Autowired
    private TopSearchInfoMapper mapper;

    @Override
    public List selectList(TopSearchPojo topSearchPojo){
        return mapper.select(topSearchPojo);
    }

    @Override
    public List<TopSearchModel> selectTopSearchList(TopSearchModel topSearchModel) {
        return mapper.selectTopSearchList(topSearchModel);
    }

    @Override
    public List<Map> getPhoneTopSearch(TopSearchModel topSearchModel){
        return mapper.getPhoneTopSearch(topSearchModel);
    }
}