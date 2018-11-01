package com.yinhetianze.common.business.sys.sysproperties.service.impl;

import com.yinhetianze.common.business.sys.sysproperties.mapper.SysPropertiesInfoMapper;
import com.yinhetianze.common.business.sys.sysproperties.service.SysPropertiesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysPropertiesInfoServiceImpl implements SysPropertiesInfoService
{
    @Autowired
    private SysPropertiesInfoMapper mapper;

    @Override
    public List<Map<String, Object>> getSysPropertiesInfoList(Map<String, Object> params)
    {
        return mapper.getSysPropertiesInfoList(params);
    }
}
