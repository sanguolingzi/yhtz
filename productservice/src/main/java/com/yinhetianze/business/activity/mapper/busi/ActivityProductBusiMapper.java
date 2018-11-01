package com.yinhetianze.business.activity.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.ActivityProductPojo;

import java.util.Map;

public interface ActivityProductBusiMapper extends BusiMapper<ActivityProductPojo> {

    int updateStorage(Map<String,Object> map);
}