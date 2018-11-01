package com.yinhetianze.business.category.model;

import com.yinhetianze.core.business.httprequest.PageModel;

/**
 * 商品分类model
 */
public class CategoryModel extends PageModel
{
    private Integer id;

    private String cateName;

    private Integer parentId;

    private Short isShow;

    private Short sort;

    private Short cateLevel;

    private String cateImg;

    private Integer createUser;

    private Integer parentCateLevel;

    private Integer parentIsLeaf;

    private Short cateType;

    private Integer updateUser;

    private String cateMobileName;

    private  Short cateBelong;
    private  String shoppingGuide;

    public String getShoppingGuide() {
        return shoppingGuide;
    }

    public void setShoppingGuide(String shoppingGuide) {
        this.shoppingGuide = shoppingGuide;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCateName()
    {
        return cateName;
    }

    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public Short getIsShow()
    {
        return isShow;
    }

    public void setIsShow(Short isShow)
    {
        this.isShow = isShow;
    }

    public Short getSort()
    {
        return sort;
    }

    public void setSort(Short sort)
    {
        this.sort = sort;
    }

    public Short getCateLevel()
    {
        return cateLevel;
    }

    public void setCateLevel(Short cateLevel)
    {
        this.cateLevel = cateLevel;
    }

    public String getCateImg()
    {
        return cateImg;
    }

    public void setCateImg(String cateImg)
    {
        this.cateImg = cateImg;
    }

    public Integer getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(Integer createUser)
    {
        this.createUser = createUser;
    }

    public Integer getParentCateLevel()
    {
        return parentCateLevel;
    }

    public void setParentCateLevel(Integer parentCateLevel)
    {
        this.parentCateLevel = parentCateLevel;
    }

    public Integer getParentIsLeaf()
    {
        return parentIsLeaf;
    }

    public void setParentIsLeaf(Integer parentIsLeaf)
    {
        this.parentIsLeaf = parentIsLeaf;
    }

    public Short getCateType()
    {
        return cateType;
    }

    public void setCateType(Short cateType)
    {
        this.cateType = cateType;
    }

    public Integer getUpdateUser()
    {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser)
    {
        this.updateUser = updateUser;
    }

    public String getCateMobileName() {
        return cateMobileName;
    }

    public void setCateMobileName(String cateMobileName) {
        this.cateMobileName = cateMobileName;
    }

    public Short getCateBelong() {
        return cateBelong;
    }

    public void setCateBelong(Short cateBelong) {
        this.cateBelong = cateBelong;
    }
}
