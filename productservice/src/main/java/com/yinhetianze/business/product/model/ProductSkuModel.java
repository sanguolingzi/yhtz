package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情中的单品信息
 * 即是一个单品的sku信息
 */
public class ProductSkuModel extends BasicModel
{
    /**
     * 商品的ID
     */
    private Integer prodId;

    /**
     * sku编号
     */
    private String sku;

    /**
     * 商品库存
     */
    private Integer storage;

    /**
     * 销售价格
     */
    private BigDecimal sellPrice;

    /**
     * 供货价(商家提供建议价格)
     */
    private BigDecimal costPrice;

    /**
     * 零售价(商家提供建议价格)
     */
    private BigDecimal memberPrice;

    /**
     * 市场价格
     */
    private BigDecimal marketPrice;

    /**
     * 减免价格
     */
    private BigDecimal discountPrice;

    /**
     * 规格列表
     */
    private List<ProductSpeciModel> speciList;
    /**
     * 商品规格销售价
     */
    private BigDecimal salePrice;

    /**
     * skuCode编号
     */
    private String skuCode;

    /**
     * 会员价
     * @return
     */
    private BigDecimal vipPrice;

    /**
     * U币
     * @return
     */
    private BigDecimal uPrice;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 友旗币
     * @return
     */
    private BigDecimal flagPrice;

    /**
     * 推广赚
     * @return
     */
    private BigDecimal promotionPrice;



    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStorage()
    {
        return storage;
    }

    public void setStorage(Integer storage)
    {
        this.storage = storage;
    }

    public BigDecimal getSellPrice()
    {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice)
    {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getCostPrice()
    {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice)
    {
        this.costPrice = costPrice;
    }

    public BigDecimal getMemberPrice()
    {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice)
    {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMarketPrice()
    {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice)
    {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getDiscountPrice()
    {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice)
    {
        this.discountPrice = discountPrice;
    }

    public List<ProductSpeciModel> getSpeciList()
    {
        return speciList;
    }

    public void setSpeciList(List<ProductSpeciModel> speciList)
    {
        this.speciList = speciList;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public Integer getProdId()
    {
        return prodId;
    }

    public void setProdId(Integer prodId)
    {
        this.prodId = prodId;
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
