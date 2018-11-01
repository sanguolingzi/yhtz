package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorDetailModel;


public interface MobileFloorDetailBusiService
{
    int insertSelective(MobileFloorDetailPojo mobileFloorDetailPojo);
    int updateByPrimaryKeySelective(MobileFloorDetailPojo mobileFloorDetailPojo);
}