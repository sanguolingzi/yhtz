package com.yinhetianze.pojo.product;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product")
public class ProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 商品标题
     */
    @Column(name = "p_title")
    private String pTitle;

    /**
     * 商品副标题
     */
    @Column(name = "p_subtitle")
    private String pSubtitle;

    /**
     * 商品名称
     */
    @Column(name = "prod_name")
    private String prodName;

    /**
     * 商品分类名称
     */
    @Column(name = "prod_cate_name")
    private String prodCateName;

    /**
     * 商品分类ID
     */
    @Column(name = "prod_cate_id")
    private Integer prodCateId;

    /**
     * 商品品牌ID
     */
    @Column(name = "prod_brand_id")
    private Integer prodBrandId;

    /**
     * 品牌名称
     */
    @Column(name = "prod_brand_name")
    private String prodBrandName;

    /**
     * 商品编码
     */
    @Column(name = "prod_code")
    private String prodCode;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商品销售价(展示)
     */
    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    /**
     * 商品让利比例
     */
    @Column(name = "profits_proportion")
    private BigDecimal profitsProportion;

    /**
     * 赠送积分比例
     */
    @Column(name = "integral_percent")
    private BigDecimal integralPercent;

    /**
     * 商品计量单位ID
     */
    @Column(name = "prod_unit_id")
    private Integer prodUnitId;

    /**
     * 商品单位
     */
    @Column(name = "prod_unit_name")
    private String prodUnitName;

    /**
     * 是否免运费，1=免运费，0=不免，默认1免运费
     */
    @Column(name = "is_freight_free")
    private Short isFreightFree;

    /**
     * 满XX免运费
     */
    @Column(name = "freight_free_price")
    private BigDecimal freightFreePrice;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 是否支持退换货，1=支持，0=不支持，默认1支持
     */
    @Column(name = "is_replacement")
    private Short isReplacement;

    /**
     * 总库存
     */
    @Column(name = "p_storage")
    private Integer pStorage;

    /**
     * 审核状态：0=待审核，1=审核中，2=审核未通过，3=审核通过
     */
    @Column(name = "audit_state")
    private Short auditState;

    /**
     * 0 上架 1 下架 2 售罄
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
     * 商品主图
     */
    @Column(name = "product_img")
    private String productImg;

    /**
     * 商品二维码图片地址
     */
    private String qrcode;

    /**
     * 所属店铺
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 商品主图
     */
    @Column(name = "ship_address")
    private String shipAddress;

    /**
     * 商品二维码图片地址
     */
    @Column(name = "ship_city")
    private Integer shipCity;

    /**
     * 商品生成单品sku时的计算值
     */
    @Column(name = "salt")
    private Integer salt;

    /**
     * 所属店铺
     */
    @Column(name = "ship_province")
    private Integer shipProvince;

    /**
     * 店内分类Id
     */
    @Column(name = "shop_cate_id")
    private Integer shopCateId;

    /**
     * 店内已有品牌Id
     */
    @Column(name = "shop_brand_id")
    private Integer shopBrandId;

    /**
     * 结算价格
     */
    @Column(name = "settlement_price")
    private BigDecimal settlementPrice;
    /**
     * 电子发票0.不支持 1.支持
     */
    @Column(name = "electronic_invoice")
    private Short electronicInvoice;

    /**
     * 商品类型(商品类型 0.普通商品 1.虚拟商品)
     * @return
     */
    @Column(name = "product_distinction")
    private Short productDistinction;

    /**
     * 商品种类（商品种类 0.自营商品 1.店铺内添加商品）
     * @return
     */
    @Column(name = "product_type")
    private  Short productType;

    /**
     *商品上架时间
     */
    @Column(name = "up_time")
    private Date upTime;

    /**
     *商品下架时间
     */
    @Column(name = "down_time")
    private Date downTime;

    /**
     * 商品详情描述
     * @return
     */
    @Column(name = "remark")
    private  String remark;

    /**
     * 供货价
     * @return
     */
    @Column(name = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 零售价
     * @return
     */
    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    /**
     * 分享优惠价
     * @return
     */
    @Column(name = "share_price")
    private  BigDecimal sharePrice;

    /**
     * 推广优惠价
     * @return
     */
    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

    /**
     * 会员价
     */
    @Column(name = "vip_price")
    private BigDecimal vipPrice;

    /**
     * 发货状态 0.自营发货 1.代发货
     * @return
     */
    @Column(name = "drop_shipping")
    private Short dropShipping;

    /**
     * 代发货店铺id
     * @return
     */
    @Column(name = "drop_shipping_id")
    private Integer dropShippingId;

    /**
     * 会员价
     */
    @Column(name = "flag_price")
    private BigDecimal flagPrice;

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
     * 获取商品分类名称
     *
     * @return prod_cate_name - 商品分类名称
     */
    public String getProdCateName() {
        return prodCateName;
    }

    /**
     * 设置商品分类名称
     *
     * @param prodCateName 商品分类名称
     */
    public void setProdCateName(String prodCateName) {
        this.prodCateName = prodCateName;
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
     * 获取商品品牌ID
     *
     * @return prod_brand_id - 商品品牌ID
     */
    public Integer getProdBrandId() {
        return prodBrandId;
    }

    /**
     * 设置商品品牌ID
     *
     * @param prodBrandId 商品品牌ID
     */
    public void setProdBrandId(Integer prodBrandId) {
        this.prodBrandId = prodBrandId;
    }

    /**
     * 获取品牌名称
     *
     * @return prod_brand_name - 品牌名称
     */
    public String getProdBrandName() {
        return prodBrandName;
    }

    /**
     * 设置品牌名称
     *
     * @param prodBrandName 品牌名称
     */
    public void setProdBrandName(String prodBrandName) {
        this.prodBrandName = prodBrandName;
    }

    /**
     * 获取商品编码
     *
     * @return prod_code - 商品编码
     */
    public String getProdCode() {
        return prodCode;
    }

    /**
     * 设置商品编码
     *
     * @param prodCode 商品编码
     */
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
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

    /**
     * 获取商品让利比例
     *
     * @return profits_proportion - 商品让利比例
     */
    public BigDecimal getProfitsProportion() {
        return profitsProportion;
    }

    /**
     * 设置商品让利比例
     *
     * @param profitsProportion 商品让利比例
     */
    public void setProfitsProportion(BigDecimal profitsProportion) {
        this.profitsProportion = profitsProportion;
    }

    /**
     * 获取赠送积分比例
     *
     * @return integral_percent - 赠送积分比例
     */
    public BigDecimal getIntegralPercent() {
        return integralPercent;
    }

    /**
     * 设置赠送积分比例
     *
     * @param integralPercent 赠送积分比例
     */
    public void setIntegralPercent(BigDecimal integralPercent) {
        this.integralPercent = integralPercent;
    }

    /**
     * 获取商品计量单位ID
     *
     * @return prod_unit_id - 商品计量单位ID
     */
    public Integer getProdUnitId() {
        return prodUnitId;
    }

    /**
     * 设置商品计量单位ID
     *
     * @param prodUnitId 商品计量单位ID
     */
    public void setProdUnitId(Integer prodUnitId) {
        this.prodUnitId = prodUnitId;
    }

    /**
     * 获取商品单位
     *
     * @return prod_unit_name - 商品单位
     */
    public String getProdUnitName() {
        return prodUnitName;
    }

    /**
     * 设置商品单位
     *
     * @param prodUnitName 商品单位
     */
    public void setProdUnitName(String prodUnitName) {
        this.prodUnitName = prodUnitName;
    }

    /**
     * 获取是否免运费，1=免运费，0=不免，默认1免运费
     *
     * @return is_freight_free - 是否免运费，1=免运费，0=不免，默认1免运费
     */
    public Short getIsFreightFree() {
        return isFreightFree;
    }

    /**
     * 设置是否免运费，1=免运费，0=不免，默认1免运费
     *
     * @param isFreightFree 是否免运费，1=免运费，0=不免，默认1免运费
     */
    public void setIsFreightFree(Short isFreightFree) {
        this.isFreightFree = isFreightFree;
    }

    /**
     * 获取满XX免运费
     *
     * @return freight_free_price - 满XX免运费
     */
    public BigDecimal getFreightFreePrice() {
        return freightFreePrice;
    }

    /**
     * 设置满XX免运费
     *
     * @param freightFreePrice 满XX免运费
     */
    public void setFreightFreePrice(BigDecimal freightFreePrice) {
        this.freightFreePrice = freightFreePrice;
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
     * 获取是否支持退换货，1=支持，0=不支持，默认1支持
     *
     * @return is_replacement - 是否支持退换货，1=支持，0=不支持，默认1支持
     */
    public Short getIsReplacement() {
        return isReplacement;
    }

    /**
     * 设置是否支持退换货，1=支持，0=不支持，默认1支持
     *
     * @param isReplacement 是否支持退换货，1=支持，0=不支持，默认1支持
     */
    public void setIsReplacement(Short isReplacement) {
        this.isReplacement = isReplacement;
    }

    /**
     * 获取总库存
     *
     * @return p_storage - 总库存
     */
    public Integer getpStorage() {
        return pStorage;
    }

    /**
     * 设置总库存
     *
     * @param pStorage 总库存
     */
    public void setpStorage(Integer pStorage) {
        this.pStorage = pStorage;
    }

    /**
     * 获取审核状态：0=待审核，1=审核中，2=审核未通过，3=审核通过
     *
     * @return audit_state - 审核状态：0=待审核，1=审核中，2=审核未通过，3=审核通过
     */
    public Short getAuditState() {
        return auditState;
    }

    /**
     * 设置审核状态：0=待审核，1=审核中，2=审核未通过，3=审核通过
     *
     * @param auditState 审核状态：0=待审核，1=审核中，2=审核未通过，3=审核通过
     */
    public void setAuditState(Short auditState) {
        this.auditState = auditState;
    }

    /**
     * 获取0 上架 1 下架 2 售罄
     *
     * @return p_status - 0 上架 1 下架 2 售罄
     */
    public Short getpStatus() {
        return pStatus;
    }

    /**
     * 设置0 上架 1 下架 2 售罄
     *
     * @param pStatus 0 上架 1 下架 2 售罄
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
     * 获取商品二维码图片地址
     *
     * @return qrcode - 商品二维码图片地址
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置商品二维码图片地址
     *
     * @param qrcode 商品二维码图片地址
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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

    public String getShipAddress()
    {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress)
    {
        this.shipAddress = shipAddress;
    }

    public Integer getShipCity()
    {
        return shipCity;
    }

    public void setShipCity(Integer shipCity)
    {
        this.shipCity = shipCity;
    }

    public Integer getShipProvince()
    {
        return shipProvince;
    }

    public void setShipProvince(Integer shipProvince)
    {
        this.shipProvince = shipProvince;
    }

    public Integer getSalt()
    {
        return salt;
    }

    public void setSalt(Integer salt)
    {
        this.salt = salt;
    }

    public Integer getShopCateId(){
        return shopCateId;
    }

    public void setShopCateId(Integer shopCateId){
        this.shopCateId = shopCateId;
    }

    public Integer getShopBrandId(){
        return shopBrandId;
    }

    public void setShopBrandId(Integer shopBrandId){
        this.shopBrandId = shopBrandId;
    }

    public BigDecimal getSettlementPrice(){
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice){
        this.settlementPrice = settlementPrice;
    }

    public Short getElectronicInvoice(){
        return electronicInvoice;
    }

    public void setElectronicInvoice(Short electronicInvoice){
        this.electronicInvoice = electronicInvoice;
    }

    public Short getProductDistinction(){
        return productDistinction;
    }

    public void setProductDistinction(Short productDistinction){
        this.productDistinction = productDistinction;
    }

    public Short getProductType(){
        return productType;
    }

    public void setProductType(Short productType){
        this.productType = productType;
    }

    public Date getUpTime(){
        return upTime;
    }

    public void setUpTime(Date upTime){
        this.upTime = upTime;
    }

    public Date getDownTime(){
        return downTime;
    }

    public void setDownTime(Date downTime){
        this.downTime = downTime;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public BigDecimal getSupplyPrice(){
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice){
        this.supplyPrice = supplyPrice;
    }

    public BigDecimal getRetailPrice(){
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice){
        this.retailPrice = retailPrice;
    }

    public BigDecimal getSharePrice(){
        return sharePrice;
    }

    public void setSharePrice(BigDecimal sharePrice){
        this.sharePrice = sharePrice;
    }

    public BigDecimal getPromotionPrice(){
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice){
        this.promotionPrice = promotionPrice;
    }

    public BigDecimal getVipPrice(){
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice){
        this.vipPrice = vipPrice;
    }

    public Short getDropShipping(){
        return dropShipping;
    }

    public void setDropShipping(Short dropShipping){
        this.dropShipping = dropShipping;
    }

    public Integer getDropShippingId(){
        return dropShippingId;
    }

    public void setDropShippingId(Integer dropShippingId){
        this.dropShippingId = dropShippingId;
    }

    public BigDecimal getFlagPrice(){
        return flagPrice;
    }

    public void setFlagPrice(BigDecimal flagPrice){
        this.flagPrice = flagPrice;
    }
}