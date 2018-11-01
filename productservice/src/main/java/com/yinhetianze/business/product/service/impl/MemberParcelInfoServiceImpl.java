package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.MemberParcelInfoService;
import com.yinhetianze.pojo.product.MemberParcelPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.MemberParcelInfoMapper;

import java.util.List;
import java.util.Map;

@Service
public class MemberParcelInfoServiceImpl implements MemberParcelInfoService
{
    @Autowired
    private MemberParcelInfoMapper mapper;

    @Override
    public List<Map> selectBackstageMemberParcelList(MemberParcelPojo memberParcelPojo) {
        return mapper.selectBackstageMemberParcelList(memberParcelPojo);
    }

    @Override
    public MemberParcelPojo selectOne(MemberParcelPojo memberParcelPojo) {
        return mapper.selectOne(memberParcelPojo);
    }

    @Override
    public List<Map> selectMemberParcelList(MemberParcelPojo memberParcelPojo) {
        return mapper.selectMemberParcelList(memberParcelPojo);
    }

    @Override
    public List<Map> selectMemberParcelDetails(MemberParcelPojo memberParcelPojo) {
        return mapper.selectMemberParcelDetails(memberParcelPojo);
    }
}