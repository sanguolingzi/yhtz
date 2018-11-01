package com.yinhetianze.business.activity.service.busi.impl;


import com.yinhetianze.business.activity.mapper.busi.ActivityProductBusiMapper;
import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.business.activity.service.busi.ActivityProductBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActivityProductBusiServiceImpl implements ActivityProductBusiService
{
    @Autowired
    private ActivityProductBusiMapper mapper;


    @Override
    public int updateStorage(Map<String, Object> map) {
        return mapper.updateStorage(map);
    }

    @Override
    public int addActivityProduct(ActivityProductPojo activityProductPojo) {
        return mapper.insertSelective(activityProductPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ActivityProductPojo activityProductPojo) {
        return  mapper.updateByPrimaryKeySelective(activityProductPojo);
    }

}