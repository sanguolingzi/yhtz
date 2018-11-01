package com.yinhetianze.systemservice.system.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;


public interface SysMemberBusiService
{
    int addParentSysMember(SysMemberInfoModel sysMemberInfoModel) throws BusinessException;

    int addChildSysMember(SysMemberInfoModel sysMemberInfoModel) throws BusinessException;

    int updateParentSysMember(SysMemberInfoModel sysMemberInfoModel) throws BusinessException;

    int updateChildSysMember(SysMemberInfoModel sysMemberInfoModel) throws BusinessException;

    int deleteParentSysMember(String ids) throws BusinessException;

    int deleteChildSysMember(String ids) throws BusinessException;

}