package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.MallActivityPojo;

public interface MallActivityBusiService
{
    int insertSelective(MallActivityPojo mallActivityPojo);

    int addInfo(MallActivityModel mallActivityModel) throws BusinessException;

    int updateInfo(MallActivityModel mallActivityModel) throws BusinessException;

    int updateByPrimaryKeySelective(MallActivityPojo mallActivityPojo);

    int deleteBatch(String ids) throws BusinessException;
}