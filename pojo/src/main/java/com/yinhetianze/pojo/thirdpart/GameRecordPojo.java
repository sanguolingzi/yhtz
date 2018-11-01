package com.yinhetianze.pojo.thirdpart;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "game_pay_record")
public class GameRecordPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城订单号
     */
    @Column(name = "ordersNo")
    private String ordersNo;

    /**
     * 支付金额(分)
     */
    @Column(name = "payAmount")
    private BigDecimal payAmount;

    /**
     * 购买数量
     */
    @Column(name = "payNum")
    private Integer payNum;

    /**
     * 游戏ID
     */
    @Column(name = "gameId")
    private String gameId;

    /**
     * 游戏类型
     */
    @Column(name = "kingId")
    private String kingId;

    /**
     * 支付状态码
     */
    private String state;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

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


    public String getOrdersNo(){
        return ordersNo;
    }

    public void setOrdersNo(String ordersNo){
        this.ordersNo = ordersNo;
    }

    public BigDecimal getPayAmount(){
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount){
        this.payAmount = payAmount;
    }

    public Integer getPayNum(){
        return payNum;
    }

    public void setPayNum(Integer payNum){
        this.payNum = payNum;
    }

    public String getGameId(){
        return gameId;
    }

    public void setGameId(String gameId){
        this.gameId = gameId;
    }

    public String getKingId(){
        return kingId;
    }

    public void setKingId(String kingId){
        this.kingId = kingId;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
}