package com.yinhetianze.business.category.service;

import com.yinhetianze.pojo.category.CateParamRelaPojo;

public interface CateParamRelaBusiService
{
   int insertSelective(CateParamRelaPojo cateParamRelaPojo);

   int updateByPrimaryKeySelective(CateParamRelaPojo cateParamRelaPojo);
}