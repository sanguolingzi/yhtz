package com.yinhetianze.pojo.product;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_detail")
public class ProductDetailPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 商品ID
     */
    @Column(name = "prod_id")
    private Integer prodId;

    /**
     * 供货价(商家提供建议价格)
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 销售价
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 零售价(商家提供建议价格)
     */
    @Column(name = "member_price")
    private BigDecimal memberPrice;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商品规格json
     */
    @Column(name = "prod_speci")
    private String prodSpeci;

    /**
     * 商品SKU编码
     */
    @Column(name = "sku_code")
    private String skuCode;

    /**
     * 商品库存
     */
    @Column(name = "p_storage")
    private Short pStorage;

    /**
     * 0 上架 1 下商品状态架 2 售罄
     */
    @Column(name = "p_status")
    private Short pStatus;

    @Column(name = "create_user")
    private Integer createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private Integer updateUser;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 商品参数
     */
    @Column(name = "prod_param")
    private String prodParam;

    /**
     * 商品图文详情
     */
    @Column(name = "prod_details")
    private String prodDetails;


    /**
     * 会员价
     */
    @Column(name = "vip_price")
    private BigDecimal vipPrice;

    /**
     * U币
     */
    @Column(name = "u_price")
    private BigDecimal uPrice;

    /**
     * 数量
     */
    @Column(name = "number")
    private Integer number;

    /**
     * 友旗币
     * @return
     */
    @Column(name = "flag_price")
    private BigDecimal flagPrice;

    /**
     * 推广赚
     * @return
     */
    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

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
     * 获取商品ID
     *
     * @return prod_id - 商品ID
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 设置商品ID
     *
     * @param prodId 商品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取成本价
     *
     * @return cost_price - 成本价
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本价
     *
     * @param costPrice 成本价
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取销售价
     *
     * @return sale_price - 销售价
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 设置销售价
     *
     * @param salePrice 销售价
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 获取会员价
     *
     * @return member_price - 会员价
     */
    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    /**
     * 设置会员价
     *
     * @param memberPrice 会员价
     */
    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
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
     * 获取商品规格json
     *
     * @return prod_speci - 商品规格json
     */
    public String getProdSpeci() {
        return prodSpeci;
    }

    /**
     * 设置商品规格json
     *
     * @param prodSpeci 商品规格json
     */
    public void setProdSpeci(String prodSpeci) {
        this.prodSpeci = prodSpeci;
    }

    /**
     * 获取商品SKU编码
     *
     * @return sku_code - 商品SKU编码
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * 设置商品SKU编码
     *
     * @param skuCode 商品SKU编码
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    /**
     * 获取商品库存
     *
     * @return p_storage - 商品库存
     */
    public Short getpStorage() {
        return pStorage;
    }

    /**
     * 设置商品库存
     *
     * @param pStorage 商品库存
     */
    public void setpStorage(Short pStorage) {
        this.pStorage = pStorage;
    }

    /**
     * 获取0 上架 1 下商品状态架 2 售罄
     *
     * @return p_status - 0 上架 1 下商品状态架 2 售罄
     */
    public Short getpStatus() {
        return pStatus;
    }

    /**
     * 设置0 上架 1 下商品状态架 2 售罄
     *
     * @param pStatus 0 上架 1 下商品状态架 2 售罄
     */
    public void setpStatus(Short pStatus) {
        this.pStatus = pStatus;
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
     * 获取商品参数
     *
     * @return prod_param - 商品参数
     */
    public String getProdParam() {
        return prodParam;
    }

    /**
     * 设置商品参数
     *
     * @param prodParam 商品参数
     */
    public void setProdParam(String prodParam) {
        this.prodParam = prodParam;
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

    public BigDecimal getVipPrice(){
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice){
        this.vipPrice = vipPrice;
    }

    public BigDecimal getuPrice(){
        return uPrice;
    }

    public void setuPrice(BigDecimal uPrice){
        this.uPrice = uPrice;
    }

    public Integer getNumber(){
        return number;
    }

    public void setNumber(Integer number){
        this.number = number;
    }

    public BigDecimal getFlagPrice(){
        return flagPrice;
    }

    public void setFlagPrice(BigDecimal flagPrice){
        this.flagPrice = flagPrice;
    }

    public BigDecimal getPromotionPrice(){
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice){
        this.promotionPrice = promotionPrice;
    }
}