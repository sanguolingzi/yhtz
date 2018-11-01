package com.yinhetianze.business.parameter.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 * 商品参数管理model
 */
public class ProductParameterModel extends PageModel
{

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 单个参数ID
     * 用于查询，更新，删除业务
     */
    private Integer paramId;

    /**
     * 参数ID集合
     * 用于绑定分类与参数关系业务
     */
    private Integer[] paramIds;

    /**
     * 排序参数
     */
    private Short sort;

    /**
     * 首字母划分
     */
    private String firstWord;

    /**
     * 是否正航显示
     */
    private Short isWholeLine;

    /**
     * 分类ID，用于参数的查询，绑定业务
     */
    private Integer cateId;

    /**
     * 以下字段暂时多余
     */
    private Short isShow;

    private Short delFlag;

    private String paramValue;

    public String getParamName()
    {
        return paramName;
    }

    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getParamValue()
    {
        return paramValue;
    }

    public void setParamValue(String paramValue)
    {
        this.paramValue = paramValue;
    }

    public Integer getParamId()
    {
        return paramId;
    }

    public void setParamId(Integer paramId)
    {
        this.paramId = paramId;
    }

    public Short getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Short isShow)
    {
        this.isShow = isShow;
    }

    public Short getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Short delFlag)
    {
        this.delFlag = delFlag;
    }

    public Short getSort()
    {
        return sort;
    }

    public void setSort(Short sort)
    {
        this.sort = sort;
    }

    public String getFirstWord()
    {
        return firstWord;
    }

    public void setFirstWord(String firstWord)
    {
        this.firstWord = firstWord;
    }

    public Integer getCateId()
    {
        return cateId;
    }

    public void setCateId(Integer cateId)
    {
        this.cateId = cateId;
    }

    public Short getIsWholeLine()
    {
        return isWholeLine;
    }

    public void setIsWholeLine(Short isWholeLine)
    {
        this.isWholeLine = isWholeLine;
    }

    public Integer[] getParamIds()
    {
        return paramIds;
    }

    public void setParamIds(Integer[] paramIds)
    {
        this.paramIds = paramIds;
    }
}
