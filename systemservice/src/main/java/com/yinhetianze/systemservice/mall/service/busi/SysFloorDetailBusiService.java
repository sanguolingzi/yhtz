package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.SysFloorDetailPojo;

public interface SysFloorDetailBusiService
{
    int addInfo(SysFloorDetailModel sysFloorDetailModel) throws BusinessException;

    int updateByPrimaryKeySelective(SysFloorDetailModel sysFloorDetailModel);

    int updateByPrimaryKeySelective(SysFloorDetailPojo sysFloorDetailPojo);

    int deleteBatch(String ids) throws BusinessException;

    int updateInfo(SysFloorDetailModel sysFloorDetailModel) throws BusinessException;
}