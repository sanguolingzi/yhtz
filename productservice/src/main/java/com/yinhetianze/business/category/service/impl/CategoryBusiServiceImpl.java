package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.service.CategoryBusiService;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.category.mapper.CategoryBusiMapper;

@Service
public class CategoryBusiServiceImpl implements CategoryBusiService
{
    @Autowired
    private CategoryBusiMapper mapper;

    @Override
    public int addCategory(CategoryPojo pojo)
    {
        return mapper.insertSelective(pojo);
    }

    @Override
    public int modifyCategory(CategoryPojo pojo)
    {
        return mapper.updateByPrimaryKeySelective(pojo);
    }

    @Override
    public int deleteCategory(CategoryPojo pojo)
    {
        return mapper.deleteByPrimaryKey(pojo);
    }

}