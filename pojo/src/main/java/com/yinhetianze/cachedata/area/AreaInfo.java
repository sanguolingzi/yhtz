package com.yinhetianze.cachedata.area;

import java.io.Serializable;
import java.util.Map;

/**
 * 地区信息数据对象
 */
public class AreaInfo implements Serializable
{
    /**
     * 地区编码
     */
    private String code;

    /**
     * 地区名称
     */
    private String name;

    /**
     * 地区级别
     * 0=国家，1=省份，2=城市，3=地级区县
     */
    private Integer type;

    /**
     * 上级地区id
     */
    private String parentId;

    /**
     * 下级地区
     */
    private Map<String, AreaInfo> childList;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Map<String, AreaInfo> getChildList()
    {
        return childList;
    }

    public void setChildList(Map<String, AreaInfo> childList)
    {
        this.childList = childList;
    }

    /**
     * 用于浅复制
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public AreaInfo clone() throws CloneNotSupportedException
    {
        AreaInfo ai = new AreaInfo();
        ai.setCode(code);
        ai.setName(name);
        ai.setParentId(parentId);
        ai.setType(type);
        return ai;
    }
}
