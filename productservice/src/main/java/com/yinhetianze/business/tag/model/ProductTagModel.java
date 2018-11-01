package com.yinhetianze.business.tag.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 * 商品活动特性标签
 */
public class ProductTagModel extends PageModel
{
    private String tagName;

    private Integer tagId;

    private String tagContent;

    private Short isShow;

    private String tagImg;

    private Short delFlag;

    private Integer prodId;

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public Integer getTagId()
    {
        return tagId;
    }

    public void setTagId(Integer tagId)
    {
        this.tagId = tagId;
    }

    public String getTagContent()
    {
        return tagContent;
    }

    public void setTagContent(String tagContent)
    {
        this.tagContent = tagContent;
    }

    public Short getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Short isShow)
    {
        this.isShow = isShow;
    }

    public String getTagImg()
    {
        return tagImg;
    }

    public void setTagImg(String tagImg)
    {
        this.tagImg = tagImg;
    }

    public Short getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Short delFlag)
    {
        this.delFlag = delFlag;
    }

    public Integer getProdId()
    {
        return prodId;
    }

    public void setProdId(Integer prodId)
    {
        this.prodId = prodId;
    }
}
