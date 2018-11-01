package com.yinhetianze.pojo.order;

import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_product_evaluate")
public class EvaluatePojo {
    /**
     * 商品评价ID
     */
    @Id
    @Column(name = "evaluate_id")
    private Integer evaluateId;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 商品SUK
     */
    @Column(name = "product_suk")
    private String productSuk;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品标题
     */
    @Column(name = "product_title")
    private String productTitle;

    /**
     * 商品规格
     */
    @Column(name = "product_spec")
    private String productSpec;

    /**
     * 商品主图
     */
    @Column(name = "product_img")
    private String productImg;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价的图片集
     */
    @Column(name = "evaluate_img")
    private String evaluateImg;

    /**
     * 商品评价星星的数量
     */
    @Column(name = "product_star")
    private Short productStar;

    /**
     * 服务评价星星数量
     */
    @Column(name = "service_star")
    private Short serviceStar;

    /**
     * 物流评价星星数量
     */
    @Column(name = "logistics_star")
    private Short logisticsStar;

    /**
     * 评论时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 评论人
     */
    @Column(name = "evaluate_user")
    private Integer evaluateUser;

    /**
     * 是否为追评（0不是，1是）
     */
    @Column(name = "add_evaluate")
    private Byte addEvaluate;

    /**
     * 店家回复
     */
    private String answer;

    /**
     * 店家回复时间
     */
    @Column(name = "answer_time")
    private Date answerTime;

    /**
     * 是否显示（0隐藏，1显示）
     */
    @Column(name = "is_show")
    private Short isShow;

    /**
     * 0 正常 1 已删除
     */
    @Column(name = "del_flag")
    private Short delFlag;

    /**
     * 获取商品评价ID
     *
     * @return evaluate_id - 商品评价ID
     */
    public Integer getEvaluateId() {
        return evaluateId;
    }

    /**
     * 设置商品评价ID
     *
     * @param evaluateId 商品评价ID
     */
    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单号
     *
     * @return order_no - 订单号
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置订单号
     *
     * @param orderNo 订单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取商品ID
     *
     * @return product_id - 商品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品ID
     *
     * @param productId 商品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取商品SUK
     *
     * @return product_suk - 商品SUK
     */
    public String getProductSuk() {
        return productSuk;
    }

    /**
     * 设置商品SUK
     *
     * @param productSuk 商品SUK
     */
    public void setProductSuk(String productSuk) {
        this.productSuk = productSuk;
    }

    /**
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取商品标题
     *
     * @return product_title - 商品标题
     */
    public String getProductTitle() {
        return productTitle;
    }

    /**
     * 设置商品标题
     *
     * @param productTitle 商品标题
     */
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    /**
     * 获取商品规格
     *
     * @return product_spec - 商品规格
     */
    public String getProductSpec() {
        return productSpec;
    }

    /**
     * 设置商品规格
     *
     * @param productSpec 商品规格
     */
    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    /**
     * 获取商品主图
     *
     * @return product_img - 商品主图
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * 设置商品主图
     *
     * @param productImg 商品主图
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取评价内容
     *
     * @return content - 评价内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评价内容
     *
     * @param content 评价内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取评价的图片集
     *
     * @return evaluate_img - 评价的图片集
     */
    public String getEvaluateImg() {
        return evaluateImg;
    }

    /**
     * 设置评价的图片集
     *
     * @param evaluateImg 评价的图片集
     */
    public void setEvaluateImg(String evaluateImg) {
        this.evaluateImg = evaluateImg;
    }

    /**
     * 获取商品评价星星的数量
     *
     * @return product_star - 商品评价星星的数量
     */
    public Short getProductStar() {
        return productStar;
    }

    /**
     * 设置商品评价星星的数量
     *
     * @param productStar 商品评价星星的数量
     */
    public void setProductStar(Short productStar) {
        this.productStar = productStar;
    }

    /**
     * 获取服务评价星星数量
     *
     * @return service_star - 服务评价星星数量
     */
    public Short getServiceStar() {
        return serviceStar;
    }

    /**
     * 设置服务评价星星数量
     *
     * @param serviceStar 服务评价星星数量
     */
    public void setServiceStar(Short serviceStar) {
        this.serviceStar = serviceStar;
    }

    /**
     * 获取物流评价星星数量
     *
     * @return logistics_star - 物流评价星星数量
     */
    public Short getLogisticsStar() {
        return logisticsStar;
    }

    /**
     * 设置物流评价星星数量
     *
     * @param logisticsStar 物流评价星星数量
     */
    public void setLogisticsStar(Short logisticsStar) {
        this.logisticsStar = logisticsStar;
    }

    /**
     * 获取评论时间
     *
     * @return create_time - 评论时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置评论时间
     *
     * @param createTime 评论时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取评论人
     *
     * @return evaluate_user - 评论人
     */
    public Integer getEvaluateUser() {
        return evaluateUser;
    }

    /**
     * 设置评论人
     *
     * @param evaluateUser 评论人
     */
    public void setEvaluateUser(Integer evaluateUser) {
        this.evaluateUser = evaluateUser;
    }

    /**
     * 获取是否为追评（0不是，1是）
     *
     * @return add_evaluate - 是否为追评（0不是，1是）
     */
    public Byte getAddEvaluate() {
        return addEvaluate;
    }

    /**
     * 设置是否为追评（0不是，1是）
     *
     * @param addEvaluate 是否为追评（0不是，1是）
     */
    public void setAddEvaluate(Byte addEvaluate) {
        this.addEvaluate = addEvaluate;
    }

    /**
     * 获取店家回复
     *
     * @return answer - 店家回复
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置店家回复
     *
     * @param answer 店家回复
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取店家回复时间
     *
     * @return answer_time - 店家回复时间
     */
    public Date getAnswerTime() {
        return answerTime;
    }

    /**
     * 设置店家回复时间
     *
     * @param answerTime 店家回复时间
     */
    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    /**
     * 获取是否显示（0隐藏，1显示）
     *
     * @return is_show - 是否显示（0隐藏，1显示）
     */
    public Short getIsShow() {
        return isShow;
    }

    /**
     * 设置是否显示（0隐藏，1显示）
     *
     * @param isShow 是否显示（0隐藏，1显示）
     */
    public void setIsShow(Short isShow) {
        this.isShow = isShow;
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
}