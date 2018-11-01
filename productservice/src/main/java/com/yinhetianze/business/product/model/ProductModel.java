package com.yinhetianze.business.product.model;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.core.business.httprequest.PageModel;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品模块model
 */
public class ProductModel extends PageModel{
    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 商品标题
     */
    private String prodTitle;

    /**
     * 商品副标题
     */
    private String prodSubTitle;

    /**
     * 商品分类id，必须是3级分类
     */
    private Integer prodCateId;

    /**
     * 商品分类名称
     */
    private String prodCateName;

    /**
     * 商品品牌ID
     */
    private Integer prodBrandId;

    /**
     * 商品品牌名称
     */
    private String brandName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 销售价
     */
    private BigDecimal sellPrice;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 满多少金额免运费
     */
    private BigDecimal freightFreePrice;

    /**
     * 商品编码
     */
    private String prodCode;

    /**
     * 让利比例，百分比
     */
    private BigDecimal profitsProportion;

    /**
     * 赠送积分比例
     */
    private BigDecimal integralPercent;

    /**
     * 是否免运费
     */
    private Short isFreightFree;

    /**
     * 是否可退换货
     */
    private Short isReplacement;

    /**
     * 库存数量
     */
    private Integer prodStorage;

    /**
     * 商品主图
     */
    private String prodImg;

    /**
     * 店铺ID
     */
    private Integer shopId;

    /**
     * 缩略图集合
     */
    private String[] thumbnail;

    /**
     * 商品计量单位ID
     */
    private Integer prodUnitId;

    /**
     * 操作员ID
     */
    private Integer operId;

    /**
     * 详情信息
     * 图文字符串
     */
    private String prodDetails;

    /**
     * 审核状态
     */
    private Short auditState;

    /**
     * 添加商品的操作员ID
     */
    private Integer createUser;

    /**
     * 发货地址
     */
    private String shipAddress;

    /**
     * 发货城市
     */
    private Integer shipCity;

    /**
     * 发货省份
     */
    private Integer shipProvince;

    /**
     * 删除标志
     */
    private Short delFlag;

    /**
     * 商品状态
     */
    private Short status;

    /**
     * 价格区间：开始价格
     */
    private BigDecimal lowSellPrice;

    /**
     * 价格区间：结束价格
     */
    private BigDecimal highSellPrice;

    /**
     * 商品描述详情
     * 富文本图文介绍
     */
    private String prodIntroduction;
    /**
     * 商品参数列表
     */
//    private List<ProductParamModel> paramList;
    /**
     * 商品所属楼层id
     */
    private String floorId;

    private List<ProductParameterModel> paramList;
    /**
     * 0 上架 1 下架 2 售罄
     */
    private Short pStatus;

    /**
     * phone端搜索排序
     * @return
     */
    private String sort;

    /**
     * 价格区间（小）
     * @return
     */
    private BigDecimal smallPrice;

    /**
     * 价格区间（大）
     * @return
     */
    private BigDecimal bigPrice;

    /**
     * 加载条数
     * @return
     */
    private int productNum;

    /**
     * 店内分类Id
     * @return
     */
    private  Integer shopCateId;

    /**
     * 店内已有品牌Id
     * @return
     */
    private Integer shopBrandId;

    /**
     * 结算价格 （结算价格与让利比例互斥 有结算价格就不会有让利比例）
     * @return
     */
    private BigDecimal settlementPrice;

    /**
     * 电子发票
     * @return
     */
    private Short electronicInvoice;

    /**
     * 商品类型(商品类型 0.普通商品 1.虚拟商品)
     * @return
     */
    private Short productDistinction;

    /**
     * 商品种类（商品种类 0.自营商品 1.店铺内添加商品）
     * @return
     */
    private  Short productType;

    /**
     *商品上架时间
     */

    private Long upTime;

    /**
     *商品下架时间
     */

    private Long downTime;

    /**
     * 结算类型 1.佣金结算 2.差价结算
     * @return
     */
    private Integer settlement;

