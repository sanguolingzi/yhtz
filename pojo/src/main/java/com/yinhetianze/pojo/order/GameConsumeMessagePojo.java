package com.yinhetianze.pojo.order;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "busi_game_consume_message")
public class GameConsumeMessagePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单号
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 商品类型
     */
    @Column(name = "trade_type")
    private Integer tradeType;

    /**
     * 订单描述
     */
    @Column(name = "trade_desc")
    private String tradeDesc;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 游戏ID
     */
    @Column(name = "game_id")
    private Integer gameId;

    /**
     * 商城用户Id
     */
    @Column(name = "customer_id")
    private Integer customerId;

    /**
     * 支付时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 游戏接口返回状态（0正常 1异常）
     */
    private Short code;

    /**
     * 房卡数量
     */
    @Column(name = "num")
    private Integer num;

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
     * 获取订单号
     *
     * @return trade_no - 订单号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 设置订单号
     *
     * @param tradeNo 订单号
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * 获取商品类型
     *
     * @return trade_type - 商品类型
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     * 设置商品类型
     *
     * @param tradeType 商品类型
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 获取订单描述
     *
     * @return trade_desc - 订单描述
     */
    public String getTradeDesc() {
        return tradeDesc;
    }

    /**
     * 设置订单描述
     *
     * @param tradeDesc 订单描述
     */
    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    /**
     * 获取订单金额
     *
     * @return amount - 订单金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置订单金额
     *
     * @param amount 订单金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取游戏ID
     *
     * @return game_id - 游戏ID
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * 设置游戏ID
     *
     * @param gameId 游戏ID
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * 获取商城用户Id
     *
     * @return customer_id - 商城用户Id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 设置商城用户Id
     *
     * @param customerId 商城用户Id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取支付时间
     *
     * @return payment_time - 支付时间
     */
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 设置支付时间
     *
     * @param paymentTime 支付时间
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
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
     * 获取返回结果
     *
     * @return result - 返回结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置返回结果
     *
     * @param result 返回结果
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取游戏接口返回状态（0正常 1异常）
     *
     * @return code - 游戏接口返回状态（0正常 1异常）
     */
    public Short getCode() {
        return code;
    }

    /**
     * 设置游戏接口返回状态（0正常 1异常）
     *
     * @param code 游戏接口返回状态（0正常 1异常）
     */
    public void setCode(Short code) {
        this.code = code;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}