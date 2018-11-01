package com.yinhetianze.systemservice.user.service.info.impl;

import com.yinhetianze.systemservice.user.mapper.info.SysOptorInfoMapper;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import com.yinhetianze.pojo.back.*;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SysOptorInfoServiceImpl implements SysOptorInfoService
{
    @Autowired
    private SysOptorInfoMapper sysOptorInfoMapper;

    @Override
    public List<BusiSysOptorModel> selectSysOptorList(BusiSysOptorPageModel busiSysOptorPageModel) {
        return sysOptorInfoMapper.selectSysOptorList(busiSysOptorPageModel);
    }

    @Override
    public BusiSysOptorPojo select(BusiSysOptorPojo busiSysOptorPojo) {
        busiSysOptorPojo.setDelFlag((short)0);
        return sysOptorInfoMapper.selectOne(busiSysOptorPojo);
    }

}