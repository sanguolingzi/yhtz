package com.yinhetianze.business.order.service.impl;


import com.yinhetianze.business.activity.service.busi.ActivityProductBusiService;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollFlowBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.business.evaluate.mapper.EvaluateBusiMapper;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.exchange.service.ExchangeBusiService;
import com.yinhetianze.business.expressCode.service.ExpressCodeInfoService;
import com.yinhetianze.business.logistics.service.busi.LogisticsInformationBusiService;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.business.oneYuanOrder.service.OneYuanOrderBusiService;
import com.yinhetianze.business.order.mapper.OrderBusiMapper;
import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.business.product.service.ProductFresherBusiService;
import com.yinhetianze.business.product.service.ProductFresherRewardBusiService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopCompanyInfoServiceImpl;
import com.yinhetianze.business.voucher.service.busi.VoucherBusiService;
import com.yinhetianze.business.voucher.service.info.VoucherInfoService;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import com.yinhetianze.pojo.order.*;
import com.yinhetianze.pojo.product.VoucherlPojo;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = {BusinessException.class,RuntimeException.class})
public class OrderBusiServiceImpl implements OrderBusiService{

    @Autowired
    private OrderBusiMapper orderBusiMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private CustomerBankrollFlowBusiService customerBankrollFlowBusiServiceImpl;

    @Autowired
    private EvaluateBusiMapper evaluateBusiMapper;

    @Autowired
    private ExchangeBusiService exchangeBusiServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private GameRecordInfoService gameRecordInfoServiceImpl;


    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private OneYuanOrderBusiService oneYuanOrderBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;

    @Autowired
    private LogisticsInformationBusiService logisticsInformationBusiServiceImpl;

    @Autowired
    private ExpressCodeInfoService expressCodeInfoServiceImpl;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private AreaModelUtils areaModelUtils;

    @Autowired
    private ShopCompanyInfoServiceImpl shopCompanyInfoServiceImpl;

    @Autowired
    private ProductFresherRewardBusiService productFresherRewardBusiServiceImpl;

    @Autowired
    private ProductFresherBusiService productFresherBusiServiceImpl;

    @Autowired
    private ActivityProductBusiService activityProductBusiServiceImpl;

    @Autowired
    private VoucherInfoService voucherInfoServiceImpl;

    @Autowired
    private VoucherBusiService voucherBusiServiceImpl;

