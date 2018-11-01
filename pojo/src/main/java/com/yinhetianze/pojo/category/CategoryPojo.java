package com.yinhetianze.pojo.category;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_category")
public class CategoryPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 分类名称
     */
    @Column(name = "cate_name")
    private String cateName;

    /**
     * 父节点id   -1 代表 顶层  对应 cate_level  为 1
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 排序号
     */
    private Short sort;

    /**
     *  0 显示  1 不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 分类层级   1 一级    2  二级    3  三级  
     */
    @Column(name = "cate_level")
    private Short cateLevel;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否是子分类，parentId为空或为-1时为0，非空或大于0时为1，默认为0
     */
    @Column(name = "is_leaf")
    private Short isLeaf;

    /**
     * 分类图片
     */
    @Column(name = "cate_img")
    private String cateImg;

    /**
     * 商品分类所属终端
     * 0=通用，1=移动端，2=PC端
     */
    @Column(name = "cate_type")
    private Short cateType;

    /**
     * 移动端使用名称

     */
    @Column(name = "cate_mobile_name")
    private String cateMobileName;

    /**
     * 商品分类名称
     * 0实体商品分类 1虚拟商品分类
     */
    @Column(name = "cate_belong")
    private  Short cateBelong;

    /**
     * 二级分类导购词
     */
    @Column(name = "shopping_guide")
    private  String shoppingGuide;

    public String getShoppingGuide() {
        return shoppingGuide;
    }

    public void setShoppingGuide(String shoppingGuide) {
        this.shoppingGuide = shoppingGuide;
    }

    public Short getCateBelong() {
        return cateBelong;
    }

    public void setCateBelong(Short cateBelong) {
        this.cateBelong = cateBelong;
    }

    public String getCateMobileName() {

        return cateMobileName;
    }

    public void setCateMobileName(String cateMobileName) {
        this.cateMobileName = cateMobileName;
    }



    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取0 正常 1 已删除
     *
     * @return del_flag - 0 正常 1 已删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置0 正常 1 已删除
     *
     * @param delFlag 0 正常 1 已删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取分类名称
     *
     * @return cate_name - 分类名称
     */
    public String getCateName() {
        return cateName;
    }

    /**
     * 设置分类名称
     *
     * @param cateName 分类名称
     */
    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    /**
     * 获取父节点id   -1 代表 顶层  对应 cate_level  为 1
     *
     * @return parent_id - 父节点id   -1 代表 顶层  对应 cate_level  为 1
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点id   -1 代表 顶层  对应 cate_level  为 1
     *
     * @param parentId 父节点id   -1 代表 顶层  对应 cate_level  为 1
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取 0 显示  1 不显示
     *
     * @return is_show -  0 显示  1 不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置 0 显示  1 不显示
     *
     * @param isShow  0 显示  1 不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取分类层级   1 一级    2  二级    3  三级  
     *
     * @return cate_level - 分类层级   1 一级    2  二级    3  三级  
     */
    public Short getCateLevel() {
        return cateLevel;
    }

    /**
     * 设置分类层级   1 一级    2  二级    3  三级  
     *
     * @param cateLevel 分类层级   1 一级    2  二级    3  三级  
     */
    public void setCateLevel(Short cateLevel) {
        this.cateLevel = cateLevel;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_user
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * @return update_user
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * @param updateUser
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否是子分类，parentId为空或为-1时为0，非空或大于0时为1，默认为0
     *
     * @return is_leaf - 是否是子分类，parentId为空或为-1时为0，非空或大于0时为1，默认为0
     */
    public Short getIsLeaf() {
        return isLeaf;
    }

    /**
     * 设置是否是子分类，parentId为空或为-1时为0，非空或大于0时为1，默认为0
     *
     * @param isLeaf 是否是子分类，parentId为空或为-1时为0，非空或大于0时为1，默认为0
     */
    public void setIsLeaf(Short isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 获取分类图片
     *
     * @return cate_img - 分类图片
     */
    public String getCateImg() {
        return cateImg;
    }

    /**
     * 设置分类图片
     *
     * @param cateImg 分类图片
     */
    public void setCateImg(String cateImg) {
        this.cateImg = cateImg;
    }

    public Short getCateType()
    {
        return cateType;
    }

    public void setCateType(Short cateType)
    {
        this.cateType = cateType;
    }


}