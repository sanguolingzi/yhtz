package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.MemberParcelImgPojo;
import com.yinhetianze.pojo.product.MemberParcelPojo;

public interface MemberParcelImgBusiService
{
    int insertSelective(MemberParcelImgPojo memberParcelImgPojo);

    void updateMemberParcelImgList(MemberParcelImgPojo memberParcelImgPojo);
}