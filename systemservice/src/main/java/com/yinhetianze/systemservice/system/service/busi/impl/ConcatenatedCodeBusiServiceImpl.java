package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.pojo.back.ConcatenatedCodePojo;
import com.yinhetianze.systemservice.system.mapper.busi.ConcatenatedCodeBusiMapper;
import com.yinhetianze.systemservice.system.service.busi.ConcatenatedCodeBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcatenatedCodeBusiServiceImpl implements ConcatenatedCodeBusiService
{
    @Autowired
    private ConcatenatedCodeBusiMapper mapper;

    @Override
    public int insertSelective(ConcatenatedCodePojo concatenatedCodePojo) {
        return mapper.insertSelective(concatenatedCodePojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ConcatenatedCodePojo concatenatedCodePojo) {
        return mapper.updateByPrimaryKeySelective(concatenatedCodePojo);
    }
}