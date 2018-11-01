package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.OneAreaBusiMapper;
import com.yinhetianze.business.product.mapper.OneAreaInfoMapper;
import com.yinhetianze.business.product.service.OneAreaBusiService;
import com.yinhetianze.business.product.service.OneAreaInfoService;
import com.yinhetianze.pojo.product.OneAreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OneAreaBusiServiceImpl implements OneAreaBusiService
{
    @Autowired
    private OneAreaBusiMapper mapper;

    @Override
    public int addOneArea(OneAreaPojo oneAreaPojo) {
        return mapper.insertSelective(oneAreaPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(OneAreaPojo oneAreaPojo) {
        return mapper.updateByPrimaryKeySelective(oneAreaPojo);
    }
}