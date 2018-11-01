package com.yinhetianze.business.activity.service.busi;

import com.yinhetianze.pojo.product.ActivityProductImgPojo;

public interface ActivityProductImgBusiService
{
    int insertSelective(ActivityProductImgPojo activityProductImgPojo);
    void updateOneAreaImgPojoList(ActivityProductImgPojo activityProductImgPojo);
}