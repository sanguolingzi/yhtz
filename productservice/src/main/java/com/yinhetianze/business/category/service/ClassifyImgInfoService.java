package com.yinhetianze.business.category.service;

import com.yinhetianze.pojo.category.ClassifyImgPojo;

import java.util.List;

public interface ClassifyImgInfoService
{
    List<ClassifyImgPojo> selectProductImgList(ClassifyImgPojo classifyImgPojParam);
}