    @Override
    public int[] addOrder(List<Map<String, Object>> list)throws BusinessException {
        try {
            int[] orderIds=new int[list.size()];
            SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
            for(int k=0;k<list.size();k++){
                Map map2=(Map)list.get(k).get("order");
                List<Map<String, Object>> orderDetail=(List)list.get(k).get("orderDetail");
                int addOrder=orderBusiMapper.addOrder(map2);
                orderIds[k]=(Integer) map2.get("id");

                for(int i=0;i<orderDetail.size();i++){
                    orderDetail.get(i).put("orderId", map2.get("id"));
                }
                int addOrderInfo=orderBusiMapper.addOrderDetail(orderDetail);
                if(addOrder<1||addOrderInfo<1){
                    throw new BusinessException("创建订单失败");
                }

                //扣除星币，减库存
                if(new BigDecimal(map2.get("integralAmount")+"").compareTo(BigDecimal.ZERO)==1){
                    BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> list1=new ArrayList<>();

                    busiUpdateBankrollModel.setStarCoin(new BigDecimal(map2.get("integralAmount")+""));
                    busiUpdateBankrollModel.setCustomerId(Integer.valueOf(map2.get("customerId")+""));
                    busiUpdateBankrollModel.setStarCoinAddOrMinus((short)1);

                    busiCustomerBankrollFlowModel.setFlowType((short)1);
                    busiCustomerBankrollFlowModel.setFlowCategory((short)3);
                    busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.STARCOINPAY.getValue());
                    busiCustomerBankrollFlowModel.setAmount(new BigDecimal(map2.get("integralAmount")+""));
                    busiCustomerBankrollFlowModel.setFlowNumber(map2.get("ordersNo")+"");
                    busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(map2.get("id")+""));
                    list1.add(busiCustomerBankrollFlowModel);

                    busiUpdateBankrollModel.setList(list1);

                    String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                    if(CommonUtil.isEmpty(id)){
                        throw new BusinessException("添加账户流水失败");
                    }
                }

                //扣除游戏币
                if(CommonUtil.isNotEmpty(map2.get("rewardAmount"))&&new BigDecimal(map2.get("rewardAmount")+"").compareTo(BigDecimal.ZERO)==1){
                    BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> list2=new ArrayList<>();

                    busiUpdateBankrollModel.setRewardAmount(new BigDecimal(map2.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
                    busiUpdateBankrollModel.setCustomerId(Integer.valueOf(map2.get("customerId")+""));
                    busiUpdateBankrollModel.setRewardAmountAddOrMinus((short)1);

                    busiCustomerBankrollFlowModel.setFlowType((short)1);
                    busiCustomerBankrollFlowModel.setFlowCategory((short)6);
                    busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.PAYGAMEAMOUNT.getValue());
                    busiCustomerBankrollFlowModel.setAmount(new BigDecimal(map2.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
                    busiCustomerBankrollFlowModel.setFlowNumber(map2.get("ordersNo")+"");
                    busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(map2.get("id")+""));
                    list2.add(busiCustomerBankrollFlowModel);

                    busiUpdateBankrollModel.setList(list2);

                    String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);

                    if(CommonUtil.isEmpty(id)){
                        throw new BusinessException("添加账户流水失败");
                    }
                }

                //增加销量
               /* if(new BigDecimal(map2.get("payAmount")+"").compareTo(BigDecimal.ZERO)==0){
                    for(int i=0;i<orderDetail.size();i++){
                        Map<String,Object> map=new HashMap<>();
                        map.put("productId",orderDetail.get(i).get("prodId"));
                        map.put("number",orderDetail.get(i).get("number")+"");
                        orderBusiMapper.updateSales(map);
                    }
                }*/

                for(int i=0;i<orderDetail.size();i++){
                    Map<String,Object> map=new HashMap<>();
                    map.put("prodId",orderDetail.get(i).get("prodId"));
                    map.put("skuCode",orderDetail.get(i).get("prodSku"));
                    map.put("number",0-Integer.valueOf(orderDetail.get(i).get("number")+""));
                    int count=productDetailBusiServiceImpl.updatePDStorage(map);
                    if(count<2){
                        throw new BusinessException("商品更新库存失败");
                    }
                }

                //游戏商品纯友旗币支付通知，送余额
                if(Integer.valueOf(map2.get("orderStatus")+"")==4&&Integer.valueOf(map2.get("isGameOrder")+"")==1){
                    GameBusinessModel gameBusinessModel=new GameBusinessModel();
                    gameBusinessModel.setTradeNo(map2.get("ordersNo")+"");
                    gameBusinessModel.setAmount(new BigDecimal(map2.get("totalAmount")+"").multiply(new BigDecimal("100")));
                    gameBusinessModel.setCustomerId(map2.get("customerId")+"");
                    gameBusinessModel.setGameId(Integer.valueOf(map2.get("gameId")+""));
                    gameBusinessModel.setTradeDesc("友旗币支付"+map2.get("totalAmount"));
                    gameBusinessModel.setPaymentTime(sf.format(new Date()));
                    gameBusinessModel.setNum(new BigDecimal(map2.get("roomCardNum")+"").intValue());
                    gameBusinessModel.setTradeType(sysPropertiesUtils.getIntValue("productType"));
                    String result=gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);
                    if(!result.equals("success")){
                        map2.put("gameNotifyResult",1);
                    }else{
                        map2.put("gameNotifyResult",0);
                    }
                    //合伙人反余额
                    if(CommonUtil.isNotEmpty(map2.get("partnerId"))){
                        BusiUpdateBankrollModel busiUpdateBankrollModel1=new BusiUpdateBankrollModel();
                        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel1=new BusiCustomerBankrollFlowModel();
                        List<BusiCustomerBankrollFlowModel> listBankroll1=new ArrayList<>();
                        busiUpdateBankrollModel1.setAmount(new BigDecimal(map2.get("partnerAmount")+""));
                        busiUpdateBankrollModel1.setGameId(Integer.valueOf(map2.get("partnerId")+""));
                        busiUpdateBankrollModel1.setAmountAddOrMinus((short)0);
                        busiCustomerBankrollFlowModel1.setFlowType((short)0);
                        busiCustomerBankrollFlowModel1.setFlowCategory((short)1);
                        busiCustomerBankrollFlowModel1.setFlowDescription(BankrollInfoEnum.PARTNERGIVE.getValue());
                        busiCustomerBankrollFlowModel1.setAmount(new BigDecimal(map2.get("partnerAmount")+""));
                        busiCustomerBankrollFlowModel1.setFlowNumber(map2.get("ordersNo")+"");
                        busiCustomerBankrollFlowModel1.setRelationId(Integer.valueOf(map2.get("id")+""));
                        busiCustomerBankrollFlowModel1.setCreateId(Integer.valueOf(map2.get("customerId")+""));
                        listBankroll1.add(busiCustomerBankrollFlowModel1);
                        busiUpdateBankrollModel1.setList(listBankroll1);
                        String id1=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel1);
                        if(CommonUtil.isEmpty(id1)){
                            throw new BusinessException("合伙人返余额失败");
                        }
                    }


                    //会员反余额
                    /*if(CommonUtil.isNotEmpty(map2.get("promotionUser"))&&new BigDecimal(map2.get("promotionPrice")+"").compareTo(BigDecimal.ZERO)==1){
                        BusiUpdateBankrollModel busiUpdateBankrollModel2=new BusiUpdateBankrollModel();
                        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel2=new BusiCustomerBankrollFlowModel();
                        List<BusiCustomerBankrollFlowModel> listBankroll2=new ArrayList<>();
                        busiUpdateBankrollModel2.setAmount(new BigDecimal(map2.get("promotionPrice")+""));
                        busiUpdateBankrollModel2.setCustomerId(Integer.valueOf(map2.get("promotionUser")+""));
                        busiUpdateBankrollModel2.setAmountAddOrMinus((short)0);
                        busiCustomerBankrollFlowModel2.setFlowType((short)0);
                        busiCustomerBankrollFlowModel2.setFlowCategory((short)1);
                        busiCustomerBankrollFlowModel2.setFlowDescription(BankrollInfoEnum.MEMBERGIVE.getValue());
                        busiCustomerBankrollFlowModel2.setAmount(new BigDecimal(map2.get("promotionPrice")+""));
                        busiCustomerBankrollFlowModel2.setFlowNumber(map2.get("ordersNo")+"");
                        busiCustomerBankrollFlowModel2.setRelationId(Integer.valueOf(map2.get("id")+""));
                        busiCustomerBankrollFlowModel2.setCreateId(Integer.valueOf(map2.get("customerId")+""));
                        listBankroll2.add(busiCustomerBankrollFlowModel2);
                        busiUpdateBankrollModel2.setList(listBankroll2);
                        String id2=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel2);
                        if(CommonUtil.isEmpty(id2)){
                            throw new BusinessException("会员返余额失败");
                        }
                    }*/

                    //推荐人的推荐人
                    /*if(CommonUtil.isNotEmpty(map2.get("upgradePromotionUser"))&&new BigDecimal(map2.get("upgradePromotionPrice")+"").compareTo(BigDecimal.ZERO)==1){
                        BusiUpdateBankrollModel busiUpdateBankrollModel2=new BusiUpdateBankrollModel();
                        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel2=new BusiCustomerBankrollFlowModel();
                        List<BusiCustomerBankrollFlowModel> listBankroll2=new ArrayList<>();
                        busiUpdateBankrollModel2.setAmount(new BigDecimal(map2.get("upgradePromotionPrice")+""));
                        busiUpdateBankrollModel2.setCustomerId(Integer.valueOf(map2.get("upgradePromotionUser")+""));
                        busiUpdateBankrollModel2.setAmountAddOrMinus((short)0);
                        busiCustomerBankrollFlowModel2.setFlowType((short)0);
                        busiCustomerBankrollFlowModel2.setFlowCategory((short)1);
                        busiCustomerBankrollFlowModel2.setFlowDescription(BankrollInfoEnum.SECONDMEMBERGIVE.getValue());
                        busiCustomerBankrollFlowModel2.setAmount(new BigDecimal(map2.get("upgradePromotionPrice")+""));
                        busiCustomerBankrollFlowModel2.setFlowNumber(map2.get("ordersNo")+"");
                        busiCustomerBankrollFlowModel2.setRelationId(Integer.valueOf(map2.get("id")+""));
                        busiCustomerBankrollFlowModel2.setCreateId(Integer.valueOf(map2.get("customerId")+""));
                        listBankroll2.add(busiCustomerBankrollFlowModel2);
                        busiUpdateBankrollModel2.setList(listBankroll2);
                        String id2=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel2);
                        if(CommonUtil.isEmpty(id2)){
                            throw new BusinessException("游戏订单推荐人的推荐人反余额失败");
                        }
                    }*/

                    //立即反消费券
                    /*if(new BigDecimal(map2.get("giveIntegral")+"").compareTo(BigDecimal.ZERO)==1){
                        BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                        List<BusiCustomerBankrollFlowModel> listBankroll=new ArrayList<>();
                        busiUpdateBankrollModel.setStarCoin(new BigDecimal(map2.get("giveIntegral")+""));
                        busiUpdateBankrollModel.setCustomerId(Integer.valueOf(map2.get("customerId")+""));
                        busiUpdateBankrollModel.setStarCoinAddOrMinus((short)0);
                        busiCustomerBankrollFlowModel.setFlowType((short)0);
                        busiCustomerBankrollFlowModel.setFlowCategory((short)3);
                        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.GAMEADD.getValue());
                        busiCustomerBankrollFlowModel.setAmount(new BigDecimal(map2.get("giveIntegral")+""));
                        busiCustomerBankrollFlowModel.setFlowNumber(map2.get("ordersNo")+"");
                        busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(map2.get("id")+""));
                        listBankroll.add(busiCustomerBankrollFlowModel);
                        busiUpdateBankrollModel.setList(listBankroll);
                        String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                        if(CommonUtil.isEmpty(id)){
                            throw new BusinessException("反消费券失败");
                        }
                        //返利记录
                        RebatePojo rebatePojo=new RebatePojo();
                        rebatePojo.setCustomerId(Integer.valueOf(map2.get("customerId")+""));
                        rebatePojo.setOrderAmount(new BigDecimal(map2.get("totalAmount")+""));
                        rebatePojo.setRecordNo(map2.get("ordersNo")+"");
                        rebatePojo.setYokaAmount(new BigDecimal(map2.get("giveIntegral")+""));
                        int youka=rebateBusiServiceImpl.add(rebatePojo);
                        if(youka<1){
                            throw new BusinessException("反消费券添加记录失败");
                        }
                    }*/
                }
            }
            return orderIds;
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }

    @Override
    public int deleteOrder(Integer orderId) {
        return orderBusiMapper.deleteOrder(orderId);
    }

    @Override
    public int updateOrder(Map<String, Object> map) {
        return orderBusiMapper.updateOrder(map);
    }

    @Override
    public int addOrderLog(Map<String, Object> map) {
        return orderBusiMapper.addOrderLog(map);
    }


    @Override
    public void changeOrder(Integer customerId,Integer[] orderIds,BigDecimal amount,List<BusiCustomerBankrollFlowModel> list) throws BusinessException{
        try {
            //改变订单状态
            String totalOrderNo = CommonUtil.getSerialnumber();
            for(int j=0;j<orderIds.length;j++){
                Map<String,Object> order=new HashMap<>();
                order.put("orderId",orderIds[j]);
                order.put("orderStatus",2);
                order.put("payTime",new Date());
                order.put("totalOrderNo", totalOrderNo);
                order.put("payType",4);
                int i=orderBusiMapper.updateOrder(order);
                if(i!=1){
                    throw new BusinessException("更改订单状态失败");
                }
                //操作记录
                order.put("onceOrderStatus",1);
                order.put("actorId",customerId);
                orderBusiMapper.addOrderLog(order);
            }

            //余额支付改变账户余额
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            busiUpdateBankrollModel.setAmount(amount);
            busiUpdateBankrollModel.setCustomerId(customerId);
            busiUpdateBankrollModel.setAmountAddOrMinus((short)1);
            busiUpdateBankrollModel.setList(list);

            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }

        }catch (Exception e){
            throw new BusinessException(e);
        }
    }

    @Override
    public int addOrderAudit(Map<String, Object> map) {

        return orderBusiMapper.addOrderAudit(map);
    }

    @Override
    public int updateOrderAudit(Map<String, Object> map) {
        return orderBusiMapper.updateOrderAudit(map);
    }

    @Override
    public int addOrderTransRecord(Map<String, Object> map) {
        return orderBusiMapper.addOrderTransRecord(map);
    }

    @Override
    public int updateTransRecord(Map<String, Object> map) {
        return orderBusiMapper.updateTransRecord(map);
    }

    @Override
    public void cancelOrder(Map<String,Object> orderMap) throws BusinessException{
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderMap.get("id"));
        map.put("orderStatus", 0);
        map.put("cancelTime", new Date());
        map.put("isAutoCancel", 0);
        int i = orderBusiMapper.updateOrder(map);
        if (i != 1) {
            throw new BusinessException("更改订单状态失败");
        }
        //操作记录
        map.put("onceOrderStatus", orderMap.get("orderStatus"));
        map.put("actorId", orderMap.get("customerId"));
        orderBusiMapper.addOrderLog(map);

        //补回扣除的星币，补回扣除的库存
        if(new BigDecimal(orderMap.get("integralAmount")+"").compareTo(BigDecimal.ZERO)==1){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

            busiUpdateBankrollModel.setStarCoin(new BigDecimal(orderMap.get("integralAmount")+""));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderMap.get("customerId")+""));
            busiUpdateBankrollModel.setStarCoinAddOrMinus((short)0);

            busiCustomerBankrollFlowModel.setFlowType((short)0);
            busiCustomerBankrollFlowModel.setFlowCategory((short)3);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.STARCOINPAYBACK.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(orderMap.get("integralAmount")+""));
            busiCustomerBankrollFlowModel.setFlowNumber(orderMap.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderMap.get("id")+""));
            list.add(busiCustomerBankrollFlowModel);

            busiUpdateBankrollModel.setList(list);

            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);

            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }

        //补回扣除的游戏币
        if(CommonUtil.isNotEmpty(orderMap.get("rewardAmount"))&&new BigDecimal(orderMap.get("rewardAmount")+"").compareTo(BigDecimal.ZERO)==1){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

            busiUpdateBankrollModel.setRewardAmount(new BigDecimal(orderMap.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderMap.get("customerId")+""));
            busiUpdateBankrollModel.setRewardAmountAddOrMinus((short)0);

            busiCustomerBankrollFlowModel.setFlowType((short)0);
            busiCustomerBankrollFlowModel.setFlowCategory((short)6);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.GAMEAMONUTBACK.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(orderMap.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiCustomerBankrollFlowModel.setFlowNumber(orderMap.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderMap.get("id")+""));
            list.add(busiCustomerBankrollFlowModel);

            busiUpdateBankrollModel.setList(list);

            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);

            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }

        //更新库存
        if(Integer.valueOf(orderMap.get("isGameOrder")+"")<2){
            List<Map<String,Object>> orderDetail=orderInfoMapper.findOrderDetail(Integer.valueOf(map.get("orderId")+""),null);
            for(int j=0;j<orderDetail.size();j++){
                map=new HashMap<>();
                map.put("prodId",orderDetail.get(j).get("prodId"));
                map.put("skuCode",orderDetail.get(j).get("prodSku"));
                map.put("number",Integer.valueOf(orderDetail.get(j).get("number")+""));
                int k=productDetailBusiServiceImpl.updatePDStorage(map);
                if(k<2){
                    throw new BusinessException("商品更新库存失败");
                }
            }
        }

    }

    @Override
    public int updateOrderDetail(Map<String, Object> map) {
        return orderBusiMapper.updateOrderDetail(map);
    }

    @Override
    public int addRefundOrder(Map<String, Object> map) throws BusinessException {
        //更改订单状态
        int i=orderBusiMapper.updateOrder(map);
        //操作记录
        int j=orderBusiMapper.addOrderLog(map);

        //查询订单信息
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("orderId",map.get("orderId"));
        List<Map<String,Object>> orderList=orderInfoMapper.findBackOrder(parameter);
        //查询店铺负责人，就是商家
        //店铺信息
        BusiShopPojo busiShopPojo = new BusiShopPojo();
        busiShopPojo.setId(Integer.valueOf(orderList.get(0).get("shopId")+""));
        busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
        if(CommonUtil.isEmpty(busiShopPojo)){
            throw new BusinessException("没有相关店铺信息");
        }
        //加到售后表
        ExchangePojo exchangePojo=new ExchangePojo();
        exchangePojo.setExchangeNo(CommonUtil.getSerialnumber());
        //exchangePojo.setUserId(busiShopPojo.getCustomerId());
        exchangePojo.setShopId(busiShopPojo.getId());
        exchangePojo.setProxyShopId(Integer.valueOf(orderList.get(0).get("proxyShopId")+""));
        exchangePojo.setOrderNo(orderList.get(0).get("ordersNo")+"");
        exchangePojo.setExchangeType((short)2);
        exchangePojo.setCustomerId(Integer.valueOf(map.get("actorId")+""));
        exchangePojo.setOrderAmount(new BigDecimal(orderList.get(0).get("totalAmount")+""));
        exchangePojo.setApplyAmount(new BigDecimal(orderList.get(0).get("totalAmount")+"").subtract(new BigDecimal(orderList.get(0).get("rewardAmount")+"")));
        int k=exchangeBusiServiceImpl.addExchange(exchangePojo);
        return i+j+k;
    }

    @Override
    public int sendOrder(Map<String, Object> map) throws BusinessException {
        Map<String, Object> parment = new HashMap<>();
        parment.put("orderId", map.get("id"));
        parment.put("deliveryTime", new Date());
        parment.put("orderStatus", 3);
        parment.put("onceOrderStatus", 2);
        int i=orderBusiMapper.updateOrder(parment);

        //操作记录
        parment.put("actorId",map.get("userId"));
        int j = orderBusiMapper.addOrderLog(parment);
        if (j != 1) {
            throw new BusinessException("订单发货后增加操作记录失败");
        }

        //通知
        Map<String, Object> orderDetail = orderInfoMapper.findOrderDetail(Integer.valueOf(map.get("id")+""),null).get(0);
        Map<String, Object> jsonMap = new HashMap<>();
        BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
        busiMessageDetailPojo.setmType((short) 0);
        busiMessageDetailPojo.setmTitle("订单发货通知");
        jsonMap.put("state", "订单发货");
        jsonMap.put("orderNo", map.get("ordersNo"));
        jsonMap.put("companyName",map.get("companyName"));
        jsonMap.put("logisticNo",map.get("expressNo"));
        jsonMap.put("prodUrl", orderDetail.get("photoUrl"));
        jsonMap.put("orderId",map.get("id"));
        busiMessageDetailPojo.setmContent(CommonUtil.objectToJsonString(jsonMap));
        busiMessageDetailPojo.setMessageId(Integer.valueOf(map.get("customerId") + ""));
        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);
        return i;
    }

    @Override
    public int updateLogistics(Map<String, Object> map) throws BusinessException {
        Map<String, Object> parment = new HashMap<>();
        parment.put("orderId", map.get("id"));
        parment.put("expressNo", map.get("expressNo"));
        parment.put("companyName",map.get("companyName"));
        int i=orderBusiMapper.updateOrder(parment);
        //物流订阅
        try {
           LogisticsInformationPojo logisticsInformationPojo = new LogisticsInformationPojo();
            ExpressCodePojo expressCodePojo=new ExpressCodePojo();
            expressCodePojo.setExpressCompany(map.get("companyName")+"");
            expressCodePojo=expressCodeInfoServiceImpl.selectOne(expressCodePojo);
            logisticsInformationPojo.setLogisticeCode(map.get("expressNo")+"");
            logisticsInformationPojo.setExpressCode(expressCodePojo.getExpressCode());
            logisticsInformationPojo.setExpressCompany(map.get("companyName")+"");
            logisticsInformationPojo.setOrdersId(Integer.valueOf(map.get("id")+""));

            ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
            shopProxyPojo.setId(Integer.valueOf(map.get("proxyShopId")+""));
            shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
            logisticsInformationPojo.setSenderName(shopProxyPojo.getShopName());
            logisticsInformationPojo.setSenderMobile(shopProxyPojo.getShopPhone());
            logisticsInformationPojo.setSenderProvince(areaModelUtils.getProvince(shopProxyPojo.getShopProvince(),false).getName());
            logisticsInformationPojo.setSenderCity(areaModelUtils.getCity(shopProxyPojo.getShopCity(),false).getName());
            logisticsInformationPojo.setSenderArea(areaModelUtils.getCounty(shopProxyPojo.getShopArea()).getName());
            logisticsInformationPojo.setSenderAddress(shopProxyPojo.getShopAddress());

            BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
            busiCustomerReceiveaddressPojo.setId(Integer.valueOf(map.get("receiveAddressId") + ""));
            busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
            logisticsInformationPojo.setReceiverName(busiCustomerReceiveaddressPojo.getReceiveName());
            logisticsInformationPojo.setReceiverMobile(busiCustomerReceiveaddressPojo.getPhone());
            logisticsInformationPojo.setReceiverProvince(busiCustomerReceiveaddressPojo.getRegionLocation());
            logisticsInformationPojo.setReceiverCity(busiCustomerReceiveaddressPojo.getCity());
            logisticsInformationPojo.setReceiverArea(busiCustomerReceiveaddressPojo.getAreaCounty());
            logisticsInformationPojo.setReceiverAddress(busiCustomerReceiveaddressPojo.getAddress());
            int j=logisticsInformationBusiServiceImpl.logisticsInformation(logisticsInformationPojo);
            if(j==0){
                throw new BusinessException("物流订阅异常");
            }
        }catch (Exception e){
            LoggerUtil.error(OrderBusiServiceImpl.class,e);
            throw new BusinessException("物流订阅异常");
        }
        return i;
    }

    @Override
    public int oneYuanSendOrder(Map<String, Object> map,Integer userId) throws BusinessException {
        int i = orderBusiMapper.updateOrder(map);
        map.put("onceOrderStatus", 2);
        if (i != 1) {
            throw new BusinessException("订单发货失败");
        }
        //操作记录
        map.put("actorId", userId);
        int j = orderBusiMapper.addOrderLog(map);
        if (j != 1) {
            throw new BusinessException("订单发货后增加操作记录失败");
        }
        Map<String, Object> orderParment = new HashMap<>();
        orderParment.put("orderId",map.get("orderId"));
        List<Map<String, Object>> list = orderInfoMapper.findOrder(orderParment);
        //通知
        List<Map<String, Object>> orderDetail = orderInfoMapper.findOrderDetail(Integer.valueOf(map.get("orderId")+""), null);
        Map<String, Object> jsonMap = new HashMap<>();
        BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
        busiMessageDetailPojo.setmType((short) 0);
        busiMessageDetailPojo.setmTitle("订单发货通知");
        jsonMap.put("state", "订单发货");
        jsonMap.put("orderNo", list.get(0).get("ordersNo"));
        jsonMap.put("companyName", map.get("companyName"));
        jsonMap.put("logisticNo", map.get("expressNo"));
        jsonMap.put("prodUrl", orderDetail.get(0).get("photoUrl"));
        jsonMap.put("orderId", map.get("orderId"));
        busiMessageDetailPojo.setmContent(CommonUtil.objectToJsonString(jsonMap));
        busiMessageDetailPojo.setMessageId(Integer.valueOf(list.get(0).get("customerId") + ""));
        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);

        //物流订阅
        try {
            LogisticsInformationPojo logisticsInformationPojo = new LogisticsInformationPojo();
            ExpressCodePojo expressCodePojo=new ExpressCodePojo();
            expressCodePojo.setExpressCompany(map.get("companyName")+"");
            expressCodePojo=expressCodeInfoServiceImpl.selectOne(expressCodePojo);
            logisticsInformationPojo.setLogisticeCode(map.get("expressNo")+"");
            logisticsInformationPojo.setExpressCode(expressCodePojo.getExpressCode());
            logisticsInformationPojo.setExpressCompany(map.get("companyName")+"");
            logisticsInformationPojo.setOrdersId(Integer.valueOf(map.get("orderId")+""));
            BusiShopPojo busiShopPojo=new BusiShopPojo();
            busiShopPojo.setId(Integer.valueOf(list.get(0).get("shopId")+""));
            busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
            logisticsInformationPojo.setSenderName(busiShopPojo.getShopName());
            logisticsInformationPojo.setSenderMobile(busiShopPojo.getShopPhone());
            BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
            busiShopCompanyPojo.setId(busiShopPojo.getCompanyinfoId());
            busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectOne(busiShopCompanyPojo);
            logisticsInformationPojo.setSenderProvince(areaModelUtils.getProvince(busiShopCompanyPojo.getRegionLocation(),false).getName());
            logisticsInformationPojo.setSenderCity(areaModelUtils.getCity(busiShopCompanyPojo.getCity(),false).getName());
            logisticsInformationPojo.setSenderArea( areaModelUtils.getCounty(busiShopCompanyPojo.getAreaCounty()).getName());
            logisticsInformationPojo.setSenderAddress(busiShopCompanyPojo.getAddress());
            BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
            busiCustomerReceiveaddressPojo.setId(Integer.valueOf(list.get(0).get("receiveAddressId") + ""));
            busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
            logisticsInformationPojo.setReceiverName(busiCustomerReceiveaddressPojo.getReceiveName());
            logisticsInformationPojo.setReceiverMobile(busiCustomerReceiveaddressPojo.getPhone());
            logisticsInformationPojo.setReceiverProvince(busiCustomerReceiveaddressPojo.getRegionLocation());
            logisticsInformationPojo.setReceiverCity(busiCustomerReceiveaddressPojo.getCity());
            logisticsInformationPojo.setReceiverArea(busiCustomerReceiveaddressPojo.getAreaCounty());
            logisticsInformationPojo.setReceiverAddress(busiCustomerReceiveaddressPojo.getAddress());
            int k=logisticsInformationBusiServiceImpl.logisticsInformation(logisticsInformationPojo);
            if(k==0){
                throw new BusinessException("物流订阅失败");
            }
            return i;
        }catch (Exception e){
            LoggerUtil.error(OrderBusiServiceImpl.class,e);
            throw new BusinessException("物流订阅异常");
        }
    }

    /**
     * 生成U币兑换订单
     * @param map
     * @param
     * @return
     * @throws BusinessException
     */
    @Override
    public int convertOrderBuy(Map<String, Object> map) throws BusinessException {
        Map<String,Object> order=(Map)map.get("order");
        Map<String,Object> orderDetail=(Map)map.get("orderDetail");
        orderBusiMapper.addOrder(order);

        orderDetail.put("orderId", order.get("id"));

        List<Map<String,Object>> list=new ArrayList<>();
        list.add(orderDetail);
        orderBusiMapper.addOrderDetail(list);

        //扣除游戏币
        if(CommonUtil.isNotEmpty(order.get("rewardAmount"))&&new BigDecimal(order.get("rewardAmount")+"").compareTo(BigDecimal.ZERO)==1){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list2=new ArrayList<>();

            busiUpdateBankrollModel.setRewardAmount(new BigDecimal(order.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(order.get("customerId")+""));
            busiUpdateBankrollModel.setRewardAmountAddOrMinus((short)1);

            busiCustomerBankrollFlowModel.setFlowType((short)1);
            busiCustomerBankrollFlowModel.setFlowCategory((short)6);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.PAYGAMEAMOUNT.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(order.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiCustomerBankrollFlowModel.setFlowNumber(order.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(order.get("id")+""));
            list2.add(busiCustomerBankrollFlowModel);
            busiUpdateBankrollModel.setList(list2);
            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }

        //更新兑换商品库存
        Map<String,Object> productFresher=new HashMap<>();
        productFresher.put("id",orderDetail.get("prodId"));
        productFresher.put("number",0-Integer.valueOf(orderDetail.get("number")+""));
        int count=productFresherBusiServiceImpl.updateStorage(productFresher);
        if(count<1){
            throw new BusinessException("更新商品库存失败");
        }

        //更新兑换表状态
        Map<String,Object> parment=new HashMap<>();
        parment.put("id",order.get("productFresherRewardId"));
        parment.put("prodId",orderDetail.get("prodId"));
        parment.put("exchangeUPrice",new BigDecimal(order.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
        parment.put("exchangeTime",new Date());
        int i=productFresherRewardBusiServiceImpl.updateStatus(parment);
        if(i!=1){
            throw new BusinessException("更新兑换表失败");
        }
        return Integer.valueOf(order.get("id")+"");
    }

    @Override
    public int activityOrderBuy(Map<String, Object> map) throws BusinessException {
        Map<String,Object> order=(Map)map.get("order");
        Map<String,Object> orderDetail=(Map)map.get("orderDetail");
        orderBusiMapper.addOrder(order);

        orderDetail.put("orderId", order.get("id"));

        List<Map<String,Object>> list=new ArrayList<>();
        list.add(orderDetail);
        orderBusiMapper.addOrderDetail(list);

        //扣除星币，减库存
        if(new BigDecimal(order.get("integralAmount")+"").compareTo(BigDecimal.ZERO)==1){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list1=new ArrayList<>();

            busiUpdateBankrollModel.setStarCoin(new BigDecimal(order.get("integralAmount")+""));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(order.get("customerId")+""));
            busiUpdateBankrollModel.setStarCoinAddOrMinus((short)1);

            busiCustomerBankrollFlowModel.setFlowType((short)1);
            busiCustomerBankrollFlowModel.setFlowCategory((short)3);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.STARCOINPAY.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(order.get("integralAmount")+""));
            busiCustomerBankrollFlowModel.setFlowNumber(order.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(order.get("id")+""));
            list1.add(busiCustomerBankrollFlowModel);

            busiUpdateBankrollModel.setList(list1);

            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }

        //扣除游戏币
        if(CommonUtil.isNotEmpty(order.get("rewardAmount"))&&new BigDecimal(order.get("rewardAmount")+"").compareTo(BigDecimal.ZERO)==1){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list2=new ArrayList<>();

            busiUpdateBankrollModel.setRewardAmount(new BigDecimal(order.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(order.get("customerId")+""));
            busiUpdateBankrollModel.setRewardAmountAddOrMinus((short)1);

            busiCustomerBankrollFlowModel.setFlowType((short)1);
            busiCustomerBankrollFlowModel.setFlowCategory((short)6);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.PAYGAMEAMOUNT.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(order.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiCustomerBankrollFlowModel.setFlowNumber(order.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(order.get("id")+""));
            list2.add(busiCustomerBankrollFlowModel);
            busiUpdateBankrollModel.setList(list2);
            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }

        //更新兑换商品库存
        Map<String,Object> productFresher=new HashMap<>();
        productFresher.put("id",orderDetail.get("prodId"));
        productFresher.put("number",0-Integer.valueOf(orderDetail.get("number")+""));
        int count=activityProductBusiServiceImpl.updateStorage(productFresher);
        if(count<1){
            throw new BusinessException("更新商品库存失败");
        }



        return Integer.valueOf(order.get("id")+"");
    }

    /**
     * 取消未付款订单
     * @param orderMap
     * @param updateMap
     * @param orderLog
     * @param productDetail
     */
    @Override
    public void updateCancelNonPayment(Map orderMap, Map updateMap, Map orderLog, Map productDetail) throws BusinessException{
        int updateOrder=orderBusiMapper.updateOrder(updateMap);
        int addOrderLog=orderBusiMapper.addOrderLog(orderLog);
        int updatePDStorage=0;
        if(orderMap.get("isGameOrder").toString().equals("0") || orderMap.get("isGameOrder").toString().equals("1")){
            updatePDStorage=productDetailBusiServiceImpl.updatePDStorage(productDetail);
        }
        //判断该订单是否已经部分消费卷抵扣
        BigDecimal integralAmount = (BigDecimal) orderMap.get("integralAmount");
        if(integralAmount.compareTo(BigDecimal.ZERO) == 1){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

            busiUpdateBankrollModel.setStarCoin(new BigDecimal(orderMap.get("integralAmount")+""));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderMap.get("customerId")+""));
            busiUpdateBankrollModel.setStarCoinAddOrMinus((short)0);

            busiCustomerBankrollFlowModel.setFlowType((short)0);
            busiCustomerBankrollFlowModel.setFlowCategory((short)3);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.STARCOINPAYBACK.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(orderMap.get("integralAmount")+""));
            busiCustomerBankrollFlowModel.setFlowNumber(orderMap.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderMap.get("orderId")+""));
            list.add(busiCustomerBankrollFlowModel);
            busiUpdateBankrollModel.setList(list);
            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }

        //补回扣除的游戏币
        if(CommonUtil.isNotEmpty(orderMap.get("rewardAmount"))&&(new BigDecimal(orderMap.get("rewardAmount")+"").compareTo(BigDecimal.ZERO)==1)){
            BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
            List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

            busiUpdateBankrollModel.setRewardAmount(new BigDecimal(orderMap.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderMap.get("customerId")+""));
            busiUpdateBankrollModel.setRewardAmountAddOrMinus((short)0);

            busiCustomerBankrollFlowModel.setFlowType((short)0);
            busiCustomerBankrollFlowModel.setFlowCategory((short)6);
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.GAMEAMONUTBACK.getValue());
            busiCustomerBankrollFlowModel.setAmount(new BigDecimal(orderMap.get("rewardAmount")+"").divide(sysPropertiesUtils.getDecimalValue("uRatio")));
            busiCustomerBankrollFlowModel.setFlowNumber(orderMap.get("ordersNo")+"");
            busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderMap.get("orderId")+""));
            list.add(busiCustomerBankrollFlowModel);

            busiUpdateBankrollModel.setList(list);

            String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);

            if(CommonUtil.isEmpty(id)){
                throw new BusinessException("添加账户流水失败");
            }
        }
        if(orderMap.get("isGameOrder").toString().equals("0") || orderMap.get("isGameOrder").toString().equals("1")){
            if(updateOrder!=1 || addOrderLog!=1 || updatePDStorage!=2){
                throw new BusinessException("订单ID为"+updateMap.get("orderId")+"的订单取消失败");
            }
        }else{
            if(updateOrder!=1 || addOrderLog!=1){
                throw new BusinessException("订单ID为"+updateMap.get("orderId")+"的订单取消失败");
            }
        }
    }
    @Override
    public void goodComment(EvaluateModel evaluateModel, Map orderLog, Map updateMap)throws BusinessException{
        int Order=orderBusiMapper.updateOrder(updateMap);
        int Log=orderBusiMapper.addOrderLog(orderLog);
        int evaluate=evaluateBusiMapper.autoEvaluate(evaluateModel);
        if(Order!=1 || Log!=1 || evaluate!=1){
            throw new BusinessException("订单ID为"+updateMap.get("orderId")+"的订单自动评论失败");
        }
    }

    @Override
    public void takeDelivery(Map updateMap, Map orderLog) throws BusinessException{
        /*if (CommonUtil.isNotEmpty(customerMap.get("promotionPrice"))) {
            BigDecimal promotionPriceMap = (BigDecimal) customerMap.get("promotionPrice");
            if (CommonUtil.isNotEmpty(customerMap.get("promotionUser")) && promotionPriceMap.compareTo(BigDecimal.ZERO) == 1) {
                //更新推荐用户资金表 流水记录
                BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
                BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
                List<BusiCustomerBankrollFlowModel> list = new ArrayList<>();
                busiUpdateBankrollModel.setAmount(new BigDecimal(customerMap.get("promotionPrice").toString()));
                busiUpdateBankrollModel.setCustomerId(Integer.valueOf(customerMap.get("promotionUser").toString()));
                busiUpdateBankrollModel.setAmountAddOrMinus((short) 0);
                busiCustomerBankrollFlowModel.setFlowType((short) 0);
                busiCustomerBankrollFlowModel.setFlowCategory((short) 1);
                busiCustomerBankrollFlowModel.setFlowDescription((short) 15);
                busiCustomerBankrollFlowModel.setAmount(new BigDecimal(customerMap.get("promotionPrice").toString()));
                busiCustomerBankrollFlowModel.setFlowNumber(customerMap.get("ordersNo").toString());
                busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderLog.get("orderId").toString()));
                list.add(busiCustomerBankrollFlowModel);
                busiUpdateBankrollModel.setList(list);
                String id = customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                if (CommonUtil.isEmpty(id)) {
                    throw new BusinessException("添加账户流水失败");
                }
            }
        }
*/
        int Order = orderBusiMapper.updateOrder(updateMap);
        int Log = orderBusiMapper.addOrderLog(orderLog);
        //收获后增加商品销量
        //int sales = orderBusiMapper.updateSales(salesMap);
        if (Order != 1 || Log != 1) {
            throw new BusinessException("订单ID为" + updateMap.get("orderId") + "的订单自动收货失败");
        }
    }

    @Override
    public void payOrder(List<Map<String, Object>> list,Integer payType) throws BusinessException {
        SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=new HashMap<>();
            //游戏商品消费券支付通知
            List<Map<String,Object>> orderDetail=orderInfoMapper.findOrderDetail(Integer.valueOf(list.get(i).get("id")+""),null);
            if(Integer.valueOf(list.get(i).get("isGameOrder")+"")==1){
                map.put("orderStatus",4);
                map.put("isRebate",1);
            }else{
                map.put("orderStatus",2);
            }
            map.put("orderId",list.get(i).get("id"));
            map.put("payTime",new Date());
            map.put("payType",payType);



            if(payType==1||payType==2){
                //查询账户ID
                BusiCustomerBankrollPojo busiCustomerBankrollPojo=customerBankrollInfoServiceImpl.selectByCustomerId(Integer.valueOf(list.get(i).get("customerId")+""));
                //加到流水表
                BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo=new BusiCustomerBankrollFlowPojo();
                busiCustomerBankrollFlowPojo.setBankrollId(busiCustomerBankrollPojo.getId());
                busiCustomerBankrollFlowPojo.setAmount(new BigDecimal(list.get(i).get("payAmount")+"").multiply(new BigDecimal("100")));
                busiCustomerBankrollFlowPojo.setFlowType((short)1);
                if(payType==1){
                    busiCustomerBankrollFlowPojo.setFlowDescription(BankrollInfoEnum.ALIPAY.getValue());
                    busiCustomerBankrollFlowPojo.setFlowCategory((short)5);
                }else{
                    busiCustomerBankrollFlowPojo.setFlowDescription(BankrollInfoEnum.WECHATPAY.getValue());
                    busiCustomerBankrollFlowPojo.setFlowCategory((short)4);
                }
                busiCustomerBankrollFlowPojo.setFlowNumber(list.get(i).get("ordersNo")+"");
                busiCustomerBankrollFlowPojo.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                customerBankrollFlowBusiServiceImpl.insertSelective(busiCustomerBankrollFlowPojo);
            }else if(payType==4){
                //余额支付改变账户余额
                BusiUpdateBankrollModel busiUpdateBankrollModel1=new BusiUpdateBankrollModel();
                BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel1=new BusiCustomerBankrollFlowModel();
                List<BusiCustomerBankrollFlowModel> listBankroll1=new ArrayList<>();
                busiUpdateBankrollModel1.setAmount(new BigDecimal(list.get(i).get("payAmount")+""));
                busiUpdateBankrollModel1.setCustomerId(Integer.valueOf(list.get(i).get("customerId")+""));
                busiUpdateBankrollModel1.setAmountAddOrMinus((short)1);
                busiCustomerBankrollFlowModel1.setFlowType((short)1);
                busiCustomerBankrollFlowModel1.setFlowCategory((short)1);
                busiCustomerBankrollFlowModel1.setFlowDescription(BankrollInfoEnum.AMOUNTPAY.getValue());
                busiCustomerBankrollFlowModel1.setAmount(new BigDecimal(list.get(i).get("payAmount")+""));
                busiCustomerBankrollFlowModel1.setFlowNumber(list.get(i).get("ordersNo")+"");
                busiCustomerBankrollFlowModel1.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                listBankroll1.add(busiCustomerBankrollFlowModel1);
                busiUpdateBankrollModel1.setList(listBankroll1);
                customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel1);
            }

            if(Integer.valueOf(list.get(i).get("isGameOrder")+"")==1){
                GameBusinessModel gameBusinessModel=new GameBusinessModel();
                gameBusinessModel.setTradeNo(list.get(i).get("ordersNo")+"");
                gameBusinessModel.setAmount(new BigDecimal(list.get(i).get("totalAmount")+"").multiply(new BigDecimal("100")));
                gameBusinessModel.setCustomerId(list.get(i).get("customerId")+"");
                gameBusinessModel.setGameId(Integer.valueOf(list.get(i).get("gameId")+""));
                gameBusinessModel.setNum(new BigDecimal(list.get(i).get("roomCardNum")+"").intValue());
                if(payType==1){
                    gameBusinessModel.setTradeDesc("支付宝支付游戏订单，其中支付宝支付金额"+list.get(i).get("payAmount")+"元");
                }else if(payType==2){
                    gameBusinessModel.setTradeDesc("微信支付游戏订单，其中微信支付金额"+list.get(i).get("payAmount")+"元");
                }else{
                    gameBusinessModel.setTradeDesc("余额支付游戏订单，其中余额支付金额"+list.get(i).get("payAmount")+"元");
                }
                gameBusinessModel.setPaymentTime(sf.format(new Date()));
                gameBusinessModel.setTradeType(sysPropertiesUtils.getIntValue("productType"));
                String resultMessage=gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);
                if(!resultMessage.equals("success")){
                    map.put("gameNotifyResult",1);
                }else{
                    map.put("gameNotifyResult",0);
                }


                //合伙人反余额
                if(CommonUtil.isNotEmpty(list.get(i).get("partnerId"))){
                    BusiUpdateBankrollModel busiUpdateBankrollModel1=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel1=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> listBankroll1=new ArrayList<>();
                    busiUpdateBankrollModel1.setAmount(new BigDecimal(list.get(i).get("partnerAmount")+""));
                    busiUpdateBankrollModel1.setGameId(Integer.valueOf(list.get(i).get("partnerId")+""));
                    busiUpdateBankrollModel1.setAmountAddOrMinus((short)0);
                    busiCustomerBankrollFlowModel1.setFlowType((short)0);
                    busiCustomerBankrollFlowModel1.setFlowCategory((short)1);
                    busiCustomerBankrollFlowModel1.setFlowDescription(BankrollInfoEnum.PARTNERGIVE.getValue());
                    busiCustomerBankrollFlowModel1.setAmount(new BigDecimal(list.get(i).get("partnerAmount")+""));
                    busiCustomerBankrollFlowModel1.setFlowNumber(list.get(i).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel1.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                    busiCustomerBankrollFlowModel1.setCreateId(Integer.valueOf(list.get(i).get("customerId")+""));
                    listBankroll1.add(busiCustomerBankrollFlowModel1);
                    busiUpdateBankrollModel1.setList(listBankroll1);
                    String id1=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel1);
                    if(CommonUtil.isEmpty(id1)){
                        throw new BusinessException("合伙人返余额失败");
                    }
                }

                //首次购买游戏商品金额大于等于300生成兑换券
                if(new BigDecimal(list.get(i).get("totalAmount")+"").compareTo(new BigDecimal(300))!=-1){
                    //查询是否有兑换资格
                    VoucherlPojo voucherlPojo=new VoucherlPojo();
                    voucherlPojo.setCustId(Integer.valueOf(list.get(i).get("customerId")+""));
                    voucherlPojo.setDelFlag((short)0);
                    voucherlPojo.setType((short)1);
                    voucherlPojo=voucherInfoServiceImpl.selectOne(voucherlPojo);
                    if(CommonUtil.isEmpty(voucherlPojo)){
                        voucherlPojo=new VoucherlPojo();
                        voucherlPojo.setCustId(Integer.valueOf(list.get(i).get("customerId")+""));
                        voucherlPojo.setStatus((short)0);
                        voucherlPojo.setType((short)1);
                        voucherlPojo.setName(sysPropertiesUtils.getStringValue("voucherName"));
                        voucherlPojo.setLink(sysPropertiesUtils.getStringValue("voucherLink"));
                        int num=voucherBusiServiceImpl.add(voucherlPojo);
                        if(num<1){
                            throw new BusinessException("生成兑换券失败");
                        }
                    }
                    //
                }

                //会员反余额
                /*if(CommonUtil.isNotEmpty(list.get(i).get("promotionUser"))&&new BigDecimal(list.get(i).get("promotionPrice")+"").compareTo(BigDecimal.ZERO)==1){
                    BusiUpdateBankrollModel busiUpdateBankrollModel2=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel2=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> listBankroll2=new ArrayList<>();
                    busiUpdateBankrollModel2.setAmount(new BigDecimal(list.get(i).get("promotionPrice")+""));
                    busiUpdateBankrollModel2.setCustomerId(Integer.valueOf(list.get(i).get("promotionUser")+""));
                    busiUpdateBankrollModel2.setAmountAddOrMinus((short)0);
                    busiCustomerBankrollFlowModel2.setFlowType((short)0);
                    busiCustomerBankrollFlowModel2.setFlowCategory((short)1);
                    busiCustomerBankrollFlowModel2.setFlowDescription(BankrollInfoEnum.MEMBERGIVE.getValue());
                    busiCustomerBankrollFlowModel2.setAmount(new BigDecimal(list.get(i).get("promotionPrice")+""));
                    busiCustomerBankrollFlowModel2.setFlowNumber(list.get(i).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel2.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                    busiCustomerBankrollFlowModel2.setCreateId(Integer.valueOf(list.get(i).get("customerId")+""));
                    listBankroll2.add(busiCustomerBankrollFlowModel2);
                    busiUpdateBankrollModel2.setList(listBankroll2);
                    String id2=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel2);
                    if(CommonUtil.isEmpty(id2)){
                        throw new BusinessException("会员返余额失败");
                    }
                }*/

                //推荐人的推荐人
                /*if(CommonUtil.isNotEmpty(list.get(i).get("upgradePromotionUser"))&&new BigDecimal(list.get(i).get("upgradePromotionPrice")+"").compareTo(BigDecimal.ZERO)==1){
                    BusiUpdateBankrollModel busiUpdateBankrollModel2=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel2=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> listBankroll2=new ArrayList<>();
                    busiUpdateBankrollModel2.setAmount(new BigDecimal(list.get(i).get("upgradePromotionPrice")+""));
                    busiUpdateBankrollModel2.setCustomerId(Integer.valueOf(list.get(i).get("upgradePromotionUser")+""));
                    busiUpdateBankrollModel2.setAmountAddOrMinus((short)0);
                    busiCustomerBankrollFlowModel2.setFlowType((short)0);
                    busiCustomerBankrollFlowModel2.setFlowCategory((short)1);
                    busiCustomerBankrollFlowModel2.setFlowDescription(BankrollInfoEnum.SECONDMEMBERGIVE.getValue());
                    busiCustomerBankrollFlowModel2.setAmount(new BigDecimal(list.get(i).get("upgradePromotionPrice")+""));
                    busiCustomerBankrollFlowModel2.setFlowNumber(list.get(i).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel2.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                    busiCustomerBankrollFlowModel2.setCreateId(Integer.valueOf(list.get(i).get("customerId")+""));
                    listBankroll2.add(busiCustomerBankrollFlowModel2);
                    busiUpdateBankrollModel2.setList(listBankroll2);
                    String id2=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel2);
                    if(CommonUtil.isEmpty(id2)){
                        throw new BusinessException("游戏订单推荐人的推荐人反余额失败");
                    }
                }*/

                //立即反消费券
                /*if(new BigDecimal(list.get(i).get("giveIntegral")+"").compareTo(BigDecimal.ZERO)==1){
                    BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> listBankroll=new ArrayList<>();
                    busiUpdateBankrollModel.setStarCoin(new BigDecimal(list.get(i).get("giveIntegral")+""));
                    busiUpdateBankrollModel.setCustomerId(Integer.valueOf(list.get(i).get("customerId")+""));
                    busiUpdateBankrollModel.setStarCoinAddOrMinus((short)0);
                    busiCustomerBankrollFlowModel.setFlowType((short)0);
                    busiCustomerBankrollFlowModel.setFlowCategory((short)3);
                    busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.GAMEADD.getValue());
                    busiCustomerBankrollFlowModel.setAmount(new BigDecimal(list.get(i).get("giveIntegral")+""));
                    busiCustomerBankrollFlowModel.setFlowNumber(list.get(i).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                    listBankroll.add(busiCustomerBankrollFlowModel);
                    busiUpdateBankrollModel.setList(listBankroll);
                    String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                    if(CommonUtil.isEmpty(id)){
                        throw new BusinessException("反消费券失败");
                    }

                    //返利记录
                    RebatePojo rebatePojo=new RebatePojo();
                    rebatePojo.setCustomerId(Integer.valueOf(list.get(i).get("customerId")+""));
                    rebatePojo.setOrderAmount(new BigDecimal(list.get(i).get("totalAmount")+""));
                    rebatePojo.setRecordNo(list.get(i).get("ordersNo")+"");
                    rebatePojo.setYokaAmount(new BigDecimal(list.get(i).get("giveIntegral")+""));
                    int youka=rebateBusiServiceImpl.add(rebatePojo);
                    if(youka<1){
                        throw new BusinessException("反消费券添加记录失败");
                    }
                }*/

            }
            //更新订单信息
            int h=orderBusiMapper.updateOrder(map);
            //操作记录
            map.put("onceOrderStatus",list.get(i).get("orderStatus"));
            map.put("actorId",list.get(i).get("customerId"));
            orderBusiMapper.addOrderLog(map);
            if(h!=1){
                throw new BusinessException("订单ID为"+list.get(i).get("id")+"的订单，修改状态失败");
            }
            //销量增加
            /*if(Integer.valueOf(list.get(i).get("isGameOrder")+"")<2){
                for(Map orderDetailMap:orderDetail){
                    map.put("productId",orderDetailMap.get("prodId"));
                    map.put("number",orderDetailMap.get("number"));
                    orderBusiMapper.updateSales(map);
                }
            }*/
            if(Integer.valueOf(list.get(i).get("isGameOrder")+"")==3){
                OneYuanOrderPojo oneYuanOrderPojo=new OneYuanOrderPojo();
                oneYuanOrderPojo.setType((short)1);
                oneYuanOrderPojo.setOutsideId(Integer.valueOf(orderDetail.get(0).get("prodId")+""));
                oneYuanOrderPojo.setCustomerId(Integer.valueOf(list.get(i).get("customerId")+""));
                oneYuanOrderBusiServiceImpl.add(oneYuanOrderPojo);
            }
            if(Integer.valueOf(list.get(i).get("isGameOrder")+"")==2){
                OneYuanOrderPojo oneYuanOrderPojo=new OneYuanOrderPojo();
                oneYuanOrderPojo.setType((short)2);
                oneYuanOrderPojo.setOutsideId(Integer.valueOf(orderDetail.get(0).get("prodId")+""));
                oneYuanOrderPojo.setCustomerId(Integer.valueOf(list.get(i).get("customerId")+""));
                oneYuanOrderBusiServiceImpl.add(oneYuanOrderPojo);
                //推荐人反余额
                if(CommonUtil.isNotEmpty(list.get(i).get("promotionUser"))){
                    BigDecimal memberBackAmount=sysPropertiesUtils.getDecimalValue("memberBackAmount");
                    BusiUpdateBankrollModel busiUpdateBankrollModel2=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel2=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> listBankroll2=new ArrayList<>();
                    busiUpdateBankrollModel2.setAmount(memberBackAmount);
                    busiUpdateBankrollModel2.setGameId(Integer.valueOf(list.get(i).get("promotionUser")+""));
                    busiUpdateBankrollModel2.setAmountAddOrMinus((short)0);
                    busiCustomerBankrollFlowModel2.setFlowType((short)0);
                    busiCustomerBankrollFlowModel2.setFlowCategory((short)1);
                    busiCustomerBankrollFlowModel2.setFlowDescription(BankrollInfoEnum.RECOMMENDAMOUNT.getValue());
                    busiCustomerBankrollFlowModel2.setAmount(memberBackAmount);
                    busiCustomerBankrollFlowModel2.setFlowNumber(list.get(i).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel2.setRelationId(Integer.valueOf(list.get(i).get("id")+""));
                    busiCustomerBankrollFlowModel2.setCreateId(Integer.valueOf(list.get(i).get("customerId")+""));
                    listBankroll2.add(busiCustomerBankrollFlowModel2);
                    busiUpdateBankrollModel2.setList(listBankroll2);
                    String id2=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel2);
                    if(CommonUtil.isEmpty(id2)){
                        throw new BusinessException("会员返余额失败");
                    }
                }
                //购买人设置成会员，生成推荐码
                /*customerBusiServiceImpl.updateMember(Integer.valueOf(list.get(i).get("customerId")+""));*/
                //房卡充值
                if(CommonUtil.isNotEmpty(orderDetail.get(0).get("roomCardPic"))&&Integer.valueOf(orderDetail.get(0).get("roomCardPic")+"")>0){
                    GameBusinessModel gameBusinessModel=new GameBusinessModel();
                    gameBusinessModel.setTradeNo(list.get(i).get("ordersNo")+"");
                    gameBusinessModel.setAmount(new BigDecimal(list.get(i).get("totalAmount")+"").multiply(new BigDecimal("100")));
                    gameBusinessModel.setCustomerId(list.get(i).get("customerId")+"");
                    gameBusinessModel.setGameId(Integer.valueOf(list.get(i).get("gameId")+""));
                    gameBusinessModel.setNum(new BigDecimal(orderDetail.get(0).get("roomCardPic")+"").intValue());
                    if(payType==1){
                        gameBusinessModel.setTradeDesc("支付宝支付游戏订单，其中支付宝支付金额"+list.get(i).get("payAmount")+"元");
                    }else if(payType==2){
                        gameBusinessModel.setTradeDesc("微信支付游戏订单，其中微信支付金额"+list.get(i).get("payAmount")+"元");
                    }else{
                        gameBusinessModel.setTradeDesc("余额支付游戏订单，其中余额支付金额"+list.get(i).get("payAmount")+"元");
                    }
                    gameBusinessModel.setPaymentTime(sf.format(new Date()));
                    gameBusinessModel.setTradeType(sysPropertiesUtils.getIntValue("productType"));
                    String resultMessage=gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);
                }
            }
        }
    }

    @Override
    public int updateSales(Map salesMap) {
        return orderBusiMapper.updateSales(salesMap);
    }    

	@Override
    public void updateAmount(Map<String,Object> customerMap) throws BusinessException{
        if (CommonUtil.isNotEmpty(customerMap.get("promotionUser")) && CommonUtil.isNotEmpty(customerMap.get("promotionPrice"))) {
            BigDecimal promotionPrice = (BigDecimal) customerMap.get("promotionPrice");
            if (CommonUtil.isNotEmpty(customerMap.get("promotionUser")) && promotionPrice.compareTo(BigDecimal.ZERO) == 1) {
                //更新推荐用户资金表 流水记录
                BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
                BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
                List<BusiCustomerBankrollFlowModel> list = new ArrayList<>();
                busiUpdateBankrollModel.setAmount(promotionPrice);
                busiUpdateBankrollModel.setCustomerId(Integer.valueOf(customerMap.get("promotionUser").toString()));
                busiUpdateBankrollModel.setAmountAddOrMinus((short) 0);
                busiCustomerBankrollFlowModel.setFlowType((short) 0);
                busiCustomerBankrollFlowModel.setFlowCategory((short) 1);
                busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.SPREADADD.getValue());
                busiCustomerBankrollFlowModel.setAmount(promotionPrice);
                busiCustomerBankrollFlowModel.setFlowNumber(customerMap.get("ordersNo").toString());
                busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(customerMap.get("ordersId").toString()));
                busiCustomerBankrollFlowModel.setCreateId(Integer.valueOf(customerMap.get("customerId").toString()));
                list.add(busiCustomerBankrollFlowModel);
                busiUpdateBankrollModel.setList(list);
                String id = customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                if(CommonUtil.isNotEmpty(id)){
                    if (CommonUtil.isNotEmpty(customerMap.get("upgradePromotionUser"))&& CommonUtil.isNotEmpty(customerMap.get("upgradePromotionPrice"))) {
                        BigDecimal promotionPriceMap = (BigDecimal) customerMap.get("upgradePromotionPrice");
                        if (CommonUtil.isNotEmpty(customerMap.get("upgradePromotionUser")) && promotionPriceMap.compareTo(BigDecimal.ZERO) == 1) {
                            //更新推荐用户资金表 流水记录
                            BusiUpdateBankrollModel busiUpdateBankroll = new BusiUpdateBankrollModel();
                            BusiCustomerBankrollFlowModel busiCustomerBankrollFlow = new BusiCustomerBankrollFlowModel();
                            List<BusiCustomerBankrollFlowModel> lists = new ArrayList<>();
                            busiUpdateBankroll.setAmount(new BigDecimal(customerMap.get("upgradePromotionPrice").toString()));
                            busiUpdateBankroll.setCustomerId(Integer.valueOf(customerMap.get("upgradePromotionUser").toString()));
                            busiUpdateBankroll.setAmountAddOrMinus((short) 0);
                            busiCustomerBankrollFlow.setFlowType((short) 0);
                            busiCustomerBankrollFlow.setFlowCategory((short) 1);
                            busiCustomerBankrollFlow.setFlowDescription(BankrollInfoEnum.SECONDSPREADADD.getValue());
                            busiCustomerBankrollFlow.setAmount(new BigDecimal(customerMap.get("upgradePromotionPrice").toString()));
                            busiCustomerBankrollFlow.setFlowNumber(customerMap.get("ordersNo").toString());
                            busiCustomerBankrollFlow.setRelationId(Integer.valueOf(customerMap.get("ordersId").toString()));
                            busiCustomerBankrollFlow.setCreateId(Integer.valueOf(customerMap.get("customerId").toString()));
                            lists.add(busiCustomerBankrollFlow);
                            busiUpdateBankroll.setList(lists);
                            String ids = customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankroll);
                            if (CommonUtil.isEmpty(ids)) {
                                throw new BusinessException("添加账户流水失败");
                            }
                        }
                    }
                }
                //修改订单返利状态
                int rebate= orderBusiMapper.updateOrderDetailRebate((Integer)customerMap.get("ordersId"));

                if (CommonUtil.isEmpty(id) || rebate !=1) {
                    throw new BusinessException("更新订单"+customerMap.get("orderDetailId")+"失败");
                }
            }
        }
    }

    @Override
    public int oneYuanBuy(Map<String, Object> map) {
        Map<String,Object> order=(Map)map.get("order");
        Map<String,Object> orderDetail=(Map)map.get("orderDetail");

        orderBusiMapper.addOrder(order);


        orderDetail.put("orderId", order.get("id"));

        List<Map<String,Object>> list=new ArrayList<>();
        list.add(orderDetail);
        int addOrderInfo=orderBusiMapper.addOrderDetail(list);
        return Integer.valueOf(order.get("id")+"");
    }    

	@Override
    public int updateOrderSettlementId(Map orderMap){
       return orderBusiMapper.updateOrderSettlementId(orderMap);
    }

    @Override
    public int addVoucherOrder(Map<String, Object> map) throws BusinessException{
        Map<String,Object> order=(Map)map.get("order");
        Map<String,Object> orderDetail=(Map)map.get("orderDetail");

        orderBusiMapper.addOrder(order);


        orderDetail.put("orderId", order.get("id"));

        List<Map<String,Object>> list=new ArrayList<>();
        list.add(orderDetail);
       orderBusiMapper.addOrderDetail(list);

        //更新兑换券表
        Map<String,Object> voucher=new HashMap<>();
        voucher.put("id",order.get("voucherId"));
        voucher.put("status",1);
        voucher.put("prodId",orderDetail.get("prodId"));
        int i=voucherBusiServiceImpl.updateStatus(voucher);
        if(i<1){
            throw new BusinessException("更新兑换券状态失败");
        }
        return Integer.valueOf(order.get("id")+"");
    }

}
