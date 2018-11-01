package com.yinhetianze.systemservice.thirdpart.model;

import com.yinhetianze.core.business.httprequest.PageModel;

import java.math.BigDecimal;
import java.util.Date;


public class GameRecordModel extends PageModel{

    private Integer id;

    /**
     * 商城订单号
     */

    private String ordersno;

    /**
     * 支付金额(分)
     */

    private BigDecimal payamount;

    /**
     * 购买数量
     */

    private Integer paynum;

    /**
     * 游戏ID
     */

    private String gameId;

    /**
     * 游戏类型
     */
    private String kingid;

    /**
     * 支付状态码
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 查询开始时间
     */
    private String startDate;
    /**
     * 查询结束时间
     */
    private String endDate;
    /**
     * 签名
     */
    private String sign;
    /**
     * 渠道编码
     */
    private String channelCode;
    /**
     *渠道秘钥
     */
    private String channelSecret;
    /**
     * 游戏gameToken
     */
    private String gameToken;

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
     * 获取商城订单号
     *
     * @return ordersNo - 商城订单号
     */
    public String getOrdersno() {
        return ordersno;
    }

    /**
     * 设置商城订单号
     *
     * @param ordersno 商城订单号
     */
    public void setOrdersno(String ordersno) {
        this.ordersno = ordersno;
    }

    /**
     * 获取支付金额(分)
     *
     * @return payAmount - 支付金额(分)
     */
    public BigDecimal getPayamount() {
        return payamount;
    }

    /**
     * 设置支付金额(分)
     *
     * @param payamount 支付金额(分)
     */
    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
    }

    /**
     * 获取购买数量
     *
     * @return payNum - 购买数量
     */
    public Integer getPaynum() {
        return paynum;
    }

    /**
     * 设置购买数量
     *
     * @param paynum 购买数量
     */
    public void setPaynum(Integer paynum) {
        this.paynum = paynum;
    }
    /**
     * 获取游戏类型
     *
     * @return kingId - 游戏类型
     */
    public String getKingid() {
        return kingid;
    }

    /**
     * 设置游戏类型
     *
     * @param kingid 游戏类型
     */
    public void setKingid(String kingid) {
        this.kingid = kingid;
    }

    /**
     * 获取支付状态码
     *
     * @return state - 支付状态码
     */
    public String getState() {
        return state;
    }

    /**
     * 设置支付状态码
     *
     * @param state 支付状态码
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String getSign(){
        return sign;
    }

    public void setSign(String sign){
        this.sign = sign;
    }

    public String getChannelCode(){
        return channelCode;
    }

    public void setChannelCode(String channelCode){
        this.channelCode = channelCode;
    }

    public String getChannelSecret(){
        return channelSecret;
    }

    public void setChannelSecret(String channelSecret){
        this.channelSecret = channelSecret;
    }

    public String getGameId(){
        return gameId;
    }

    public void setGameId(String gameId){
        this.gameId = gameId;
    }

    public String getGameToken(){
        return gameToken;
    }

    public void setGameToken(String gameToken){
        this.gameToken = gameToken;
    }
}