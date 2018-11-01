package com.yinhetianze.business.activity.service.info;

import com.yinhetianze.pojo.product.ActivityProductPojo;

import java.util.List;
import java.util.Map;

public interface ActivityProductInfoService
{
    ActivityProductPojo selectOne(ActivityProductPojo activityProductPojo);

    List<Map> selectBackstageActivityProductList(ActivityProductPojo activityProductPojo);

    List<Map>selectActivityProductList(ActivityProductPojo activityProductPojo);

    List<Map> selectActivityProduct(ActivityProductPojo activityProductPojo);

    List<ActivityProductPojo>putawayActivityProduct(ActivityProductPojo activityProductPojo);
}