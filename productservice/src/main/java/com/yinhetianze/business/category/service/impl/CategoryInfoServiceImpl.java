package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.category.mapper.CategoryInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class CategoryInfoServiceImpl implements CategoryInfoService
{
    @Autowired
    private CategoryInfoMapper mapper;

    @Override
    public CategoryPojo findCategory(CategoryPojo pojo)
    {
        return mapper.selectOne(pojo);
    }

    public List<CategoryPojo> getCategoryList(Map<String, Object> params)
    {
        return mapper.getCategoryList(params);
    }

    @Override
    public List<Map<String,Object>> getSelectMap(){
        return mapper.getSelectMap();
    }

    @Override
    public int selectCategoryid(CategoryModel categoryModel) {
        return mapper.selectCategoryid(categoryModel);
    }

    @Override
    public CategoryPojo selectCategory(CategoryPojo categoryPojo) {
        return mapper.selectOne(categoryPojo);
    }

    @Override
    public List<Map> getOneCateList() {
        return mapper.getOneCateList();
    }

    @Override
    public List<Map<String, Object>> selectMap() {
        return mapper.selectMap();
    }
}