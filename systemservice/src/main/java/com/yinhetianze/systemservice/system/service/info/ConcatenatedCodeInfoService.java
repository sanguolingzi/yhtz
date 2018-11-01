package com.yinhetianze.systemservice.system.service.info;

import com.yinhetianze.pojo.back.ConcatenatedCodePojo;

import java.util.List;
import java.util.Map;

public interface ConcatenatedCodeInfoService
{
    List<Map> selectConcatenatedCodeList();

    ConcatenatedCodePojo selectOne(ConcatenatedCodePojo concatenatedCodePojo);
    List<Map>selectConcatenatedCodeNameList();
    List<Map>selectConcatenatedCode(ConcatenatedCodePojo concatenatedCodePojo);
}