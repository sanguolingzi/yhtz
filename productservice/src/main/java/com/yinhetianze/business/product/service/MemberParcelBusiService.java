package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.MemberParcelPojo;

public interface MemberParcelBusiService
{
    int addMemberParcel(MemberParcelPojo memberParcelPojo);
    int updateByPrimaryKeySelective(MemberParcelPojo memberParcelPojo);
}