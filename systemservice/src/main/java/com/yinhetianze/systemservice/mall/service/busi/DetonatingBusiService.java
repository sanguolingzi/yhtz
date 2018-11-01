package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.DetonatingPojo;
import com.yinhetianze.systemservice.mall.model.DetonatingModel;

public interface DetonatingBusiService
{
    int addDetonating(DetonatingPojo detonatingPojo);
    int updateByPrimaryKeySelective(DetonatingPojo detonatingPojo);
    int addInfo(DetonatingModel detonatingModel) throws BusinessException;
    int updateInfo(DetonatingModel detonatingModel) throws BusinessException;

}