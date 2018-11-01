package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.pojo.back.DetonatingPojo;
import com.yinhetianze.systemservice.mall.service.info.DetonatingInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.systemservice.mall.mapper.info.DetonatingInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class DetonatingInfoServiceImpl implements DetonatingInfoService
{
    @Autowired
    private DetonatingInfoMapper mapper;

    @Override
    public DetonatingPojo selectOne(DetonatingPojo detonatingPojo) {
        return mapper.selectOne(detonatingPojo);
    }

    @Override
    public List<Map> selectBackstageDetonatingList(DetonatingPojo detonatingPojo) {
        return mapper.selectBackstageDetonatingList(detonatingPojo);
    }

    @Override
    public List<Map> selectDetonatingList() {
        return mapper.selectDetonatingList();
    }
}