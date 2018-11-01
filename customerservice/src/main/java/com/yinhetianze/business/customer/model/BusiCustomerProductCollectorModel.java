package com.yinhetianze.business.customer.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;

public class BusiCustomerProductCollectorModel extends PageModel{
    private Integer id;

    /**
     * 0 商品收藏 1店铺收藏
     */
    private Short cType;

    /**
     * 关联数据id
     */
    private Integer relationId;

    /**
     * 所属用户Id
     */
    private Integer customerId;
    /**
     * 返回数据结果
     */
    private String content;


    private String name;


    private String imgSrc;

    /**
     * 销售价
     */
    private BigDecimal price;

    /**
     * 市场价
     */
    private BigDecimal mPrice;

    /**
     * 会员价
     */
    private BigDecimal vipPrice;
    /**
     * 商品上架下架状态
     */
    private short pStatus;

    /**
     * 商品分类Id
     */
    private short pCateId;
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
     * 获取0 商品收藏 1店铺收藏
     *
     * @return c_type - 0 商品收藏 1店铺收藏
     */
    public Short getcType() {
        return cType;
    }

    /**
     * 设置0 商品收藏 1店铺收藏
     *
     * @param cType 0 商品收藏 1店铺收藏
     */
    public void setcType(Short cType) {
        this.cType = cType;
    }

    /**
     * 获取关联数据id
     *
     * @return relation_id - 关联数据id
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * 设置关联数据id
     *
     * @param relationId 关联数据id
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getmPrice() {
        return mPrice;
    }

    public void setmPrice(BigDecimal mPrice) {
        this.mPrice = mPrice;
    }

    public short getpStatus() {
        return pStatus;
    }

    public void setpStatus(short pStatus) {
        this.pStatus = pStatus;
    }

    public short getpCateId() {
        return pCateId;
    }

    public void setpCateId(short pCateId) {
        this.pCateId = pCateId;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }
}