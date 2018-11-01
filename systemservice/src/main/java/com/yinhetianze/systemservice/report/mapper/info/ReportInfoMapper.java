package com.yinhetianze.systemservice.report.mapper.info;

import com.yinhetianze.systemservice.report.model.OrderReportModel;

import java.util.List;
import java.util.Map;

public interface ReportInfoMapper{

    //总退单数
    int selectSumExchange(Map map);
    //总退款金额
    Integer selectSumRefund();
    //供应商结算金额
    Integer selectSumSupplier();
    //退单
    List<Map>selectExchangeList();

    Map getTotalOrder(Map timeMap);

    Map getTotalAmountOrder(Map timeMap);

    Map getTotalRebateAmount(Map timeMap);

    List<OrderReportModel> getTotalOrderDetail(Map timeMap);

    List<Map> getTotalAmountDetail(Map timeMap);
}
