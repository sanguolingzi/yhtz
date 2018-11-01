package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.OneAreaPojo;

import java.util.List;
import java.util.Map;

public interface OneAreaInfoMapper extends InfoMapper<OneAreaPojo> {

    List<Map>selectBackstageOneAreaList(OneAreaPojo oneAreaPojo);

    List<Map>selectOneAreaList(OneAreaPojo oneAreaPojo);

    List<Map>selectOneAreaDetails(OneAreaPojo oneAreaPojo);
}