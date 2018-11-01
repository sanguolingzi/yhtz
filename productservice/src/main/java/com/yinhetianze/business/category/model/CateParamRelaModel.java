package com.yinhetianze.business.category.model;

import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httprequest.PageModel;
import com.yinhetianze.pojo.category.ParameterPojo;

import javax.persistence.*;
import java.util.Date;


public class CateParamRelaModel extends PageModel{

    private Integer id;

    /**
     * 分类ID
     */

    private Integer cateId;

    /**
     * 参数ID
     */

    private Integer paramId;


    private Date createTime;
    /**
     * 0 正常 1 已删除
     */

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
    private  ParameterModel parameterModel;

    public ParameterModel getParameterModel() {
        return parameterModel;
    }

    public void setParameterModel(ParameterModel parameterModel) {
        this.parameterModel = parameterModel;
    }

    private ParameterPojo parameterPojo;

    public ParameterPojo getParameterPojo() {
        return parameterPojo;
    }

    public void setParameterPojo(ParameterPojo parameterPojo) {
        this.parameterPojo = parameterPojo;
    }
}