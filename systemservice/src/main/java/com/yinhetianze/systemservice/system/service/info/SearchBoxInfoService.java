package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.systemservice.system.model.SearchBoxModel;
import com.yinhetianze.pojo.back.SearchBoxPojo;

import java.util.List;

public interface SearchBoxInfoService
{
    List selectList(SearchBoxPojo searchBoxPojo);

    List<SearchBoxModel> selectModelList(SearchBoxModel searchBoxModel);

    int selectSearchBoxId(SearchBoxModel searchBoxModel);

    SearchBoxPojo selectSearchBox(SearchBoxPojo searchBoxPojo);
}