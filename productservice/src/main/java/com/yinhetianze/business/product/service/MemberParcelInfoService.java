package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.MemberParcelPojo;

import java.util.List;
import java.util.Map;

public interface MemberParcelInfoService
{
    List<Map> selectBackstageMemberParcelList(MemberParcelPojo memberParcelPojo);

    MemberParcelPojo selectOne(MemberParcelPojo memberParcelPojo);

    List<Map>selectMemberParcelList(MemberParcelPojo memberParcelPojo);

    List<Map>selectMemberParcelDetails(MemberParcelPojo memberParcelPojo);
}