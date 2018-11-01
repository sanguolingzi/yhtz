package com.yinhetianze.business.activity.service.info;

import com.yinhetianze.pojo.product.ActivityProductImgPojo;
import java.util.List;

public interface ActivityProductImgInfoService
{
    List<ActivityProductImgPojo> selectOneAreaImgList(ActivityProductImgPojo activityProductImgPojo);
}