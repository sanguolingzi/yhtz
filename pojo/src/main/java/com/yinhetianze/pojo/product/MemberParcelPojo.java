package com.yinhetianze.pojo.product;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_member_parcel")
public class MemberParcelPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 礼包标题
     */
    @Column(name = "parcel_title")
    private String parcelTitle;

    /**
     * 礼包副标题
     */
    @Column(name = "parcel_subtitle")
    private String parcelSubtitle;

    /**
     * 礼包名称
     */
    @Column(name = "parcel_name")
    private String parcelName;

    /**
     * 商品销售价(展示)
     */
    @Column(name = "parcel_price")
    private BigDecimal parcelPrice;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;



    /**
     * 邮费
     */
    private BigDecimal freight;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 所属店铺
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 礼包主图
     */
    @Column(name = "parcel_img")
    private String parcelImg;

    /**
     * 礼包规格json
     */
    @Column(name = "parcel_speci")
    private String parcelSpeci;

    /**
     * 0=显示 1=不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 礼包描述
     */
    @Column(name = "parcel_describe")
    private String parcelDescribe;

    /**
     * 房卡数量
     */
    private Integer number;

    /**
     * 礼包详情
     */
    @Column(name = "parcel_details")
    private String parcelDetails;

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
     * 获取礼包标题
     *
     * @return parcel_title - 礼包标题
     */
    public String getParcelTitle() {
        return parcelTitle;
    }

    /**
     * 设置礼包标题
     *
     * @param parcelTitle 礼包标题
     */
    public void setParcelTitle(String parcelTitle) {
        this.parcelTitle = parcelTitle;
    }

    /**
     * 获取礼包副标题
     *
     * @return parcel_subtitle - 礼包副标题
     */
    public String getParcelSubtitle() {
        return parcelSubtitle;
    }

    /**
     * 设置礼包副标题
     *
     * @param parcelSubtitle 礼包副标题
     */
    public void setParcelSubtitle(String parcelSubtitle) {
        this.parcelSubtitle = parcelSubtitle;
    }

    /**
     * 获取礼包名称
     *
     * @return parcel_name - 礼包名称
     */
    public String getParcelName() {
        return parcelName;
    }

    /**
     * 设置礼包名称
     *
     * @param parcelName 礼包名称
     */
    public void setParcelName(String parcelName) {
        this.parcelName = parcelName;
    }

    /**
     * 获取商品销售价(展示)
     *
     * @return parcel_price - 商品销售价(展示)
     */
    public BigDecimal getParcelPrice() {
        return parcelPrice;
    }

    /**
     * 设置商品销售价(展示)
     *
     * @param parcelPrice 商品销售价(展示)
     */
    public void setParcelPrice(BigDecimal parcelPrice) {
        this.parcelPrice = parcelPrice;
    }


    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取邮费
     *
     * @return freight - 邮费
     */
    public BigDecimal getFreight() {
        return freight;
    }

    /**
     * 设置邮费
     *
     * @param freight 邮费
     */
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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
     * 获取所属店铺
     *
     * @return shop_id - 所属店铺
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置所属店铺
     *
     * @param shopId 所属店铺
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取礼包主图
     *
     * @return parcel_img - 礼包主图
     */
    public String getParcelImg() {
        return parcelImg;
    }

    /**
     * 设置礼包主图
     *
     * @param parcelImg 礼包主图
     */
    public void setParcelImg(String parcelImg) {
        this.parcelImg = parcelImg;
    }

    public String getParcelSpeci() {
        return parcelSpeci;
    }

    public void setParcelSpeci(String parcelSpeci) {
        this.parcelSpeci = parcelSpeci;
    }


    /**
     * 获取0=显示 1=不显示
     *
     * @return is_show - 0=显示 1=不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置0=显示 1=不显示
     *
     * @param isShow 0=显示 1=不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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
     * 获取礼包描述
     *
     * @return parcel_describe - 礼包描述
     */
    public String getParcelDescribe() {
        return parcelDescribe;
    }

    /**
     * 设置礼包描述
     *
     * @param parcelDescribe 礼包描述
     */
    public void setParcelDescribe(String parcelDescribe) {
        this.parcelDescribe = parcelDescribe;
    }

    /**
     * 获取房卡数量
     *
     * @return number - 房卡数量
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置房卡数量
     *
     * @param number 房卡数量
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取礼包详情
     *
     * @return parcel_details - 礼包详情
     */
    public String getParcelDetails() {
        return parcelDetails;
    }

    /**
     * 设置礼包详情
     *
     * @param parcelDetails 礼包详情
     */
    public void setParcelDetails(String parcelDetails) {
        this.parcelDetails = parcelDetails;
    }
}