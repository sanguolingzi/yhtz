package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.PageModel;
import java.math.BigDecimal;
import java.util.Date;

public class OneAreaModel extends PageModel {

    private Integer id;


    private Integer createUser;


    private Integer updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商品标题
     */
    private String pTitle;

    /**
     * 商品副标题
     */
    private String pSubtitle;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 商品销售价(展示)
     */
    private BigDecimal sellPrice;

    /**
     * 邮费
     */
    private BigDecimal freight;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 商品主图
     */
    private String productImg;

    /**
     * 0=显示 1=不显示
     */
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

    /**
     * 商品详情
     */
    private String prodDetails;

    /**
     * 所属店铺
     */
    private Integer shopId;
    /**
     * 所属店铺
     */
    private String prodSpeci;

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
     * 获取商品标题
     *
     * @return p_title - 商品标题
     */
    public String getpTitle() {
        return pTitle;
    }

    /**
     * 设置商品标题
     *
     * @param pTitle 商品标题
     */
    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    /**
     * 获取商品副标题
     *
     * @return p_subtitle - 商品副标题
     */
    public String getpSubtitle() {
        return pSubtitle;
    }

    /**
     * 设置商品副标题
     *
     * @param pSubtitle 商品副标题
     */
    public void setpSubtitle(String pSubtitle) {
        this.pSubtitle = pSubtitle;
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
     * 获取商品销售价(展示)
     *
     * @return sell_price - 商品销售价(展示)
     */
    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    /**
     * 设置商品销售价(展示)
     *
     * @param sellPrice 商品销售价(展示)
     */
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

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
     * 获取商品主图
     *
     * @return product_img - 商品主图
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * 设置商品主图
     *
     * @param productImg 商品主图
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
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
     * 获取商品详情
     *
     * @return prod_details - 商品详情
     */
    public String getProdDetails() {
        return prodDetails;
    }

    /**
     * 设置商品详情
     *
     * @param prodDetails 商品详情
     */
    public void setProdDetails(String prodDetails) {
        this.prodDetails = prodDetails;
    }
}
