package com.yinhetianze.systemservice.user.mapper.info;

import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.systemservice.user.model.BusiSysOptorPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;

import java.util.List;

public interface SysOptorInfoMapper extends InfoMapper<BusiSysOptorPojo> {

    List<BusiSysOptorModel> selectSysOptorList(BusiSysOptorPageModel busiSysOptorPageModel);

}