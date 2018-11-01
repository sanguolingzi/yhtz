package com.yinhetianze.systemservice.user.service.busi;

import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;

public interface SysOptorBusiService
{
    int addInfo(BusiSysOptorModel busiSysOptorModel) throws BusinessException;

    int updateInfo(BusiSysOptorModel busiSysOptorModel) throws BusinessException;

    int deleteInfo(Integer id)  throws BusinessException;

    int updateByPrimaryKeySelective(BusiSysOptorPojo busiSysOptorPojo);

    int insertSelective(BusiSysOptorPojo busiSysOptorPojo);
}