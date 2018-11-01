package com.yinhetianze.systemservice.coupon.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.Date;

public class CouponModel extends PageModel{

    private Integer id;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券金额
     */
    private BigDecimal couponAmount;

    /**
     * 是否有期限 0=无期限 1=有期限 默认无期限
     */
    private Short isDeadlines;

    /**
     * 优惠券期限
     */
    private Integer termOfValidity;

    /**
     * 优惠券分类 0=无门槛全品类 1=无门槛单项类 2=满减全品类 3=满减单项类 默认无门槛全品类
     */
    private Short couponCate;

    /**
     * 满多少金额才可以使用
     */
    private BigDecimal satisfyAmount;

    /**
     * 商品分类ID
     */
    private Integer prodCateId;

    private Integer createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

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
     * 获取优惠券名称
     *
     * @return coupon_name - 优惠券名称
     */
    public String getCouponName() {
        return couponName;
    }

    /**
     * 设置优惠券名称
     *
     * @param couponName 优惠券名称
     */
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    /**
     * 获取优惠券金额
     *
     * @return coupon_amount - 优惠券金额
     */
    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    /**
     * 设置优惠券金额
     *
     * @param couponAmount 优惠券金额
     */
    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    /**
     * 获取是否有期限 0=无期限 1=有期限 默认无期限
     *
     * @return is_deadlines - 是否有期限 0=无期限 1=有期限 默认无期限
     */
    public Short getIsDeadlines() {
        return isDeadlines;
    }

    /**
     * 设置是否有期限 0=无期限 1=有期限 默认无期限
     *
     * @param isDeadlines 是否有期限 0=无期限 1=有期限 默认无期限
     */
    public void setIsDeadlines(Short isDeadlines) {
        this.isDeadlines = isDeadlines;
    }

    /**
     * 获取优惠券期限
     *
     * @return term_of_validity - 优惠券期限
     */
    public Integer getTermOfValidity() {
        return termOfValidity;
    }

    /**
     * 设置优惠券期限
     *
     * @param termOfValidity 优惠券期限
     */
    public void setTermOfValidity(Integer termOfValidity) {
        this.termOfValidity = termOfValidity;
    }

    /**
     * 获取优惠券分类 0=无门槛全品类 1=无门槛单项类 2=满减全品类 3=满减单项类 默认无门槛全品类
     *
     * @return coupon_cate - 优惠券分类 0=无门槛全品类 1=无门槛单项类 2=满减全品类 3=满减单项类 默认无门槛全品类
     */
    public Short getCouponCate() {
        return couponCate;
    }

    /**
     * 设置优惠券分类 0=无门槛全品类 1=无门槛单项类 2=满减全品类 3=满减单项类 默认无门槛全品类
     *
     * @param couponCate 优惠券分类 0=无门槛全品类 1=无门槛单项类 2=满减全品类 3=满减单项类 默认无门槛全品类
     */
    public void setCouponCate(Short couponCate) {
        this.couponCate = couponCate;
    }

    /**
     * 获取满多少金额才可以使用
     *
     * @return satisfy_amount - 满多少金额才可以使用
     */
    public BigDecimal getSatisfyAmount() {
        return satisfyAmount;
    }

    /**
     * 设置满多少金额才可以使用
     *
     * @param satisfyAmount 满多少金额才可以使用
     */
    public void setSatisfyAmount(BigDecimal satisfyAmount) {
        this.satisfyAmount = satisfyAmount;
    }

    /**
     * 获取商品分类ID
     *
     * @return prod_cate_id - 商品分类ID
     */
    public Integer getProdCateId() {
        return prodCateId;
    }

    /**
     * 设置商品分类ID
     *
     * @param prodCateId 商品分类ID
     */
    public void setProdCateId(Integer prodCateId) {
        this.prodCateId = prodCateId;
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

}