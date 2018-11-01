package com.yinhetianze.pojo.order;

import javax.persistence.*;

@Table(name = "busi_customer_yiyuan_relation")
public class OneYuanOrderPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 类型（1一元专区 2礼包专区）
     */
    private Short type;

    /**
     * 外部主键
     */
    @Column(name = "outside_id")
    private Integer outsideId;

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
     * 获取用户ID
     *
     * @return customer_id - 用户ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置用户ID
     *
     * @param customerId 用户ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取类型（1一元专区 2礼包专区）
     *
     * @return type - 类型（1一元专区 2礼包专区）
     */
    public Short getType() {
        return type;
    }

    /**
     * 设置类型（1一元专区 2礼包专区）
     *
     * @param type 类型（1一元专区 2礼包专区）
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * 获取外部主键
     *
     * @return outside_id - 外部主键
     */
    public Integer getOutsideId() {
        return outsideId;
    }

    /**
     * 设置外部主键
     *
     * @param outsideId 外部主键
     */
    public void setOutsideId(Integer outsideId) {
        this.outsideId = outsideId;
    }
}