package com.yinhetianze.common.business.sys.sysproperties.mapper;

import java.util.List;
import java.util.Map;

public interface SysPropertiesInfoMapper
{
    List<Map<String, Object>> getSysPropertiesInfoList(Map<String, Object> params);
}
