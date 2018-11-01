package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetailInfoModel extends BasicModel {
    /**
     * 商品基础信息ID
     */
    private Integer productId;
    /**
     * 商品单品配置信息
     * 用来添加商品单品的集合
     * 集合中的每一个元素都是一个商品单品
     */
    private List<ProductSkuModel> skuList;
    /**
     * 商品销售价
     */
    private BigDecimal sellPrice;
    /**
     * 分享优惠价
     * @return
     */
    private  BigDecimal sharePrice;
    /**
     * 0 上架 1 下架 2 售罄
     */
    private Short pStatus;

    /**
     * 推广优惠价
     * @return
     */
    private BigDecimal promotionPrice;
    /**
     * 审核状态
     */
    private Short auditState;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<ProductSkuModel> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ProductSkuModel> skuList) {
        this.skuList = skuList;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(BigDecimal sharePrice) {
        this.sharePrice = sharePrice;
    }

    public Short getpStatus() {
        return pStatus;
    }

    public void setpStatus(Short pStatus) {
        this.pStatus = pStatus;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Short getAuditState() {
        return auditState;
    }

    public void setAuditState(Short auditState) {
        this.auditState = auditState;
    }
}
