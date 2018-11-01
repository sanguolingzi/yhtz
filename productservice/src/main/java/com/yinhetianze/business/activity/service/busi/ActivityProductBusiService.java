package com.yinhetianze.business.activity.service.busi;

import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.product.ActivityProductPojo;

import java.util.Map;

public interface ActivityProductBusiService
{
    int updateStorage(Map<String,Object> map);
    int addActivityProduct(ActivityProductPojo activityProductPojo);
    int updateByPrimaryKeySelective(ActivityProductPojo activityProductPojo);
    //int addInfo(ActivityProductModel activityProductModel)throws BusinessException;
}