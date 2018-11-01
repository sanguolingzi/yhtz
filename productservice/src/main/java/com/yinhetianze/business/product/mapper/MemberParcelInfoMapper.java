package com.yinhetianze.business.product.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.product.MemberParcelPojo;

import java.util.List;
import java.util.Map;

public interface MemberParcelInfoMapper extends InfoMapper<MemberParcelPojo> {

    List<Map>selectBackstageMemberParcelList(MemberParcelPojo memberParcelPojo);
    List<Map>selectMemberParcelList(MemberParcelPojo memberParcelPojo);
    List<Map>selectMemberParcelDetails(MemberParcelPojo memberParcelPojo);

}