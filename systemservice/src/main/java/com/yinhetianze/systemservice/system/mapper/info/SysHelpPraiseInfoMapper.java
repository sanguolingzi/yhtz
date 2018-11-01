package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.SysHelpPraisePojo;
import com.yinhetianze.systemservice.system.model.SysHelpPraiseModel;

public interface SysHelpPraiseInfoMapper extends InfoMapper<SysHelpPraisePojo> {

    int  selectSysHelpPraiseId(SysHelpPraiseModel sysHelpPraiseModel);
}