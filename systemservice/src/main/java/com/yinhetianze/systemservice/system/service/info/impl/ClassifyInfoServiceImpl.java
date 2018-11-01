package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.systemservice.system.service.info.ClassifyInfoService;
import com.yinhetianze.pojo.back.ClassifyPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.info.ClassifyInfoMapper;

import java.util.List;

@Service
public class ClassifyInfoServiceImpl implements ClassifyInfoService
{
    @Autowired
    private ClassifyInfoMapper mapper;

    @Override
    public ClassifyPojo selectOne(ClassifyPojo classifyPojo) {
        return mapper.selectOne(classifyPojo);
    }

    @Override
    public List<ClassifyModel> findAllClassify() {
        return mapper.findAllClassify();
    }
}