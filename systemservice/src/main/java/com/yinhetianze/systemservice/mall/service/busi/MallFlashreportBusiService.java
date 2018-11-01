package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.BusiMallFlashreportPojo;

public interface MallFlashreportBusiService {

    int insertSelective(BusiMallFlashreportPojo busiMallFlashreportPojo );

    int deleteBatch(String ids)throws BusinessException;

    int updateInfo(MallFlashreportModel mallFlashreportModel)throws BusinessException;

    int updateByPrimaryKeySelective(BusiMallFlashreportPojo busiMallFlashreportPojo);

}
