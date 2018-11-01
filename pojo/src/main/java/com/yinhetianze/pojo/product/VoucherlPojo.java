package com.yinhetianze.pojo.product;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_voucher")
public class VoucherlPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 兑换券类型，字典：VOUCHER_TYPE
     */
    private Short type;

    /**
     * 兑换券名称
     */
    private String name;

    /**
     * 兑换券拥有者
     */
    @Column(name = "cust_id")
    private Integer custId;

    /**
     * 兑换券绑定兑换商品ID
     */
    @Column(name = "prod_id")
    private Integer prodId;

    /**
     * 兑换券跳转链接
     */
    private String link;

    /**
     * 券状态：字典：VOUCHER_STATUS
     */
    private Short status;

    /**
     * 是否删除，0未删除，已删除，默认0未删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取兑换券类型，字典：VOUCHER_TYPE
     *
     * @return type - 兑换券类型，字典：VOUCHER_TYPE
     */
    public Short getType() {
        return type;
    }

    /**
     * 设置兑换券类型，字典：VOUCHER_TYPE
     *
     * @param type 兑换券类型，字典：VOUCHER_TYPE
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * 获取兑换券名称
     *
     * @return name - 兑换券名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置兑换券名称
     *
     * @param name 兑换券名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取兑换券拥有者
     *
     * @return cust_id - 兑换券拥有者
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * 设置兑换券拥有者
     *
     * @param custId 兑换券拥有者
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * 获取兑换券绑定兑换商品ID
     *
     * @return prod_id - 兑换券绑定兑换商品ID
     */
    public Integer getProdId() {
        return prodId;
    }

    /**
     * 设置兑换券绑定兑换商品ID
     *
     * @param prodId 兑换券绑定兑换商品ID
     */
    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    /**
     * 获取兑换券跳转链接
     *
     * @return link - 兑换券跳转链接
     */
    public String getLink() {
        return link;
    }

    /**
     * 设置兑换券跳转链接
     *
     * @param link 兑换券跳转链接
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 获取券状态：字典：VOUCHER_STATUS
     *
     * @return status - 券状态：字典：VOUCHER_STATUS
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置券状态：字典：VOUCHER_STATUS
     *
     * @param status 券状态：字典：VOUCHER_STATUS
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * 获取是否删除，0未删除，已删除，默认0未删除
     *
     * @return del_flag - 是否删除，0未删除，已删除，默认0未删除
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * 设置是否删除，0未删除，已删除，默认0未删除
     *
     * @param delFlag 是否删除，0未删除，已删除，默认0未删除
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取过期时间
     *
     * @return expire_time - 过期时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置过期时间
     *
     * @param expireTime 过期时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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
}