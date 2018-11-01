package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.SysDictionaryPojo;
import com.yinhetianze.systemservice.system.model.SysDictionaryModel;

import java.util.List;

public interface SysDictionaryInfoService
{

    List selectList(SysDictionaryPojo sysDictionaryPojo);

    int selectSysDictionaryId(SysDictionaryModel sysDictionaryModel);

    SysDictionaryPojo selectSysDictionary(SysDictionaryPojo sysDictionaryPojo);
}