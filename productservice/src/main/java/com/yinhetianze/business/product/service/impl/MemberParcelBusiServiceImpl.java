package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.MemberParcelBusiMapper;
import com.yinhetianze.business.product.mapper.MemberParcelInfoMapper;
import com.yinhetianze.business.product.service.MemberParcelBusiService;
import com.yinhetianze.business.product.service.MemberParcelInfoService;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberParcelBusiServiceImpl implements MemberParcelBusiService
{
    @Autowired
    private MemberParcelBusiMapper mapper;

    @Override
    public int addMemberParcel(MemberParcelPojo memberParcelPojo) {
        return mapper.insertSelective(memberParcelPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberParcelPojo memberParcelPojo) {
        return mapper.updateByPrimaryKeySelective(memberParcelPojo);
    }
}