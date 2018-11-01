package com.yinhetianze.systemservice.report.service.info;

import com.yinhetianze.systemservice.report.model.OrderReportModel;

import java.util.List;
import java.util.Map;

public interface ReportInfoService {
    //总退单数
    int selectSumExchange(Map map);
    //总退款金额
    Integer selectSumRefund();
    //供应商结算金额
    Integer selectSumSupplier();
    //退单
    List<Map> selectExchangeList();

    //总订单数
    Map getTotalOrder(Map timeMap);

    //交易金额
    Map getTotalAmountOrder(Map timeMap);

    //退款金额
    Map getTotalRebateAmount(Map timeMap);

    //总订单详情
    List<OrderReportModel> getTotalOrderDetail(Map timeMap);

    //总交易额详情
    List<Map> getTotalAmountDetail(Map timeMap);
}
