package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.pojo.back.MallActivityPojo;

import java.util.List;
public interface MallActivityInfoService
{
    List selectList(MallActivityPojo mallActivityPojo);

    List<MallActivityModel> selectMallActivityList(MallActivityModel mallActivityModel);

    MallActivityPojo selectOne(MallActivityPojo pojo);

}