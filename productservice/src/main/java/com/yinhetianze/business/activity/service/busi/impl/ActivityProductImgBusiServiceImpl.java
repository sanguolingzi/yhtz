package com.yinhetianze.business.activity.service.busi.impl;

import com.yinhetianze.business.activity.mapper.busi.ActivityProductImgBusiMapper;
import com.yinhetianze.business.activity.service.busi.ActivityProductImgBusiService;
import com.yinhetianze.pojo.product.ActivityProductImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityProductImgBusiServiceImpl implements ActivityProductImgBusiService
{
    @Autowired
    private ActivityProductImgBusiMapper mapper;

    @Override
    public int insertSelective(ActivityProductImgPojo activityProductImgPojo) {
        return mapper.insertSelective(activityProductImgPojo);
    }

    @Override
    public void updateOneAreaImgPojoList(ActivityProductImgPojo activityProductImgPojo) {
        mapper.updateByPrimaryKeySelective(activityProductImgPojo);
    }
}