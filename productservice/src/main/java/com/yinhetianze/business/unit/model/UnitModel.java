package com.yinhetianze.business.unit.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 * 商品计量单位Model
 */
public class UnitModel extends PageModel
{
    private String unitName;

    private Short isShow;

    private Integer unitId;

    private Short sort;

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public Short getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Short isShow)
    {
        this.isShow = isShow;
    }

    public Integer getUnitId()
    {
        return unitId;
    }

    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Short getSort()
    {
        return sort;
    }

    public void setSort(Short sort)
    {
        this.sort = sort;
    }
}
