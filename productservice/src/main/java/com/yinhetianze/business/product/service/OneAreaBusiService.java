package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.OneAreaPojo;

public interface OneAreaBusiService
{
    int addOneArea(OneAreaPojo oneAreaPojo);
    int updateByPrimaryKeySelective(OneAreaPojo oneAreaPojo);
}