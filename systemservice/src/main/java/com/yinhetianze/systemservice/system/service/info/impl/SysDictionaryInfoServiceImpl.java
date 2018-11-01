package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.mapper.info.SysDictionaryInfoMapper;
import com.yinhetianze.systemservice.system.model.SysDictionaryModel;
import com.yinhetianze.systemservice.system.service.info.SysDictionaryInfoService;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictionaryInfoServiceImpl implements SysDictionaryInfoService
{
    @Autowired
    private SysDictionaryInfoMapper mapper;

    @Override
    public List selectList(SysDictionaryPojo sysDictionaryPojo){
        return mapper.selectAll(sysDictionaryPojo);
    }

    @Override
    public int selectSysDictionaryId(SysDictionaryModel sysDictionaryModel) {
        return mapper.selectSysDictionaryId(sysDictionaryModel);
    }

    @Override
    public SysDictionaryPojo selectSysDictionary(SysDictionaryPojo sysDictionaryPojo) {
        return mapper.selectOne(sysDictionaryPojo);
    }
}