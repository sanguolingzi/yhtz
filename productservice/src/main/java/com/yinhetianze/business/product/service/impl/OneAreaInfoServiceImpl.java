package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.OneAreaInfoService;
import com.yinhetianze.pojo.product.OneAreaPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.OneAreaInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class OneAreaInfoServiceImpl implements OneAreaInfoService
{
    @Autowired
    private OneAreaInfoMapper mapper;

    @Override
    public List<Map> selectBackstageOneAreaList(OneAreaPojo oneAreaPojo) {
        return mapper.selectBackstageOneAreaList(oneAreaPojo);
    }

    @Override
    public OneAreaPojo selectOne(OneAreaPojo oneAreaPojo) {
        return mapper.selectOne(oneAreaPojo);
    }

    @Override
    public List<Map> selectOneAreaList(OneAreaPojo oneAreaPojo) {
        return mapper.selectOneAreaList(oneAreaPojo);
    }

    @Override
    public List<Map> selectOneAreaDetails(OneAreaPojo oneAreaPojo) {
        return mapper.selectOneAreaDetails(oneAreaPojo);
    }
}