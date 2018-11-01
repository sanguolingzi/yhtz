package com.yinhetianze.systemservice.report.service.info.Impl;

import com.yinhetianze.systemservice.report.mapper.info.ReportInfoMapper;
import com.yinhetianze.systemservice.report.model.OrderReportModel;
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportInfoServiceImpl implements ReportInfoService {

    @Autowired
    private ReportInfoMapper mapper;

    @Override
    public int selectSumExchange(Map map) {
        return mapper.selectSumExchange(map);
    }


    @Override
    public Integer selectSumRefund() {
        return mapper.selectSumRefund();
    }

    @Override
    public Integer selectSumSupplier() {
        return mapper.selectSumSupplier();
    }

    @Override
    public List<Map> selectExchangeList() {
        return mapper.selectExchangeList();
    }   
	
	@Override
    public Map getTotalOrder(Map timeMap){
        return mapper.getTotalOrder(timeMap);
    }

    @Override
    public Map getTotalAmountOrder(Map timeMap){
        return mapper.getTotalAmountOrder(timeMap);
    }

    @Override
    public Map getTotalRebateAmount(Map timeMap){
        return mapper.getTotalRebateAmount(timeMap);
    }

    @Override
    public List<OrderReportModel> getTotalOrderDetail(Map timeMap){
        return mapper.getTotalOrderDetail(timeMap);
    }

    @Override
    public List<Map> getTotalAmountDetail(Map timeMap){
        return mapper.getTotalAmountDetail(timeMap);
    }
}
