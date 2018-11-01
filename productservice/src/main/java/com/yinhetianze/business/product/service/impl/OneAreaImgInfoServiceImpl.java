package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.OneAreaImgInfoService;
import com.yinhetianze.pojo.product.OneAreaImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.OneAreaImgInfoMapper;

import java.util.List;

@Service
public class OneAreaImgInfoServiceImpl implements OneAreaImgInfoService
{
    @Autowired
    private OneAreaImgInfoMapper mapper;

    @Override
    public List<OneAreaImgPojo> selectOneAreaImgList(OneAreaImgPojo oneAreaImgPojo) {
        return mapper.select(oneAreaImgPojo);
    }
}