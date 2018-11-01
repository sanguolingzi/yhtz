package com.yinhetianze.systemservice.mall.mapper.info;
import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.BusiMallFlashreportPojo;
import java.util.List;


public interface MallFlashreportInfoMapper extends InfoMapper<BusiMallFlashreportPojo> {
    List<MallFlashreportModel> getBusiMallFlashreport(MallFlashreportModel mallFlashreportModel);
}