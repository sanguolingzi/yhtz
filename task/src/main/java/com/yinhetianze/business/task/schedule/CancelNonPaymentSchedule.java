package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.order.mapper.OrderBusiMapper;
import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.mapper.ProductDetailBusiMapper;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 取消未支付订单定时器 所有订单 包括 0 普通商品 1. 游戏商品 2.会员礼包
 */
public class CancelNonPaymentSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        //获取OrderInfoMapper 对象
        OrderInfoMapper orderInfoMapper= (OrderInfoMapper)ApplicationContextFactory.getBean("orderInfoMapper");
        //获取系统参数信息
        /*SysSyspropertiesInfoServiceImpl sysSyspropertiesInfoServiceImpl=(SysSyspropertiesInfoServiceImpl) ApplicationContextFactory.getBean("sysSyspropertiesInfoServiceImpl");
        BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        busiSysSyspropertiesPojo.setpName("cancelNonPayment");
        BusiSysSyspropertiesPojo sysSysproperties=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo);*/
        SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
        if(CommonUtil.isNotNull(sysPropertiesUtils)){
            Map<String,Object> orderMap =new HashMap<>();
            orderMap.put("orderStatus",1);
            orderMap.put("cancelNonPayment",sysPropertiesUtils.getIntValue("cancelNonPayment"));
            //获取所有满足条件的未支付订单
            List<Map<String,Object>> orderList= orderInfoMapper.getCancelNonPayment(orderMap);
            if(CommonUtil.isNotNull(orderList) && orderList.size()>0){
                //获取订单serverImpl
                OrderBusiService orderBusiServiceImpl=(OrderBusiService)ApplicationContextFactory.getBean("orderBusiServiceImpl");
                //获取可改订单mapper
                /*OrderBusiMapper orderBusiMapper= (OrderBusiMapper)ApplicationContextFactory.getBean("orderBusiMapper");
                //获取商品库存表mapper
                ProductDetailBusiMapper ProductDetailBusiMapper=(ProductDetailBusiMapper)ApplicationContextFactory.getBean("productDetailBusiMapper");*/
                //修改满足条件的订单
                orderList.forEach(Map -> {
                    Map updateMap=new HashMap();
                    updateMap.put("orderId",Map.get("orderId"));
                    updateMap.put("orderStatus",0);
                    updateMap.put("cancelTime",new Date());
                    updateMap.put("isAutoCancel",1);
                   // orderBusiMapper.updateOrder(updateMap);
                    //订单记录入参map
                    Map orderLog =new HashMap();
                    orderLog.put("orderId",Map.get("orderId"));
                    orderLog.put("orderStatus",0);
                    orderLog.put("onceOrderStatus",Map.get("orderStatus"));
                    //新增订单日志记录
                    // orderBusiMapper.addOrderLog(orderLog);
                    //修改商品库存
                    Map productDetail =new HashMap();
                    productDetail.put("number",Map.get("number"));
                    productDetail.put("skuCode",Map.get("productSku"));
                    productDetail.put("prodId",Map.get("productId"));
                    //ProductDetailBusiMapper.updatePDStorage(productDetail);
                    try {
                        orderBusiServiceImpl.updateCancelNonPayment(Map,updateMap,orderLog,productDetail);
                    } catch (BusinessException e) {
                        e.printStackTrace();
                    }
                });

            }
        }
    }
}


