package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.List;

public class ProductPageModel extends PageModel {
    private Integer id;

    /**
     * 商品基础信息ID
     */
    private Integer productId;
    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 上架时间
     */
    private Short shelfTime;

    /**
     * 商品品牌ID
     */
    private Integer prodBrandId;
    /**
     * 价格高低
     */
    private Short sellPrice;
    /**
     * 最低价
     */
    private BigDecimal minsellPrice;
    /**
     * 最高价
     */
    private BigDecimal maxsellPrice;
    /**
     * 地省级ID
     */
    private String areaCode;

    /**
     * 商品分类id，必须是3级分类
     */
    private Integer prodCateId;

    /**
     * 单个商品的skuCode
     * 用来查询单个商品详情
     */
    private String skuCode;

    /**
     * 商品单品配置信息
     * 用来添加商品单品的集合
     * 集合中的每一个元素都是一个商品单品
     */
    private List<ProductSkuModel> skuList;

    /**
     * 单个商品sku
     */
    private ProductSkuModel sku;

    /**
     * 操作员ID
     */
    private Integer createUser;

    /**
     * 店铺分类ID
     */
    private Integer shopCategoryId;

    /**
     * 销量高低
     */
    private Short salesVolume;

    public Short getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Short salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Integer getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Integer shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<ProductSkuModel> getSkuList()
    {
        return skuList;
    }

    public void setSkuList(List<ProductSkuModel> skuList)
    {
        this.skuList = skuList;
    }

    public Integer getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(Integer createUser)
    {
        this.createUser = createUser;
    }

    public String getSkuCode()
    {
        return skuCode;
    }

    public void setSkuCode(String skuCode)
    {
        this.skuCode = skuCode;
    }

    public ProductSkuModel getSku(){
        return sku;
    }

    public void setSku(ProductSkuModel sku){
        this.sku = sku;
    }
    /**
     *
     */
    private Integer shopId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Short getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Short shelfTime) {
        this.shelfTime = shelfTime;
    }

    public Short getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Short sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getMinsellPrice() {
        return minsellPrice;
    }

    public void setMinsellPrice(BigDecimal minsellPrice) {
        this.minsellPrice = minsellPrice;
    }

    public BigDecimal getMaxsellPrice() {
        return maxsellPrice;
    }

    public void setMaxsellPrice(BigDecimal maxsellPrice) {
        this.maxsellPrice = maxsellPrice;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getProdCateId() {
        return prodCateId;
    }

    public void setProdCateId(Integer prodCateId) {
        this.prodCateId = prodCateId;
    }

    public Integer getProdBrandId() {
        return prodBrandId;
    }

    public void setProdBrandId(Integer prodBrandId) {
        this.prodBrandId = prodBrandId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
}
