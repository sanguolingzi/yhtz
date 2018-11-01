package com.yinhetianze.pojo.product;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_fresher")
public class ProductFresherPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品标题
     */
    @Column(name = "prod_title")
    private String prodTitle;

    /**
     * 商品副标题
     */
    @Column(name = "prod_subtitle")
    private String prodSubtitle;

    /**
     * 商品名称
     */
    @Column(name = "prod_name")
    private String prodName;

    /**
     * 商品描述
     */
    @Column(name = "prod_describe")
    private String prodDescribe;

    /**
     * U币抵扣量
     */
    @Column(name = "u_price")
    private BigDecimal uPrice;

    /**
     * 商品销售价
     */
    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商品主图
     */
    @Column(name = "prod_img")
    private String prodImg;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 库存量
     */
    @Column(name = "prod_storage")
    private Integer prodStorage;

    /**
     * 是否首页显示，0显示，1不显示，默认1不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 排序编号
     */
    private Short sort;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 规格json
     */
    @Column(name = "prod_speci")
    private String prodSpeci;

    /**
     * 是否删除，1已删除，0未删除，默认0未删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Integer createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新操作人
     */
    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 商品图文详情
     */
    @Column(name = "prod_details")
    private String prodDetails;

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
     * 获取商品标题
     *
     * @return prod_title - 商品标题
     */
    public String getProdTitle() {
        return prodTitle;
    }

    /**
     * 设置商品标题
     *
     * @param prodTitle 商品标题
     */
    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    /**
     * 获取商品副标题
     *
     * @return prod_subtitle - 商品副标题
     */
    public String getProdSubtitle() {
        return prodSubtitle;
    }

    /**
     * 设置商品副标题
     *
     * @param prodSubtitle 商品副标题
     */
    public void setProdSubtitle(String prodSubtitle) {
        this.prodSubtitle = prodSubtitle;
    }

    /**
     * 获取商品名称
     *
     * @return prod_name - 商品名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 设置商品名称
     *
     * @param prodName 商品名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    /**
     * 获取商品描述
     *
     * @return prod_describe - 商品描述
     */
    public String getProdDescribe() {
        return prodDescribe;
    }

    /**
     * 设置商品描述
     *
     * @param prodDescribe 商品描述
     */
    public void setProdDescribe(String prodDescribe) {
        this.prodDescribe = prodDescribe;
    }

    /**
     * 获取U币抵扣量
     *
     * @return u_price - U币抵扣量
     */
    public BigDecimal getuPrice() {
        return uPrice;
    }

    /**
     * 设置U币抵扣量
     *
     * @param uPrice U币抵扣量
     */
    public void setuPrice(BigDecimal uPrice) {
        this.uPrice = uPrice;
    }

    /**
     * 获取商品销售价
     *
     * @return sell_price - 商品销售价
     */
    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    /**
     * 设置商品销售价
     *
     * @param sellPrice 商品销售价
     */
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * 获取商品主图
     *
     * @return prod_img - 商品主图
     */
    public String getProdImg() {
        return prodImg;
    }

    /**
     * 设置商品主图
     *
     * @param prodImg 商品主图
     */
    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    /**
     * 获取运费
     *
     * @return freight - 运费
     */
    public BigDecimal getFreight() {
        return freight;
    }

    /**
     * 设置运费
     *
     * @param freight 运费
     */
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    /**
     * 获取库存量
     *
     * @return prod_storage - 库存量
     */
    public Integer getProdStorage() {
        return prodStorage;
    }

    /**
     * 设置库存量
     *
     * @param prodStorage 库存量
     */
    public void setProdStorage(Integer prodStorage) {
        this.prodStorage = prodStorage;
    }

    /**
     * 获取是否首页显示，0显示，1不显示，默认1不显示
     *
     * @return is_show - 是否首页显示，0显示，1不显示，默认1不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否首页显示，0显示，1不显示，默认1不显示
     *
     * @param isShow 是否首页显示，0显示，1不显示，默认1不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取排序编号
     *
     * @return sort - 排序编号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序编号
     *
     * @param sort 排序编号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取规格json
     *
     * @return prod_speci - 规格json
     */
    public String getProdSpeci() {
        return prodSpeci;
    }

    /**
     * 设置规格json
     *
     * @param prodSpeci 规格json
     */
    public void setProdSpeci(String prodSpeci) {
        this.prodSpeci = prodSpeci;
    }

    /**
     * 获取是否删除，1已删除，0未删除，默认0未删除
     *
     * @return del_flag - 是否删除，1已删除，0未删除，默认0未删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除，1已删除，0未删除，默认0未删除
     *
     * @param delFlag 是否删除，1已删除，0未删除，默认0未删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取创建人
     *
     * @return create_user - 创建人
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人
     *
     * @param createUser 创建人
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
     * 获取更新操作人
     *
     * @return update_user - 更新操作人
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新操作人
     *
     * @param updateUser 更新操作人
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取商品图文详情
     *
     * @return prod_details - 商品图文详情
     */
    public String getProdDetails() {
        return prodDetails;
    }

    /**
     * 设置商品图文详情
     *
     * @param prodDetails 商品图文详情
     */
    public void setProdDetails(String prodDetails) {
        this.prodDetails = prodDetails;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
}