package com.yinhetianze.pojo.product;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_extra")
public class ProductExtraPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品ID
     */
    @Column(name = "prod_id")
    private Integer prodId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 商品参数集json串
     */
    @Column(name = "prod_param")
    private String prodParam;

    /**
     * 商品图文详情描述/介绍
     */
    @Column(name = "prod_introduction")
    private String prodIntroduction;

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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取商品参数集json串
     *
     * @return prod_param - 商品参数集json串
     */
    public String getProdParam() {
        return prodParam;
    }

    /**
     * 设置商品参数集json串
     *
     * @param prodParam 商品参数集json串
     */
    public void setProdParam(String prodParam) {
        this.prodParam = prodParam;
    }

    /**
     * 获取商品图文详情描述/介绍
     *
     * @return prod_introduction - 商品图文详情描述/介绍
     */
    public String getProdIntroduction() {
        return prodIntroduction;
    }

    /**
     * 设置商品图文详情描述/介绍
     *
     * @param prodIntroduction 商品图文详情描述/介绍
     */
    public void setProdIntroduction(String prodIntroduction) {
        this.prodIntroduction = prodIntroduction;
    }
}