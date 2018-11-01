package com.yinhetianze.systemservice.mall.service.info;

import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.pojo.back.BusiMallFlashreportPojo;
import java.util.List;

public interface MallFlashreportInfoService
{
    List<MallFlashreportModel> getBusiMallFlashreport(MallFlashreportModel mallFlashreportModel);

    BusiMallFlashreportPojo selectOne(BusiMallFlashreportPojo busiMallFlashreportPojo);

}