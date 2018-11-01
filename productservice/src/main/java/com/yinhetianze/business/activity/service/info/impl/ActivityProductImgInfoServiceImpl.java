package com.yinhetianze.business.activity.service.info.impl;

import com.yinhetianze.business.activity.service.info.ActivityProductImgInfoService;
import com.yinhetianze.pojo.product.ActivityProductImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.activity.mapper.info.ActivityProductImgInfoMapper;
import java.util.List;

@Service
public class ActivityProductImgInfoServiceImpl implements ActivityProductImgInfoService
{
    @Autowired
    private ActivityProductImgInfoMapper mapper;

    @Override
    public List<ActivityProductImgPojo> selectOneAreaImgList(ActivityProductImgPojo activityProductImgPojo) {
        return mapper.select(activityProductImgPojo);
    }
}