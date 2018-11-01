package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.service.busi.ClassifyBusiService;
import com.yinhetianze.pojo.back.ClassifyPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.system.mapper.busi.ClassifyBusiMapper;

@Service
public class ClassifyBusiServiceImpl implements ClassifyBusiService
{
    @Autowired
    private ClassifyBusiMapper mapper;

    @Override
    public int insertSelective(ClassifyPojo classifyPojo) {
        return mapper.insertSelective(classifyPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ClassifyPojo classifyPojo) {
        return mapper.updateByPrimaryKeySelective(classifyPojo);
    }

    @Override
    public int delete(ClassifyPojo classifyPojo) {
        return mapper.delete(classifyPojo);
    }
}