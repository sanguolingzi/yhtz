package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.systemservice.system.model.SearchBoxModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SearchBoxPojo;

import java.util.List;

public interface SearchBoxInfoMapper extends InfoMapper<SearchBoxPojo> {
    List<SearchBoxModel> selectModelList(SearchBoxModel searchBoxModel);

    int selectSearchBoxId(SearchBoxModel searchBoxModel);
    
}