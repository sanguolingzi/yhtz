package com.yinhetianze.business.settlement.service.impl;

import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.settlement.service.SettlementBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.SettlementPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.settlement.mapper.SettlementBusiMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = {BusinessException.class,RuntimeException.class})
public class SettlementBusiServiceImpl implements SettlementBusiService
{
    @Autowired
    private SettlementBusiMapper mapper;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Override
    public int addSettlement(SettlementPojo settlementPojo,Integer type) {
        String orderId="";
        if(type==1){
            Map<String,Object> map=new HashMap<>();
            map.put("shopId",settlementPojo.getShopId());
            List<Map<String,Object>> list=orderInfoServiceImpl.settlementOrder(map);
            if(CommonUtil.isNotEmpty(list)){
                for(int j=0;j<list.size();j++){
                    if(j==list.size()-1){
                        orderId=orderId+list.get(j).get("id")+"";
                    }else{
                        orderId=orderId+list.get(j).get("id")+",";
                    }
                }
                settlementPojo.setOrdersIds(orderId);
            }
        }
        String[] orderIds =settlementPojo.getOrdersIds().split(",");

        BigDecimal totalCost=new BigDecimal("0");
        BigDecimal finaltTotalCost=new BigDecimal("0");
        for(int i=0;i<orderIds.length;i++){
            Map<String,Object> parement=new HashMap<>();
            List<Map<String,Object>> orderDetail=orderInfoServiceImpl.findOrderDetail(Integer.valueOf(orderIds[i]),null);
            BigDecimal settlementAmount=new BigDecimal("0");
            for(Map<String,Object> map:orderDetail){
                totalCost=totalCost.add(new BigDecimal(map.get("sellPrice")+"").multiply(new BigDecimal(map.get("number")+"")));
                finaltTotalCost=finaltTotalCost.add(new BigDecimal(map.get("finalPrice")+"").multiply(new BigDecimal(map.get("number")+"")));
                settlementAmount=settlementAmount.add(new BigDecimal(map.get("finalPrice")+"").multiply(new BigDecimal(map.get("number")+"")));
            }
            parement.put("settlementStatus",2);
            parement.put("orderId",orderIds[i]);
            parement.put("settlementAmount",settlementAmount);
            orderBusiServiceImpl.updateOrder(parement);
        }
        settlementPojo.setFinaltTotalCost(finaltTotalCost);
        settlementPojo.setTotalCost(totalCost);
        settlementPojo.setRakeCost(totalCost.subtract(finaltTotalCost));
        return mapper.insertSelective(settlementPojo);
    }

    @Override
    public int checkSettlement(SettlementPojo settlementPojo) {
        int settlementStatus=3;
        if(settlementPojo.getStatus()==3){
            settlementStatus=4;
        }
        String[] orderIds=settlementPojo.getOrdersIds().split(",");
        for(int i=0;i<orderIds.length;i++){
            Map<String,Object> parement=new HashMap<>();
            parement.put("settlementStatus",settlementStatus);
            parement.put("orderId",orderIds[i]);
            orderBusiServiceImpl.updateOrder(parement);
        }
        return mapper.updateByPrimaryKeySelective(settlementPojo);
    }

    @Override
    public int paymentSettlement(SettlementPojo settlementPojo) {
        String[] orderIds=settlementPojo.getOrdersIds().split(",");
        for(int i=0;i<orderIds.length;i++){
            Map<String,Object> parement=new HashMap<>();
            parement.put("settlementStatus",5);
            parement.put("orderId",orderIds[i]);
            orderBusiServiceImpl.updateOrder(parement);
        }
        return mapper.updateByPrimaryKeySelective(settlementPojo);
    }

    @Override
    public void insertSelective(SettlementPojo settlementPojo) throws BusinessException {
         int settlement=mapper.insertSelective(settlementPojo);
         if(settlement!=1){
             throw new BusinessException("生成结算单失败");
         }
         Map orderMap =new HashMap();
        orderMap.put("ids",settlementPojo.getOrdersIds());
        orderMap.put("settlementId",settlementPojo.getSettlementId());
         //修改订单状态表
        int ss= orderBusiServiceImpl.updateOrderSettlementId(orderMap);
        if(ss == 0 ){
            throw new BusinessException("订单状态修改失败");
        }
    }
}