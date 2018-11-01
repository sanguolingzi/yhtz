package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.service.MemberParcelImgInfoService;
import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.MemberParcelImgInfoMapper;

import java.util.List;

@Service
public class MemberParcelImgInfoServiceImpl implements MemberParcelImgInfoService
{
    @Autowired
    private MemberParcelImgInfoMapper mapper;

    @Override
    public List<MemberParcelImgPojo> selectMemberParcelImgList(MemberParcelImgPojo memberParcelImgPojo) {
        return mapper.select(memberParcelImgPojo);
    }
}