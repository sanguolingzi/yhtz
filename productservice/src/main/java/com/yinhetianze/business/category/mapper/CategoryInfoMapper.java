package com.yinhetianze.business.category.mapper;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.category.CategoryPojo;

import java.util.List;
import java.util.Map;

public interface CategoryInfoMapper extends InfoMapper<CategoryPojo> {

    List<CategoryPojo> getCategoryList(Map<String, Object> param);
    int selectCategoryid(CategoryModel categoryModel);
    List<Map<String,Object>> getSelectMap();
    List<Map<String,Object>> selectMap();
    List<Map>getOneCateList();
}