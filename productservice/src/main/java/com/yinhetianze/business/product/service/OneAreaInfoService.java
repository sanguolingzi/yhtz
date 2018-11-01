package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.OneAreaPojo;

import java.util.List;
import java.util.Map;

public interface OneAreaInfoService
{
    List<Map> selectBackstageOneAreaList(OneAreaPojo oneAreaPojo);

    OneAreaPojo selectOne(OneAreaPojo oneAreaPojo);

    List<Map>selectOneAreaList(OneAreaPojo oneAreaPojo);

    List<Map>selectOneAreaDetails(OneAreaPojo oneAreaPojo);
}