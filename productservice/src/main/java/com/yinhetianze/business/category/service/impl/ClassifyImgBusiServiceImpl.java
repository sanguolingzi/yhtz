package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.service.ClassifyImgBusiService;
import com.yinhetianze.pojo.category.ClassifyImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.category.mapper.ClassifyImgBusiMapper;

@Service
public class ClassifyImgBusiServiceImpl implements ClassifyImgBusiService
{
    @Autowired
    private ClassifyImgBusiMapper mapper;

    @Override
    public void updateProductImgList(ClassifyImgPojo classifyImgPojo){
        mapper.updateByPrimaryKeySelective(classifyImgPojo);
    }

    @Override
    public int insertSelective(ClassifyImgPojo classifyImgPojo){
        return mapper.insertSelective(classifyImgPojo);
    }
}