    /**
     * 商品数组 productId[]
     * @return
     */
    private int[] productArr;

    /**
     * 商品详情描述
     * @return
     */
    private  String remark;

    /**
     * 价格升序降序 0.升序 1.降序
     * @return
     */
    private String priceSort;

    /**
     * 供货价
     * @return
     */
    private BigDecimal supplyPrice;

    /**
     * 零售价
     * @return
     */
    private BigDecimal retailPrice;

    /**
     * 分享优惠价
     * @return
     */
    private  BigDecimal sharePrice;

    /**
     * 推广优惠价
     * @return
     */
    private BigDecimal promotionPrice;

    /**
     * 会员价
     * @return
     */
    private BigDecimal vipPrice;

    /**
     * 发货状态 0.自营发货 1.代发货
     * @return
     */
    private Short dropShipping;

    /**
     * 代发货店铺id
     * @return
     */
    private Integer dropShippingId;

    /**
     *赠送友旗币
     * @return
     */
    private BigDecimal flagPrice;


    public Integer getProductId(){
        return productId;
    }

    public void setProductId(Integer productId){
        this.productId = productId;
    }

    public String getProdName(){
        return prodName;
    }

    public void setProdName(String prodName){
        this.prodName = prodName;
    }

    public String getProdTitle(){
        return prodTitle;
    }

    public void setProdTitle(String prodTitle){
        this.prodTitle = prodTitle;
    }

    public String getProdSubTitle(){
        return prodSubTitle;
    }

    public void setProdSubTitle(String prodSubTitle){
        this.prodSubTitle = prodSubTitle;
    }

    public Integer getProdCateId(){
        return prodCateId;
    }

    public void setProdCateId(Integer prodCateId){
        this.prodCateId = prodCateId;
    }

    public String getProdCateName(){
        return prodCateName;
    }

    public void setProdCateName(String prodCateName){
        this.prodCateName = prodCateName;
    }

    public Integer getProdBrandId(){
        return prodBrandId;
    }

    public void setProdBrandId(Integer prodBrandId){
        this.prodBrandId = prodBrandId;
    }

    public String getBrandName(){
        return brandName;
    }

    public void setBrandName(String brandName){
        this.brandName = brandName;
    }

    public BigDecimal getMarketPrice(){
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice){
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSellPrice(){
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice){
        this.sellPrice = sellPrice;
    }

    public BigDecimal getFreight(){
        return freight;
    }

    public void setFreight(BigDecimal freight){
        this.freight = freight;
    }

    public BigDecimal getFreightFreePrice(){
        return freightFreePrice;
    }

    public void setFreightFreePrice(BigDecimal freightFreePrice){
        this.freightFreePrice = freightFreePrice;
    }

    public String getProdCode(){
        return prodCode;
    }

    public void setProdCode(String prodCode){
        this.prodCode = prodCode;
    }

    public BigDecimal getProfitsProportion(){
        return profitsProportion;
    }

    public void setProfitsProportion(BigDecimal profitsProportion){
        this.profitsProportion = profitsProportion;
    }

    public BigDecimal getIntegralPercent(){
        return integralPercent;
    }

    public void setIntegralPercent(BigDecimal integralPercent){
        this.integralPercent = integralPercent;
    }

    public Short getIsFreightFree(){
        return isFreightFree;
    }

    public void setIsFreightFree(Short isFreightFree){
        this.isFreightFree = isFreightFree;
    }

    public Short getIsReplacement(){
        return isReplacement;
    }

    public void setIsReplacement(Short isReplacement){
        this.isReplacement = isReplacement;
    }

    public Integer getProdStorage(){
        return prodStorage;
    }

    public void setProdStorage(Integer prodStorage){
        this.prodStorage = prodStorage;
    }

    public String getProdImg(){
        return prodImg;
    }

    public void setProdImg(String prodImg){
        this.prodImg = prodImg;
    }

    public Integer getShopId(){
        return shopId;
    }

    public void setShopId(Integer shopId){
        this.shopId = shopId;
    }

    public String[] getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String[] thumbnail){
        this.thumbnail = thumbnail;
    }

