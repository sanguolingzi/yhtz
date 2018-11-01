package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.MemberParcelImgBusiMapper;
import com.yinhetianze.business.product.service.MemberParcelImgBusiService;
import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberParcelImgBusiServiceImpl implements MemberParcelImgBusiService
{
    @Autowired
    private MemberParcelImgBusiMapper mapper;

    @Override
    public int insertSelective(MemberParcelImgPojo memberParcelImgPojo) {
        return mapper.insertSelective(memberParcelImgPojo);
    }

    @Override
    public void updateMemberParcelImgList(MemberParcelImgPojo memberParcelImgPojo) {
        mapper.updateByPrimaryKeySelective(memberParcelImgPojo);
    }
}