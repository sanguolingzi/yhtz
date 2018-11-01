package com.yinhetianze.systemservice.report.model;

import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.systemservice.report.reportutil.ReportColumn;

import java.math.BigDecimal;
import java.util.Date;


public class OrderReportModel extends BasicModel{

    @ReportColumn
    private BigDecimal total_amount;

    @ReportColumn
    private String orders_no;

    @ReportColumn
    private BigDecimal pay_amount;


    @ReportColumn
    private String payTime;
    private Date pay_time;

    @ReportColumn
    private String pay_type;

    @ReportColumn
    private BigDecimal settlement_amount;

    @ReportColumn
    private BigDecimal reward_amount;

    @ReportColumn
    private BigDecimal integral_amount;

    @ReportColumn
    private Integer customer_id;

    @ReportColumn
    private String is_game_order;



    public BigDecimal getTotal_amount(){
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount){
        this.total_amount = total_amount;
    }

    public String getOrders_no(){
        return orders_no;
    }

    public void setOrders_no(String orders_no){
        this.orders_no = orders_no;
    }

    public BigDecimal getPay_amount(){
        return pay_amount;
    }

    public void setPay_amount(BigDecimal pay_amount){
        this.pay_amount = pay_amount;
    }

    public Date getPay_time(){
        return pay_time;
    }

    public void setPay_time(Date pay_time){
        this.pay_time = pay_time;
    }

    public String getPay_type(){
        return pay_type;
    }

    public void setPay_type(String pay_type){
        this.pay_type = pay_type;
    }

    public BigDecimal getSettlement_amount(){
        return settlement_amount;
    }

    public void setSettlement_amount(BigDecimal settlement_amount){
        this.settlement_amount = settlement_amount;
    }

    public BigDecimal getReward_amount(){
        return reward_amount;
    }

    public void setReward_amount(BigDecimal reward_amount){
        this.reward_amount = reward_amount;
    }

    public BigDecimal getIntegral_amount(){
        return integral_amount;
    }

    public void setIntegral_amount(BigDecimal integral_amount){
        this.integral_amount = integral_amount;
    }

    public Integer getCustomer_id(){
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id){
        this.customer_id = customer_id;
    }

    public String getIs_game_order(){
        return is_game_order;
    }

    public void setIs_game_order(String is_game_order){
        this.is_game_order = is_game_order;
    }

    public String getPayTime(){
        return payTime;
    }

    public void setPayTime(String payTime){
        this.payTime = payTime;
    }
}
