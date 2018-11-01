package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.SysFloorPojo;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;

public interface SysFloorBusiService
{
    int insertSelective(SysFloorPojo sysFloorPojo);

    int updateByPrimaryKeySelective(SysFloorPojo sysFloorPojo);

    int deleteBatch(String ids) throws BusinessException;

    int updateInfo(SysFloorModel sysFloorModel) throws BusinessException;

    int addInfo(SysFloorModel sysFloorModel) throws BusinessException;

}