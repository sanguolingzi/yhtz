package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.business.customer.model.BusiAmountFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerConsumptionModel;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;

import java.util.List;
import java.util.Map;

public interface CustomerBankrollFlowInfoService {

    BusiCustomerBankrollFlowPojo selectOne(BusiCustomerBankrollFlowPojo record);

    List<BusiCustomerBankrollFlowModel> selectList(BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel);

    /**
     * 查询消费者 消费情况
     * @param paraMap
     * @return
     */
    List<Map<String, Object>> selectConsumption(Map<String,Object> paraMap);

    /**
     * 我的市场 查询 部分余额流水 以及详细信息
     * @param paraMap  bankrollId 指定账户 flowCategory 指定余额类型  flowDescription 指定余额业务
     * @return
     */
    List<BusiAmountFlowModel> selectPersonal(Map<String,Object> paraMap);

    /**
     * 查询指定账户 指定日期的 余额汇总流水
     * @param paraMap
     * @return
     */
    List<BusiCustomerBankrollFlowModel> selectMarket(Map<String,Object> paraMap);

}