package com.yinhetianze.pojo.product;

import javax.persistence.*;

@Table(name = "busi_product_sales")
public class ProductSalesPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 初始化销量
     */
    @Column(name = "init_sales")
    private Integer initSales;

    /**
     * 增长销量
     */
    @Column(name = "increase_sales")
    private Integer increaseSales;

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
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取初始化销量
     *
     * @return init_sales - 初始化销量
     */
    public Integer getInitSales() {
        return initSales;
    }

    /**
     * 设置初始化销量
     *
     * @param initSales 初始化销量
     */
    public void setInitSales(Integer initSales) {
        this.initSales = initSales;
    }

    /**
     * 获取增长销量
     *
     * @return increase_sales - 增长销量
     */
    public Integer getIncreaseSales() {
        return increaseSales;
    }

    /**
     * 设置增长销量
     *
     * @param increaseSales 增长销量
     */
    public void setIncreaseSales(Integer increaseSales) {
        this.increaseSales = increaseSales;
    }
}