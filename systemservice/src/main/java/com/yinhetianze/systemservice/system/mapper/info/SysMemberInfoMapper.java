package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.BusiSysMemberInfoPojo;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;

import java.util.List;

public interface SysMemberInfoMapper extends InfoMapper<BusiSysMemberInfoPojo> {
    List<SysMemberInfoModel> selectParentSystemMember(SysMemberInfoModel sysMemberInfoModel);

    List<SysMemberInfoModel> selectChildSystemMember(SysMemberInfoModel sysMemberInfoModel);
}