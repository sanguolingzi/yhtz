package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.pojo.back.ConcatenatedCodePojo;
import com.yinhetianze.systemservice.system.mapper.info.ConcatenatedCodeInfoMapper;
import com.yinhetianze.systemservice.system.service.info.ConcatenatedCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConcatenatedCodeInfoServiceImpl implements ConcatenatedCodeInfoService
{
    @Autowired
    private ConcatenatedCodeInfoMapper mapper;

    @Override
    public List<Map> selectConcatenatedCodeList() {
        return mapper.selectConcatenatedCodeList();
    }

    @Override
    public ConcatenatedCodePojo selectOne(ConcatenatedCodePojo concatenatedCodePojo) {
        return mapper.selectOne(concatenatedCodePojo);
    }

    @Override
    public List<Map> selectConcatenatedCodeNameList() {
        return mapper.selectConcatenatedCodeNameList();
    }

    @Override
    public List<Map> selectConcatenatedCode(ConcatenatedCodePojo concatenatedCodePojo) {
        return mapper.selectConcatenatedCode(concatenatedCodePojo);
    }
}