package com.yinhetianze.pojo.tag;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_tag")
public class ProductTagPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标签名称
     */
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 标签内容
     */
    @Column(name = "tag_content")
    private String tagContent;

    /**
     * 标签图标
     */
    @Column(name = "tag_img")
    private String tagImg;

    /**
     * 是否显示，1=显示，0=不显示，默认1显示
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除
     * 1=删除，0=未删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

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
     * 获取标签名称
     *
     * @return tag_name - 标签名称
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * 设置标签名称
     *
     * @param tagName 标签名称
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * 获取标签内容
     *
     * @return tag_content - 标签内容
     */
    public String getTagContent() {
        return tagContent;
    }

    /**
     * 设置标签内容
     *
     * @param tagContent 标签内容
     */
    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    /**
     * 获取标签图标
     *
     * @return tag_img - 标签图标
     */
    public String getTagImg() {
        return tagImg;
    }

    /**
     * 设置标签图标
     *
     * @param tagImg 标签图标
     */
    public void setTagImg(String tagImg) {
        this.tagImg = tagImg;
    }

    /**
     * 获取是否显示，1=显示，0=不显示，默认1显示
     *
     * @return is_show - 是否显示，1=显示，0=不显示，默认1显示
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示，1=显示，0=不显示，默认1显示
     *
     * @param isShow 是否显示，1=显示，0=不显示，默认1显示
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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

    public Short getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(Short delFlag)
    {
        this.delFlag = delFlag;
    }
}