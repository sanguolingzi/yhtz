package com.yinhetianze.pojo.product;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_activity_product")
public class ActivityProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品名称
     */
    @Column(name = "prod_name")
    private String prodName;

    /**
     * 商品标题
     */
    @Column(name = "prod_title")
    private String prodTitle;

    /**
     * 商品副标题
     */
    @Column(name = "prod_sub_title")
    private String prodSubTitle;

    /**
     * 活动编码，对应字典ACTIVITY_TYPE
     */
    @Column(name = "act_id")
    private String actId;

    /**
     * 活动名称
     */
    @Column(name = "act_name")
    private String actName;

    /**
     * 商品主图
     */
    @Column(name = "prod_img")
    private String prodImg;

    /**
     * 排序标志
     */
    private Short sort;

    /**
     * 商品库存
     */
    @Column(name = "prod_storage")
    private Integer prodStorage;

    /**
     * 销售价
     */
    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * U币抵扣量
     */
    @Column(name = "u_price")
    private Integer uPrice;

    /**
     * 邮费
     */
    @Column(name = "freight")
    private BigDecimal freight;

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
     * 是否首页显示，0显示，1不显示，默认不显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 是否删除，0未删除，1已删除，默认0不删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 0 上架 1 下架 2 售罄 3.待上架
     */
    @Column(name = "is_sale")
    private Short isSale;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否自动上架标志，0不自动，1自动，默认0不自动，为1自动上架时间不能为空
     */
    @Column(name = "is_auto_sale")
    private Short isAutoSale;

    /**
     * 是否自动下架，0不自动，1自动，默认0不自动，为1时自动下架时间不能为空
     */
    @Column(name = "is_auto_off")
    private Short isAutoOff;

    /**
     * 自动上架时间，is_auto_sale为1时不能为空
     */
    @Column(name = "auto_sale_time")
    private Date autoSaleTime;

    /**
     * 自动下架时间，is_auto_off为1时不能为空
     */
    @Column(name = "auto_off_time")
    private Date autoOffTime;

    /**
     * 创建者ID
     */
    @Column(name = "create_user")
    private Integer createUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新者ID
     */
    @Column(name = "update_user")
    private Integer updateUser;

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
     * @return prod_sub_title - 商品副标题
     */
    public String getProdSubTitle() {
        return prodSubTitle;
    }

    /**
     * 设置商品副标题
     *
     * @param prodSubTitle 商品副标题
     */
    public void setProdSubTitle(String prodSubTitle) {
        this.prodSubTitle = prodSubTitle;
    }

    /**
     * 获取活动编码，对应字典ACTIVITY_TYPE
     *
     * @return act_id - 活动编码，对应字典ACTIVITY_TYPE
     */
    public String getActId() {
        return actId;
    }

    /**
     * 设置活动编码，对应字典ACTIVITY_TYPE
     *
     * @param actId 活动编码，对应字典ACTIVITY_TYPE
     */
    public void setActId(String actId) {
        this.actId = actId;
    }

    /**
     * 获取活动名称
     *
     * @return act_name - 活动名称
     */
    public String getActName() {
        return actName;
    }

    /**
     * 设置活动名称
     *
     * @param actName 活动名称
     */
    public void setActName(String actName) {
        this.actName = actName;
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
     * 获取排序标志
     *
     * @return sort - 排序标志
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序标志
     *
     * @param sort 排序标志
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取商品库存
     *
     * @return prod_storage - 商品库存
     */
    public Integer getProdStorage() {
        return prodStorage;
    }

    /**
     * 设置商品库存
     *
     * @param prodStorage 商品库存
     */
    public void setProdStorage(Integer prodStorage) {
        this.prodStorage = prodStorage;
    }

    /**
     * 获取销售价
     *
     * @return sell_price - 销售价
     */
    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    /**
     * 设置销售价
     *
     * @param sellPrice 销售价
     */
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取U币抵扣量
     *
     * @return u_price - U币抵扣量
     */
    public Integer getuPrice() {
        return uPrice;
    }

    /**
     * 设置U币抵扣量
     *
     * @param uPrice U币抵扣量
     */
    public void setuPrice(Integer uPrice) {
        this.uPrice = uPrice;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getProdSpeci() {
        return prodSpeci;
    }

    public void setProdSpeci(String prodSpeci) {
        this.prodSpeci = prodSpeci;
    }

    /**
     * 获取是否首页显示，0显示，1不显示，默认不显示
     *
     * @return is_show - 是否首页显示，0显示，1不显示，默认不显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否首页显示，0显示，1不显示，默认不显示
     *
     * @param isShow 是否首页显示，0显示，1不显示，默认不显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
    }

    /**
     * 获取是否删除，0未删除，1已删除，默认0不删除
     *
     * @return del_flag - 是否删除，0未删除，1已删除，默认0不删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除，0未删除，1已删除，默认0不删除
     *
     * @param delFlag 是否删除，0未删除，1已删除，默认0不删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取0 上架 1 下架 2 售罄 3.待上架
     *
     * @return is_sale - 0 上架 1 下架 2 售罄 3.待上架
     */
    public Short getIsSale() {
        return isSale;
    }

    /**
     * 设置0 上架 1 下架 2 售罄 3.待上架
     *
     * @param isSale 0 上架 1 下架 2 售罄 3.待上架
     */
    public void setIsSale(Short isSale) {
        this.isSale = isSale;
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
     * 获取是否自动上架标志，0不自动，1自动，默认0不自动，为1自动上架时间不能为空
     *
     * @return is_auto_sale - 是否自动上架标志，0不自动，1自动，默认0不自动，为1自动上架时间不能为空
     */
    public Short getIsAutoSale() {
        return isAutoSale;
    }

    /**
     * 设置是否自动上架标志，0不自动，1自动，默认0不自动，为1自动上架时间不能为空
     *
     * @param isAutoSale 是否自动上架标志，0不自动，1自动，默认0不自动，为1自动上架时间不能为空
     */
    public void setIsAutoSale(Short isAutoSale) {
        this.isAutoSale = isAutoSale;
    }

    /**
     * 获取是否自动下架，0不自动，1自动，默认0不自动，为1时自动下架时间不能为空
     *
     * @return is_auto_off - 是否自动下架，0不自动，1自动，默认0不自动，为1时自动下架时间不能为空
     */
    public Short getIsAutoOff() {
        return isAutoOff;
    }

    /**
     * 设置是否自动下架，0不自动，1自动，默认0不自动，为1时自动下架时间不能为空
     *
     * @param isAutoOff 是否自动下架，0不自动，1自动，默认0不自动，为1时自动下架时间不能为空
     */
    public void setIsAutoOff(Short isAutoOff) {
        this.isAutoOff = isAutoOff;
    }

    /**
     * 获取自动上架时间，is_auto_sale为1时不能为空
     *
     * @return auto_sale_time - 自动上架时间，is_auto_sale为1时不能为空
     */
    public Date getAutoSaleTime() {
        return autoSaleTime;
    }

    /**
     * 设置自动上架时间，is_auto_sale为1时不能为空
     *
     * @param autoSaleTime 自动上架时间，is_auto_sale为1时不能为空
     */
    public void setAutoSaleTime(Date autoSaleTime) {
        this.autoSaleTime = autoSaleTime;
    }

    /**
     * 获取自动下架时间，is_auto_off为1时不能为空
     *
     * @return auto_off_time - 自动下架时间，is_auto_off为1时不能为空
     */
    public Date getAutoOffTime() {
        return autoOffTime;
    }

    /**
     * 设置自动下架时间，is_auto_off为1时不能为空
     *
     * @param autoOffTime 自动下架时间，is_auto_off为1时不能为空
     */
    public void setAutoOffTime(Date autoOffTime) {
        this.autoOffTime = autoOffTime;
    }

    /**
     * 获取创建者ID
     *
     * @return create_user - 创建者ID
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建者ID
     *
     * @param createUser 创建者ID
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
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
     * 获取更新者ID
     *
     * @return update_user - 更新者ID
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新者ID
     *
     * @param updateUser 更新者ID
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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
}