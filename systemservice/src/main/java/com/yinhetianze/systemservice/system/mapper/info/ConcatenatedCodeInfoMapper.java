package com.yinhetianze.systemservice.system.mapper.info;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.ConcatenatedCodePojo;

import java.util.List;
import java.util.Map;

public interface ConcatenatedCodeInfoMapper extends InfoMapper<ConcatenatedCodePojo> {

    List<Map> selectConcatenatedCodeList();

    List<Map>selectConcatenatedCodeNameList();
    List<Map>selectConcatenatedCode(ConcatenatedCodePojo concatenatedCodePojo);
}