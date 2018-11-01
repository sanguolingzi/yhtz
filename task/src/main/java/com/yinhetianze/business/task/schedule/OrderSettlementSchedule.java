package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.customer.service.busi.impl.CustomerBusiServiceImpl;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.order.service.impl.OrderInfoServiceImpl;
import com.yinhetianze.business.settlement.service.SettlementBusiService;
import com.yinhetianze.business.settlement.service.impl.SettlementBusiServiceImpl;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.pojo.order.SettlementPojo;
import com.yinhetianze.security.custom.userdetails.impl.RedisUserDetailsService;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成结算单定时器
 */
public class OrderSettlementSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        //获取orderInfoServiceImpl 对象
        OrderInfoService orderInfoServiceImpl = (OrderInfoServiceImpl) ApplicationContextFactory.getBean("orderInfoServiceImpl");
        // 获取 SettlementBusiService对象
        SettlementBusiService settlementBusiServiceImpl = (SettlementBusiServiceImpl) ApplicationContextFactory.getBean("settlementBusiServiceImpl");
        //获取可结算订单
        List<Map<String,Object>> list = orderInfoServiceImpl.autoSettlementOrder();
        if (CommonUtil.isNotEmpty(list)) {
            //获取配置的扣税比率
            SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
            if(CommonUtil.isNotNull(sysPropertiesUtils)) {
                for (Map<String,Object> map : list) {
                    try {
                        SettlementPojo settlementPojo = new SettlementPojo();
                        settlementPojo.setShopId(Integer.parseInt(map.get("proxyShopId").toString()));
                        settlementPojo.setShopName(map.get("shopName").toString());
                        //获取总订单总金额
                        BigDecimal totalAmount = new BigDecimal(map.get("totalAmount").toString());
                        //平台应支付金额(未扣税)
                        BigDecimal taxPayAmount = new BigDecimal(map.get("settlementAmount").toString());
                        //计算扣税金额
                        BigDecimal taxAmount = taxPayAmount.multiply(sysPropertiesUtils.getDecimalValue("taxAmount"));
                        //平台应支付金额(扣税)
                        BigDecimal payAmount = taxPayAmount.subtract(taxAmount);
                        //平台获利金额
                        BigDecimal makeAmount = totalAmount.subtract(taxPayAmount);
                        settlementPojo.setTotalCost(totalAmount);
                        settlementPojo.setFinaltTotalCost(payAmount);
                        settlementPojo.setRakeCost(makeAmount);
                        settlementPojo.setTaxAmount(taxAmount);
                        settlementPojo.setOrdersIds(map.get("ids").toString());
                        settlementPojo.setBankCardName(map.get("bank") == null ? null : map.get("bank").toString());
                        settlementPojo.setBankCardNo(map.get("bankCardNumber") == null ? null : map.get("bankCardNumber").toString());
                        settlementPojo.setBankUserName(map.get("accountName") == null ? null : map.get("accountName").toString());
                        settlementPojo.setSettlementNo(CommonUtil.getSerialnumber());
                        settlementBusiServiceImpl.insertSelective(settlementPojo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoggerUtil.error(OrderSettlementSchedule.class, "生成结算单失败" + map.get("proxyShopId") + ",时间：" + new Date()+e.toString());
                    }
                }
            }


        }
    }
}


