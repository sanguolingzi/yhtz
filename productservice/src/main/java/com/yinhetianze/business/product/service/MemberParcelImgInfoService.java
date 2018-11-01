package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.MemberParcelImgPojo;

import java.util.List;

public interface MemberParcelImgInfoService
{
    List<MemberParcelImgPojo> selectMemberParcelImgList(MemberParcelImgPojo memberParcelImgPojo);

}