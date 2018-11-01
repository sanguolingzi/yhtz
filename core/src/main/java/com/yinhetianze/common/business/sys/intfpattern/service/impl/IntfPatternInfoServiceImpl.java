package com.yinhetianze.common.business.sys.intfpattern.service.impl;

import com.yinhetianze.common.business.sys.intfpattern.mapper.IntfPatternInfoMapper;
import com.yinhetianze.common.business.sys.intfpattern.service.IntfPatternInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
public class IntfPatternInfoServiceImpl implements IntfPatternInfoService
{
    @Autowired
    private IntfPatternInfoMapper mapper;

    @Override
    public Map<String, Object> findIntfPattern(String pattern)
    {
        return mapper.findIntfPattern(pattern);
    }

    @Override
    public Collection<Map<String, Object>> getIntfPattern(Map<String, Object> params)
    {
        return mapper.getIntfPattern(params);
    }

    @Override
    public Set<Map<String, Object>> getIntfPattern(Set<String> params)
    {
        return mapper.getIntfPatternRes(params);
    }
}
