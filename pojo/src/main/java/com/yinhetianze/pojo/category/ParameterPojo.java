package com.yinhetianze.pojo.category;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_parameter")
public class ParameterPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 参数名称
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 排序编号
     */
    private Short sort;

    /**
     * 排序首字母
     */
    @Column(name = "first_word")
    private String firstWord;

    /**
     * 用户输入的参数，0=不可输入，1=可输入，默认0不可输入
     */
    @Column(name = "is_inputable")
    private Short isInputable;

    /**
     * 是否独占一行显示，1=是，0=否，默认1独占一行显示
     */
    @Column(name = "is_whole_line")
    private Short isWholeLine;

    /**
     * 是否有效，1=有效，0=无效，默认1有效
     */
    private Short status;

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
     * 获取参数名称
     *
     * @return param_name - 参数名称
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置参数名称
     *
     * @param paramName 参数名称
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 获取排序编号
     *
     * @return sort - 排序编号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序编号
     *
     * @param sort 排序编号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取排序首字母
     *
     * @return first_word - 排序首字母
     */
    public String getFirstWord() {
        return firstWord;
    }

    /**
     * 设置排序首字母
     *
     * @param firstWord 排序首字母
     */
    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    /**
     * 获取用户输入的参数，0=不可输入，1=可输入，默认0不可输入
     *
     * @return is_inputable - 用户输入的参数，0=不可输入，1=可输入，默认0不可输入
     */
    public Short getIsInputable() {
        return isInputable;
    }

    /**
     * 设置用户输入的参数，0=不可输入，1=可输入，默认0不可输入
     *
     * @param isInputable 用户输入的参数，0=不可输入，1=可输入，默认0不可输入
     */
    public void setIsInputable(Short isInputable) {
        this.isInputable = isInputable;
    }

    /**
     * 获取是否独占一行显示，1=是，0=否，默认1独占一行显示
     *
     * @return is_whole_line - 是否独占一行显示，1=是，0=否，默认1独占一行显示
     */
    public Short getIsWholeLine() {
        return isWholeLine;
    }

    /**
     * 设置是否独占一行显示，1=是，0=否，默认1独占一行显示
     *
     * @param isWholeLine 是否独占一行显示，1=是，0=否，默认1独占一行显示
     */
    public void setIsWholeLine(Short isWholeLine) {
        this.isWholeLine = isWholeLine;
    }

    /**
     * 获取是否有效，1=有效，0=无效，默认1有效
     *
     * @return status - 是否有效，1=有效，0=无效，默认1有效
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置是否有效，1=有效，0=无效，默认1有效
     *
     * @param status 是否有效，1=有效，0=无效，默认1有效
     */
    public void setStatus(Short status) {
        this.status = status;
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
}