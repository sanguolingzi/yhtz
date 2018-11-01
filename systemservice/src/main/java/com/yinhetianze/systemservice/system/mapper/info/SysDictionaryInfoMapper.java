package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import com.yinhetianze.systemservice.system.model.SysDictionaryModel;

import java.util.List;

public interface SysDictionaryInfoMapper extends InfoMapper<SysDictionaryPojo> {
    List selectAll(SysDictionaryPojo sysDictionaryPojo);
    int selectSysDictionaryId(SysDictionaryModel sysDictionaryModel);
}