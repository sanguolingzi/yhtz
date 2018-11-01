package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.pojo.back.ClassifyPojo;

import java.util.List;

public interface ClassifyInfoService
{
    ClassifyPojo selectOne(ClassifyPojo classifyPojo);

    List<ClassifyModel> findAllClassify();
}