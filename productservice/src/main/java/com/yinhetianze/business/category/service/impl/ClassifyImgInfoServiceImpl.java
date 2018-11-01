package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.service.ClassifyImgInfoService;
import com.yinhetianze.pojo.category.ClassifyImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.category.mapper.ClassifyImgInfoMapper;

import java.util.List;

@Service
public class ClassifyImgInfoServiceImpl implements ClassifyImgInfoService
{
    @Autowired
    private ClassifyImgInfoMapper mapper;

    @Override
    public List<ClassifyImgPojo> selectProductImgList(ClassifyImgPojo classifyImgPojParam){
        return mapper.select(classifyImgPojParam);
    }
}