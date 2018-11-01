package com.yinhetianze.common.business.sys.intfpattern.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IntfPatternInfoService
{
    Map<String, Object> findIntfPattern(String pattern);

    Collection<Map<String, Object>> getIntfPattern(Map<String, Object> params);

    Set<Map<String, Object>> getIntfPattern(Set<String> params);
}
