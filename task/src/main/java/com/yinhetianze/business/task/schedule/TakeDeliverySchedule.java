package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.business.message.service.busi.impl.MessageDetailBusiServiceImpl;
import com.yinhetianze.business.order.mapper.OrderBusiMapper;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.impl.OrderInfoServiceImpl;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 自动收货定时器 自动收货只针对 0.普通商品 2.会员礼包
 */
public class TakeDeliverySchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
        //获取orderInfoServiceImpl 对象
        OrderInfoServiceImpl orderInfoServiceImpl= (OrderInfoServiceImpl)ApplicationContextFactory.getBean("orderInfoServiceImpl");
        //获取系统参数信息
        /*SysSyspropertiesInfoServiceImpl sysSyspropertiesInfoServiceImpl=(SysSyspropertiesInfoServiceImpl) ApplicationContextFactory.getBean("sysSyspropertiesInfoServiceImpl");
        BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        busiSysSyspropertiesPojo.setpName("takeDeliveryDays");
        BusiSysSyspropertiesPojo sysSysproperties=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo);*/
        //获取发送消息service
        //MessageDetailBusiService messageDetailBusiServiceImpl=(MessageDetailBusiServiceImpl) ApplicationContextFactory.getBean("messageDetailBusiServiceImpl");
        if(CommonUtil.isNotNull(sysPropertiesUtils)){
            Map<String,Object> orderMap =new HashMap<>();
            orderMap.put("orderStatus",3);
            orderMap.put("takeDeliveryDays",sysPropertiesUtils.getIntValue("takeDeliveryDays"));
            //获取所有满足条件的已发货订单
            List<Map<String,Object>> orderList= orderInfoServiceImpl.getTakeDelivery(orderMap);
            if(CommonUtil.isNotNull(orderList) && orderList.size()>0){
                OrderBusiService orderBusiServiceImpl=(OrderBusiService)ApplicationContextFactory.getBean("orderBusiServiceImpl");
                //OrderBusiMapper orderBusiMapper= (OrderBusiMapper)ApplicationContextFactory.getBean("orderBusiMapper");
                //修改满足条件的订单
                orderList.forEach(Map -> {
                    Map updateMap=new HashMap();
                    updateMap.put("orderId",Map.get("id"));
                    updateMap.put("orderStatus",4);
                    updateMap.put("completeTime", new Date());
                   // orderBusiMapper.updateOrder(updateMap);
                    //订单记录入参map
                    Map orderLog =new HashMap();
                    orderLog.put("orderId",Map.get("id"));
                    orderLog.put("orderStatus",4);
                    orderLog.put("onceOrderStatus",Map.get("orderStatus"));
                    //新增订单日志记录
                   // orderBusiMapper.addOrderLog(updateMap,orderLog);
                    //更新销量Map
//                    Map salesMap = new HashMap();
//                    salesMap.put("number",Map.get("number"));
//                    salesMap.put("productId",Map.get("productId"));
                    //推荐费
                    /*Map customerMap=new HashMap();
                    customerMap.put("promotionUser",Map.get("promotionUser"));
                    customerMap.put("promotionPrice",Map.get("promotionPrice"));
                    customerMap.put("ordersNo", Map.get("ordersNo"));
                    customerMap.put("upgradePromotionUser",Map.get("upgradePromotionUser"));
                    customerMap.put("upgradePromotionPrice",Map.get("upgradePromotionPrice"));*/
                    try {
                        orderBusiServiceImpl.takeDelivery(updateMap,orderLog);
                       /* //封装json串map
                        Map jsonMap=new HashMap();
                        jsonMap.put("orderNo",Map.get("ordersNo"));
                        jsonMap.put("state",Map.get("自动收货"));
                        //将对象序列化成json串
                        String mContent=CommonUtil.objectToJsonString(jsonMap);
                        //收货消息通知
                        BusiMessageDetailPojo busiMessageDetailPojo =new BusiMessageDetailPojo();
                        busiMessageDetailPojo.setmType((short)1);
                        busiMessageDetailPojo.setmTitle("自动收货通知");
                        busiMessageDetailPojo.setmContent(mContent);
                        busiMessageDetailPojo.setMessageId((Integer) Map.get("customerId"));
                        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);*/
                    } catch (BusinessException e) {
                        e.printStackTrace();
                    }

                });

            }
        }
    }
}


