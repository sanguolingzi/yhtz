package com.yinhetianze.pojo.category;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_cate_param_rela")
public class CateParamRelaPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类ID
     */
    @Column(name = "cate_id")
    private Integer cateId;

    /**
     * 参数ID
     */
    @Column(name = "param_id")
    private Integer paramId;

    @Column(name = "create_time")
    private Date createTime;
    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    public Short getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
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
     * 获取分类ID
     *
     * @return cate_id - 分类ID
     */
    public Integer getCateId() {
        return cateId;
    }

    /**
     * 设置分类ID
     *
     * @param cateId 分类ID
     */
    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    /**
     * 获取参数ID
     *
     * @return param_id - 参数ID
     */
    public Integer getParamId() {
        return paramId;
    }

    /**
     * 设置参数ID
     *
     * @param paramId 参数ID
     */
    public void setParamId(Integer paramId) {
        this.paramId = paramId;
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

    private ParameterPojo parameterPojo;

    public ParameterPojo getParameterPojo() {
        return parameterPojo;
    }

    public void setParameterPojo(ParameterPojo parameterPojo) {
        this.parameterPojo = parameterPojo;
    }
}