package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.NoticePojo;

public interface NoticeBusiService
{
    int updateByPrimaryKeySelective(NoticePojo noticePojo);

    int insertSelective(NoticePojo noticePojo);

    int deleteBatch(String ids) throws BusinessException;
}