package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.evaluate.mapper.EvaluateBusiMapper;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.order.mapper.OrderBusiMapper;
import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动评价定时器 自动评价只针对 0普通商品 1.游戏商品 会员礼包不参与评价
 */
public class GoodCommentSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        //获取orderInfoServiceImpl 对象
        OrderInfoMapper orderInfoMapper= (OrderInfoMapper)ApplicationContextFactory.getBean("orderInfoMapper");
        //获取系统参数信息
        /*SysSyspropertiesInfoServiceImpl sysSyspropertiesInfoServiceImpl=(SysSyspropertiesInfoServiceImpl) ApplicationContextFactory.getBean("sysSyspropertiesInfoServiceImpl");
        BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        busiSysSyspropertiesPojo.setpName("goodComment");
        BusiSysSyspropertiesPojo sysSysproperties=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo);*/
        SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
        if(CommonUtil.isNotNull(sysPropertiesUtils)){
            Map<String,Object> orderMap =new HashMap<>();
            orderMap.put("orderStatus",4);
            orderMap.put("goodComment",sysPropertiesUtils.getIntValue("goodComment"));
            //获取所有满足条件的已发货订单
            List<Map<String,Object>> orderList= orderInfoMapper.getGoodComment(orderMap);
            if(CommonUtil.isNotNull(orderList) && orderList.size()>0){
                //获取司改订单mapper
                OrderBusiService orderBusiServiceImpl=(OrderBusiService)ApplicationContextFactory.getBean("orderBusiServiceImpl");
               // OrderBusiMapper orderBusiMapper= (OrderBusiMapper)ApplicationContextFactory.getBean("orderBusiMapper");
                //获取评论mapper
               // EvaluateBusiMapper evaluateBusiMapper= (EvaluateBusiMapper)ApplicationContextFactory.getBean("evaluateBusiMapper");
                //修改满足条件的订单
                orderList.forEach(Map -> {
                    Map updateMap=new HashMap();
                    updateMap.put("orderId",Map.get("orderId"));
                    updateMap.put("orderStatus",5);
                    updateMap.put("evaluateTime",new Date());
                   // orderBusiMapper.updateOrder(updateMap);
                    //订单记录入参map
                    Map orderLog =new HashMap();
                    orderLog.put("orderId",Map.get("orderId"));
                    orderLog.put("orderStatus",5);
                    orderLog.put("onceOrderStatus",Map.get("orderStatus"));
                    //新增订单日志记录
                    //orderBusiMapper.addOrderLog(orderLog);
                    //评论记录入参Model
                    EvaluateModel evaluateModel =new EvaluateModel();
                    evaluateModel.setOrderId((Integer) Map.get("orderId"));
                    evaluateModel.setProductId((Integer) Map.get("productId"));
                    evaluateModel.setProductSuk((String) Map.get("productSku"));
                    evaluateModel.setProductName((String) Map.get("productName"));
                    evaluateModel.setProductTitle((String) Map.get("productTitle"));
                    evaluateModel.setProductSpec((String) Map.get("productSpec"));
                    evaluateModel.setProductImg((String) Map.get("productImg"));
                    evaluateModel.setShopId((Integer) Map.get("shopId"));
                    evaluateModel.setProductStar((short)5);
                    evaluateModel.setServiceStar((short)5);
                    evaluateModel.setLogisticsStar((short)5);
                    evaluateModel.setContent("默认好评");
                    evaluateModel.setOrderNo(Map.get("ordersNo").toString());
                    evaluateModel.setEvaluateUser(Integer.parseInt(Map.get("customerId").toString()));
                    //新增订单评论记录
                   // evaluateBusiMapper.autoEvaluate(evaluateModel);
                    try {
                        orderBusiServiceImpl.goodComment(evaluateModel,orderLog,updateMap);
                    } catch (BusinessException e) {
                        e.printStackTrace();
                    }
                });

            }
        }
    }
}


