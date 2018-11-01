package com.yinhetianze.business.parameter.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import javax.persistence.*;
import java.util.Date;


public class SpecialParametersModel extends BasicModel{

    private Integer id;

    /**
     * 参数名称
     */

    private String paraName;

    /**
     * 参数值
     */

    private String paraValue;

    /**
     * 描述
     */

    private String paraDescribe;

    /**
     * 类型 0.文本框，1.单选 2.下拉框
     */

    private Integer paraType;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 0 正常 1 已删除
     */

    private Short delFlag;

    /**
     * 商品Id
     */

    private Integer productId;

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
     * 获取参数名称
     *
     * @return para_name - 参数名称
     */
    public String getParaName() {
        return paraName;
    }

    /**
     * 设置参数名称
     *
     * @param paraName 参数名称
     */
    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    /**
     * 获取参数值
     *
     * @return para_value - 参数值
     */
    public String getParaValue() {
        return paraValue;
    }

    /**
     * 设置参数值
     *
     * @param paraValue 参数值
     */
    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    /**
     * 获取描述
     *
     * @return para_describe - 描述
     */
    public String getParaDescribe() {
        return paraDescribe;
    }

    /**
     * 设置描述
     *
     * @param paraDescribe 描述
     */
    public void setParaDescribe(String paraDescribe) {
        this.paraDescribe = paraDescribe;
    }

    /**
     * 获取类型 0.文本框，1.单选 2.下拉框
     *
     * @return para_type - 类型 0.文本框，1.单选 2.下拉框
     */
    public Integer getParaType() {
        return paraType;
    }

    /**
     * 设置类型 0.文本框，1.单选 2.下拉框
     *
     * @param paraType 类型 0.文本框，1.单选 2.下拉框
     */
    public void setParaType(Integer paraType) {
        this.paraType = paraType;
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
     * 获取商品Id
     *
     * @return product_id - 商品Id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品Id
     *
     * @param productId 商品Id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}