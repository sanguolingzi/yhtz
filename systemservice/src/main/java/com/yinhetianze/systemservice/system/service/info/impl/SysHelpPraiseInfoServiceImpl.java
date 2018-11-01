package com.yinhetianze.systemservice.system.service.info.impl;

import com.yinhetianze.systemservice.system.mapper.info.SysHelpPraiseInfoMapper;
import com.yinhetianze.systemservice.system.model.SysHelpPraiseModel;
import com.yinhetianze.systemservice.system.service.info.SysHelpPraiseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysHelpPraiseInfoServiceImpl implements SysHelpPraiseInfoService
{
    @Autowired
    private SysHelpPraiseInfoMapper mapper;

    @Override
    public int selectSysHelpPraiseId(SysHelpPraiseModel sysHelpPraiseModel) {
        return mapper.selectSysHelpPraiseId(sysHelpPraiseModel);
    }
}