    public Integer getProdUnitId(){
        return prodUnitId;
    }

    public void setProdUnitId(Integer prodUnitId){
        this.prodUnitId = prodUnitId;
    }

    public Integer getOperId(){
        return operId;
    }

    public void setOperId(Integer operId){
        this.operId = operId;
    }

    public String getProdDetails(){
        return prodDetails;
    }

    public void setProdDetails(String prodDetails){
        this.prodDetails = prodDetails;
    }

    public Short getAuditState(){
        return auditState;
    }

    public void setAuditState(Short auditState){
        this.auditState = auditState;
    }

    public Integer getCreateUser(){
        return createUser;
    }

    public void setCreateUser(Integer createUser){
        this.createUser = createUser;
    }

    public String getShipAddress(){
        return shipAddress;
    }

    public void setShipAddress(String shipAddress){
        this.shipAddress = shipAddress;
    }

    public Integer getShipCity(){
        return shipCity;
    }

    public void setShipCity(Integer shipCity){
        this.shipCity = shipCity;
    }

    public Integer getShipProvince(){
        return shipProvince;
    }

    public void setShipProvince(Integer shipProvince){
        this.shipProvince = shipProvince;
    }

    public Short getDelFlag(){
        return delFlag;
    }

    public void setDelFlag(Short delFlag){
        this.delFlag = delFlag;
    }

    public Short getStatus(){
        return status;
    }

    public void setStatus(Short status){
        this.status = status;
    }

    public BigDecimal getLowSellPrice(){
        return lowSellPrice;
    }

    public void setLowSellPrice(BigDecimal lowSellPrice){
        this.lowSellPrice = lowSellPrice;
    }

    public BigDecimal getHighSellPrice(){
        return highSellPrice;
    }

    public void setHighSellPrice(BigDecimal highSellPrice){
        this.highSellPrice = highSellPrice;
    }

    public String getProdIntroduction(){
        return prodIntroduction;
    }

    public void setProdIntroduction(String prodIntroduction){
        this.prodIntroduction = prodIntroduction;
    }

    public List<ProductParameterModel> getParamList(){
        return paramList;
    }

    public void setParamList(List<ProductParameterModel> paramList){
        this.paramList = paramList;
    }

    public String getFloorId(){
        return floorId;
    }

    public void setFloorId(String floorId){
        this.floorId = floorId;
    }

    public Short getpStatus(){
        return pStatus;
    }

    public void setpStatus(Short pStatus){
        this.pStatus = pStatus;
    }

    public String getSort(){
        return sort;
    }

    public void setSort(String sort){
        this.sort = sort;
    }

    public BigDecimal getSmallPrice(){
        return smallPrice;
    }

    public void setSmallPrice(BigDecimal smallPrice){
        this.smallPrice = smallPrice;
    }

    public BigDecimal getBigPrice(){
        return bigPrice;
    }

    public void setBigPrice(BigDecimal bigPrice){
        this.bigPrice = bigPrice;
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

    public Long getUpTime(){
        return upTime;
    }

    public void setUpTime(Long upTime){
        this.upTime = upTime;
    }

    public Long getDownTime(){
        return downTime;
    }

    public void setDownTime(Long downTime){
        this.downTime = downTime;
    }

    public int[] getProductArr(){
        return productArr;
    }

    public void setProductArr(int[] productArr){
        this.productArr = productArr;
    }

    public int getProductNum(){
        return productNum;
    }

    public void setProductNum(int productNum){
        this.productNum = productNum;
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

    public Integer getSettlement(){
        return settlement;
    }

    public void setSettlement(Integer settlement){
        this.settlement = settlement;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getPriceSort(){
        return priceSort;
    }

    public void setPriceSort(String priceSort){
        this.priceSort = priceSort;
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
