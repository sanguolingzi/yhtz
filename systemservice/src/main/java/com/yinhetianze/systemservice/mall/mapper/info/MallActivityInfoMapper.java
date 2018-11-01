package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.MallActivityPojo;

import java.util.List;

public interface MallActivityInfoMapper extends InfoMapper<MallActivityPojo> {

    List<MallActivityModel> selectForCustomerMessage(MallActivityModel mallActivityModel);

    List<MallActivityModel> selectMallActivityList(MallActivityModel mallActivityModel);
}