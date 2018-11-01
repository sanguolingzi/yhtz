package com.yinhetianze.systemservice.user.service.info;

import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;

import java.util.List;

public interface SysOptorInfoService
{
    List<BusiSysOptorModel> selectSysOptorList(BusiSysOptorPageModel busiSysOptorPageModel);

    BusiSysOptorPojo select(BusiSysOptorPojo busiSysOptorPojo);
}