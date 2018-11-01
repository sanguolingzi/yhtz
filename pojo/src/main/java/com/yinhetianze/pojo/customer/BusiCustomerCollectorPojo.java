package com.yinhetianze.pojo.customer;

import javax.persistence.*;
import java.util.Date;

@Table(name = "busi_customer_collector")
public class BusiCustomerCollectorPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0 商品收藏 1店铺收藏
     */
    @Column(name = "c_type")
    private Short cType;

    /**
     * 关联数据id
     */
    @Column(name = "relation_id")
    private Integer relationId;

    @Column(name = "del_flag")
    private Short delFlag;

    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "relation_id_his")
    private Integer relationIdHis;

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

    /**
     * @return del_flag
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return customer_id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public Integer getRelationIdHis() {
        return relationIdHis;
    }

    public void setRelationIdHis(Integer relationIdHis) {
        this.relationIdHis = relationIdHis;
    }
}