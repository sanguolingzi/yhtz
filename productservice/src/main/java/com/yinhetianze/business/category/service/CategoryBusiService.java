package com.yinhetianze.business.category.service;

import com.yinhetianze.pojo.category.CategoryPojo;

public interface CategoryBusiService
{
    int addCategory(CategoryPojo pojo);

    int modifyCategory(CategoryPojo pojo);

    int deleteCategory(CategoryPojo pojo);
}