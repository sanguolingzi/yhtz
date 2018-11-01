package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.SysBannerPojo;

public interface SysBannerBusiService
{
    int insertSelective(SysBannerPojo sysBannerPojo);

    int updateByPrimaryKeySelective(SysBannerPojo sysBannerPojo);

    int deleteInfo(String ids) throws BusinessException;

    int addInfo(SysBannerModel sysBannerModel) throws BusinessException;

    int updateInfo(SysBannerModel sysBannerModel) throws BusinessException;
}