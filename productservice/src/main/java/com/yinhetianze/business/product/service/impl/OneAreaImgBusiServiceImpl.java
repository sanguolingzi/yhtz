package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.OneAreaImgBusiMapper;
import com.yinhetianze.business.product.service.OneAreaImgBusiService;
import com.yinhetianze.pojo.product.OneAreaImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OneAreaImgBusiServiceImpl implements OneAreaImgBusiService
{
    @Autowired
    private OneAreaImgBusiMapper mapper;

    @Override
    public int insertSelective(OneAreaImgPojo oneAreaImgPojo) {
        return mapper.insertSelective(oneAreaImgPojo);
    }

    @Override
    public void updateOneAreaImgPojoList(OneAreaImgPojo oneAreaImgPojo) {
        mapper.updateByPrimaryKeySelective(oneAreaImgPojo);
    }
}