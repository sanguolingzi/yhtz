package com.yinhetianze.business.category.service;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.pojo.category.CategoryPojo;

import java.util.List;
import java.util.Map;

public interface CategoryInfoService
{
    CategoryPojo findCategory(CategoryPojo pojo);

    List<CategoryPojo> getCategoryList(Map<String, Object> params);

    List<Map<String,Object>> getSelectMap();

    int selectCategoryid(CategoryModel categoryModel);

    CategoryPojo selectCategory(CategoryPojo categoryPojo);

    List<Map>getOneCateList();
    List<Map<String,Object>> selectMap();

}