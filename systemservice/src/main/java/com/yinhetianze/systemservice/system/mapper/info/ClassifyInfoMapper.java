package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.ClassifyPojo;

import java.util.List;

public interface ClassifyInfoMapper extends InfoMapper<ClassifyPojo> {

    List<ClassifyModel> findAllClassify();
}