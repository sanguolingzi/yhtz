package com.yinhetianze.business.activity.service.info.impl;

import com.yinhetianze.pojo.product.ActivityProductPojo;
import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.activity.mapper.info.ActivityProductInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class ActivityProductInfoServiceImpl implements ActivityProductInfoService
{
    @Autowired
    private ActivityProductInfoMapper mapper;

    @Override
    public ActivityProductPojo selectOne(ActivityProductPojo activityProductPojo) {
        return mapper.selectOne(activityProductPojo);
    }

    @Override
    public List<Map> selectBackstageActivityProductList(ActivityProductPojo activityProductPojo) {
        return mapper.selectBackstageActivityProductList(activityProductPojo);
    }

    @Override
    public List<Map> selectActivityProductList(ActivityProductPojo activityProductPojo) {
        return mapper.selectActivityProductList(activityProductPojo);
    }

    @Override
    public List<Map> selectActivityProduct(ActivityProductPojo activityProductPojo) {
        return mapper.selectActivityProduct(activityProductPojo);
    }

    @Override
    public List<ActivityProductPojo> putawayActivityProduct(ActivityProductPojo activityProductPojo) {
        return mapper.putawayActivityProduct(activityProductPojo);
    }
}