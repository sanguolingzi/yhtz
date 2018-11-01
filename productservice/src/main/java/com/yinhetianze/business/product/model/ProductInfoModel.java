package com.yinhetianze.business.product.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.List;

/**
 * 商品的单品model，包含相关单品的sku信息
 */
public class ProductInfoModel extends BasicModel
{
    /**
     * 商品基础信息ID
     */
    private Integer productId;

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

    public Integer getProductId()
    {
        return productId;
    }

    public void setProductId(Integer productId)
    {
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
}
