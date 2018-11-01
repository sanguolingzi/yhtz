package com.yinhetianze.business.category.service.impl;

import com.yinhetianze.business.category.mapper.CateParamRelaBusiMapper;
import com.yinhetianze.business.category.mapper.CateParamRelaInfoMapper;
import com.yinhetianze.business.category.service.CateParamRelaBusiService;
import com.yinhetianze.pojo.category.CateParamRelaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CateParamRelaBusiServiceImpl implements CateParamRelaBusiService
{
    @Autowired
    private CateParamRelaBusiMapper mapper;

    @Override
    public int insertSelective(CateParamRelaPojo cateParamRelaPojo) {
        return mapper.insertSelective(cateParamRelaPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(CateParamRelaPojo cateParamRelaPojo) {
        return mapper.updateByPrimaryKeySelective(cateParamRelaPojo);
    }
}