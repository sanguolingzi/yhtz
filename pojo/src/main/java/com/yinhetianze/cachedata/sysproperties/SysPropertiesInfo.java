package com.yinhetianze.cachedata.sysproperties;

import java.io.Serializable;

public class SysPropertiesInfo implements Serializable
{
    private String id;

    private String name;

    private String value;

    private String module;

    private String description;

    private Integer isWork;

    private Integer delFlag;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getIsWork()
    {
        return isWork;
    }

    public void setIsWork(Integer isWork)
    {
        this.isWork = isWork;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
}
