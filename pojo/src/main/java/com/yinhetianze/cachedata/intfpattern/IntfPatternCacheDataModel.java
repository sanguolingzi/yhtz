package com.yinhetianze.cachedata.intfpattern;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class IntfPatternCacheDataModel implements Serializable
{
    private List<IntfPatternInfo> intfPatternList;

    private Map<String, IntfPatternInfo> intfPatternMap;

    private Map<String, Map<String, IntfPatternInfo>> moduleIntfPattern;

    private Map<String, Map<String, IntfPatternInfo>> permissionIntfPattern;

    public List<IntfPatternInfo> getIntfPatternList()
    {
        return intfPatternList;
    }

    public void setIntfPatternList(List<IntfPatternInfo> intfPatternList)
    {
        this.intfPatternList = intfPatternList;
    }

    public Map<String, IntfPatternInfo> getIntfPatternMap()
    {
        return intfPatternMap;
    }

    public void setIntfPatternMap(Map<String, IntfPatternInfo> intfPatternMap)
    {
        this.intfPatternMap = intfPatternMap;
    }

    public Map<String, Map<String, IntfPatternInfo>> getModuleIntfPattern()
    {
        return moduleIntfPattern;
    }

    public void setModuleIntfPattern(Map<String, Map<String, IntfPatternInfo>> moduleIntfPattern)
    {
        this.moduleIntfPattern = moduleIntfPattern;
    }

    public Map<String, Map<String, IntfPatternInfo>> getPermissionIntfPattern()
    {
        return permissionIntfPattern;
    }

    public void setPermissionIntfPattern(Map<String, Map<String, IntfPatternInfo>> permissionIntfPattern)
    {
        this.permissionIntfPattern = permissionIntfPattern;
    }
}
