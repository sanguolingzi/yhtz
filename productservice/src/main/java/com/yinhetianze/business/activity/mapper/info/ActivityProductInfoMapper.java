package com.yinhetianze.business.activity.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.ActivityProductPojo;

import java.util.List;
import java.util.Map;

public interface ActivityProductInfoMapper extends InfoMapper<ActivityProductPojo> {

    List<Map>selectBackstageActivityProductList(ActivityProductPojo activityProductPojo);
    List<Map>selectActivityProductList(ActivityProductPojo activityProductPojo);
    List<Map> selectActivityProduct(ActivityProductPojo activityProductPojo);
    List<ActivityProductPojo>putawayActivityProduct(ActivityProductPojo activityProductPojo);
}