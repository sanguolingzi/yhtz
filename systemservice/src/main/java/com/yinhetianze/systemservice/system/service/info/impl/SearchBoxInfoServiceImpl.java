package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.model.SearchBoxModel;
import com.yinhetianze.systemservice.system.service.info.SearchBoxInfoService;
import com.yinhetianze.pojo.back.SearchBoxPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.SearchBoxInfoMapper;

import java.util.List;

@Service
public class SearchBoxInfoServiceImpl implements SearchBoxInfoService
{
    @Autowired
    private SearchBoxInfoMapper mapper;

    @Override
    public List selectList(SearchBoxPojo searchBoxPojo){
        return mapper.select(searchBoxPojo);
    }

    @Override
    public List<SearchBoxModel> selectModelList(SearchBoxModel searchBoxModel) {
        return mapper.selectModelList(searchBoxModel);
    }

    @Override
    public int selectSearchBoxId(SearchBoxModel searchBoxModel) {
        return mapper.selectSearchBoxId(searchBoxModel);
    }

    @Override
    public SearchBoxPojo selectSearchBox(SearchBoxPojo searchBoxPojo) {
        return mapper.selectOne(searchBoxPojo);
    }
}