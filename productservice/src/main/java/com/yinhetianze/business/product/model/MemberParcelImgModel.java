package com.yinhetianze.business.product.model;


import com.yinhetianze.core.business.httprequest.BasicModel;

public class MemberParcelImgModel extends BasicModel{
    private Integer id;

    /**
     * 排序号
     */
    private Short sort;

    /**
     * 所属礼包
     */
    private Integer memberParcelId;

    /**
     * 礼包图片
     */
    private String parcelImg;

    /**
     * 0 正常 1 已删除
     */
    private Short delFlag;

    /**
     * 分类轮播图数组
     */
    private String[] memberParcelName;

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
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取所属礼包
     *
     * @return member_parcel_id - 所属礼包
     */
    public Integer getMemberParcelId() {
        return memberParcelId;
    }

    /**
     * 设置所属礼包
     *
     * @param memberParcelId 所属礼包
     */
    public void setMemberParcelId(Integer memberParcelId) {
        this.memberParcelId = memberParcelId;
    }

    /**
     * 获取礼包图片
     *
     * @return parcel_img - 礼包图片
     */
    public String getParcelImg() {
        return parcelImg;
    }

    /**
     * 设置礼包图片
     *
     * @param parcelImg 礼包图片
     */
    public void setParcelImg(String parcelImg) {
        this.parcelImg = parcelImg;
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

    public String[] getMemberParcelName() {
        return memberParcelName;
    }

    public void setMemberParcelName(String[] memberParcelName) {
        this.memberParcelName = memberParcelName;
    }
}
