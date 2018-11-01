package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.impl.CustomerBankrollBusiServiceImpl;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.impl.CustomerBankrollInfoServiceImpl;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.business.message.service.busi.impl.MessageDetailBusiServiceImpl;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.impl.OrderInfoServiceImpl;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推广赚定时器 没发生退货情况下 确认收货后7天自动返推广赚的奖励给推荐人 前提是推荐人必须存在
 * 推广赚只针对 0 普通商品 别的商品无推广赚情况
 */
public class ExpandTheSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        //获取orderInfoServiceImpl 对象
        OrderInfoServiceImpl orderInfoServiceImpl= (OrderInfoServiceImpl)ApplicationContextFactory.getBean("orderInfoServiceImpl");
        //获取系统参数信息
        /*SysSyspropertiesInfoServiceImpl sysSyspropertiesInfoServiceImpl=(SysSyspropertiesInfoServiceImpl) ApplicationContextFactory.getBean("sysSyspropertiesInfoServiceImpl");
        BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        busiSysSyspropertiesPojo.setpName("groundless");
        BusiSysSyspropertiesPojo sysSysproperties=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo)*/;
        SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
        if(CommonUtil.isNotNull(sysPropertiesUtils)){
            Map<String,Object> orderMap =new HashMap<>();
            orderMap.put("groundless",sysPropertiesUtils.getIntValue("groundless"));
            //获取所有满足条件的未返推广赚的订单
            List<Map<String,Object>> orderList = orderInfoServiceImpl.getexpandThe(orderMap);
            if(CommonUtil.isNotNull(orderList) && orderList.size()>0){
                //获取订单service对象
                OrderBusiService orderBusiServiceImpl=(OrderBusiService)ApplicationContextFactory.getBean("orderBusiServiceImpl");
                //获取用户资金service对象
                CustomerBankrollInfoService bankrollInfoService = (CustomerBankrollInfoServiceImpl)ApplicationContextFactory.getBean("customerBankrollInfoServiceImpl");
                //跟订单推荐用户的奖余额
                orderList.forEach(Map -> {
                            try {
                                BusiCustomerBankrollPojo customerBankroll = bankrollInfoService.selectByCustomerId((Integer) Map.get("promotionUser"));
                                if(CommonUtil.isNotEmpty(customerBankroll)){
                                    orderBusiServiceImpl.updateAmount(Map);
                                }
                            } catch (BusinessException e) {
                                e.printStackTrace();
                            }
                        }

                );
            }
        }
    }
}


