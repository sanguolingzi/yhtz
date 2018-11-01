package com.yinhetianze.business.category.service;

import com.yinhetianze.pojo.category.ClassifyImgPojo;

public interface ClassifyImgBusiService
{
    void updateProductImgList(ClassifyImgPojo classifyImgPojo);

    int insertSelective(ClassifyImgPojo classifyImgPojo);
}