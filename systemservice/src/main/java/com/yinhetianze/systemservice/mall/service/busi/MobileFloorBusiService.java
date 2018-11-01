package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorModel;

public interface MobileFloorBusiService
{
    int insertSelective(MobileFloorPojo mobileFloorPojo);
    int addInfo(MobileFloorModel mobileFloorModel) throws BusinessException;
    int updateByPrimaryKeySelective(MobileFloorPojo mobileFloorPojo);
    int updateInfo(MobileFloorModel mobileFloorModel) throws BusinessException;
}