package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.systemservice.mall.mapper.info.MallActivityInfoMapper;
import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.systemservice.mall.service.info.MallActivityInfoService;
import com.yinhetianze.pojo.back.MallActivityPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallActivityInfoServiceImpl implements MallActivityInfoService
{
    @Autowired
    private MallActivityInfoMapper mapper;

    @Override
    public List selectList(MallActivityPojo mallActivityPojo){
        return mapper.select(mallActivityPojo);
    }

    @Override
    public List<MallActivityModel> selectMallActivityList(MallActivityModel mallActivityModel) {
        return  mapper.selectMallActivityList(mallActivityModel);
    }

    @Override
    public MallActivityPojo selectOne(MallActivityPojo pojo) {
        pojo.setDelFlag((short)0);
        return mapper.selectOne(pojo);
    }
}