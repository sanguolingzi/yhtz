package com.yinhetianze.business.exchange.service.impl;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollFlowBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.business.exchange.mapper.busi.ExchangeBusiMapper;
import com.yinhetianze.business.exchange.service.ExchangeBusiService;
import com.yinhetianze.business.exchange.service.ExchangeOplogBusiService;
import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.order.ExchangeOplog;
import com.yinhetianze.pojo.order.ExchangePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = {BusinessException.class,RuntimeException.class})
public class ExchangeBusiServiceImpl implements ExchangeBusiService
{
    @Autowired
    private ExchangeBusiMapper mapper;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private ExchangeOplogBusiService exchangeOplogBusiServiceImpl;

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowBusiService customerBankrollFlowBusiServiceImpl;

    @Override
    public int addExchange(ExchangePojo exchangePojo) throws BusinessException{
        //图片上传到oss
        try{
            if(CommonUtil.isNotEmpty(exchangePojo.getExchangeImg())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String orderOssRootPath = sysPropertiesUtils.getStringValue("orderOssRootPath");
                String exchangeImagePath = sysPropertiesUtils.getStringValue("exchangeImagePath");
                String ossPath = orderOssRootPath+exchangeImagePath;
                String storeFilePath = fileUploadPath+ossPath;
                String key="";
                String[] exchangeList=exchangePojo.getExchangeImg().split(",");
                for(int i=0;i<exchangeList.length;i++){
                    LoggerUtil.info(ExchangeBusiServiceImpl.class, "help add read local file "+storeFilePath+ File.separatorChar+exchangeList[i]);
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath,exchangeList[i]);
                    //上传oss
                    String ossKey=ossFileUpload.fileUpload(file, ossPath);
                    if(ossKey == null){
                        throw new BusinessException("上传图片失败");
                    }
                    if(i==exchangeList.length-1){
                        key=key+ossKey;
                    }else{
                        key=key+ossKey+",";
                    }
                    //删除本地文件
                    file.delete();
                }
                exchangePojo.setExchangeImg(key);
            }
        }catch (Exception e){
            LoggerUtil.error(ExchangeBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("上传售后图片失败");
        }
        int i=mapper.insertSelective(exchangePojo);
        Map<String,Object> map=new HashMap<>();
        map.put("refundStatus",1);
        map.put("exchangeId",exchangePojo.getId());
        map.put("id",exchangePojo.getOrderDetailId());
        orderBusiServiceImpl.updateOrderDetail(map);
        return i;
    }

    @Override
    public int updateExchange(ExchangePojo exchangePojo) {
        return mapper.updateByPrimaryKey(exchangePojo);
    }

    @Override
    public int cancelExchange(ExchangePojo exchangePojo,Integer customerId) throws BusinessException{
        //更改订单状态，取消售后之后，订单表售后的状态refundStatus改为已取消
        Map<String,Object> map=new HashMap<>();
        if(CommonUtil.isNotEmpty(exchangePojo.getOrderDetailId())){
            map.put("id",exchangePojo.getOrderDetailId());
            map.put("refundStatus",4);
            orderBusiServiceImpl.updateOrderDetail(map);
        }else{
            map.put("ordersNo",exchangePojo.getOrderNo());
            String orderId=orderInfoMapper.findOrder(map).get(0).get("id")+"";
            map.put("orderId",orderId);
            map.put("orderStatus",2);
            int i=orderBusiServiceImpl.updateOrder(map);
            if(i!=1){
                throw new BusinessException("更改订单状态失败");
            }
            //操作记录
            map.put("onceOrderStatus",7);
            map.put("actorId",customerId);
            orderBusiServiceImpl.addOrderLog(map);
        }

        //售后单操作记录
        ExchangeOplog exchangeOplog=new ExchangeOplog();
        exchangeOplog.setActorId(customerId);
        exchangeOplog.setExchangeOrderId(exchangePojo.getId());
        exchangeOplog.setOnceExchangeOrderStatus((short)1);
        exchangeOplog.setExchangeOrderStatus(exchangePojo.getExchangeStatus());
        exchangeOplogBusiServiceImpl.add(exchangeOplog);
        //售后单状态
        return mapper.updateByPrimaryKeySelective(exchangePojo);
    }

    @Override
    public int checkExchange(ExchangePojo exchangePojo,Integer customerId) throws BusinessException{
        //退款审核
        if(exchangePojo.getExchangeType()==2){
            Map<String,Object> map=new HashMap<>();
            map.put("ordersNo",exchangePojo.getOrderNo());
            List<Map<String,Object>> orderList=orderInfoMapper.findOrder(map);
            map.put("orderId",orderList.get(0).get("id"));
            if(exchangePojo.getCheckState()==2){
                exchangePojo.setCompleteTime(new Date());
                exchangePojo.setUserId(customerId);

                map.put("orderStatus",8);
                exchangePojo.setRefundTotalAmount(new BigDecimal(orderList.get(0).get("payAmount")+"").add(new BigDecimal(orderList.get(0).get("integralAmount")+"")));
                //退星币
                if(new BigDecimal(orderList.get(0).get("integralAmount")+"").compareTo(BigDecimal.ZERO)==1){
                    exchangePojo.setRefundStarCoin(new BigDecimal(orderList.get(0).get("integralAmount")+""));
                    BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

                    busiUpdateBankrollModel.setStarCoin(new BigDecimal(orderList.get(0).get("integralAmount")+""));
                    busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderList.get(0).get("customerId")+""));
                    busiUpdateBankrollModel.setStarCoinAddOrMinus((short)0);

                    busiCustomerBankrollFlowModel.setFlowType((short)0);
                    busiCustomerBankrollFlowModel.setFlowCategory((short)3);
                    busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.STARCOINPAYBACK.getValue());
                    busiCustomerBankrollFlowModel.setAmount(new BigDecimal(orderList.get(0).get("integralAmount")+""));
                    busiCustomerBankrollFlowModel.setFlowNumber(orderList.get(0).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderList.get(0).get("id")+""));
                    list.add(busiCustomerBankrollFlowModel);

                    busiUpdateBankrollModel.setList(list);

                    String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);

                    if(CommonUtil.isEmpty(id)){
                        throw new BusinessException("添加账户流水失败");
                    }
                }
                //退款流水
                if(new BigDecimal(orderList.get(0).get("payAmount")+"").compareTo(BigDecimal.ZERO)==1) {
                    exchangePojo.setRefundAmount(new BigDecimal(orderList.get(0).get("payAmount")+""));
                    //退余额
                    if(Integer.valueOf(orderList.get(0).get("payType")+"")==4){
                        BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                        List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

                        busiUpdateBankrollModel.setAmount(new BigDecimal(orderList.get(0).get("payAmount")+""));
                        busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderList.get(0).get("customerId")+""));
                        busiUpdateBankrollModel.setAmountAddOrMinus((short)0);

                        busiCustomerBankrollFlowModel.setFlowType((short)0);
                        busiCustomerBankrollFlowModel.setFlowCategory((short)1);
                        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.AMOUNTPAYBACK.getValue());
                        busiCustomerBankrollFlowModel.setAmount(new BigDecimal(orderList.get(0).get("payAmount")+""));
                        busiCustomerBankrollFlowModel.setFlowNumber(orderList.get(0).get("ordersNo")+"");
                        busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderList.get(0).get("id")+""));
                        list.add(busiCustomerBankrollFlowModel);

                        busiUpdateBankrollModel.setList(list);

                        customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                    }else{
                        //查询账户ID
                        BusiCustomerBankrollPojo busiCustomerBankrollPojo=customerBankrollInfoServiceImpl.selectByCustomerId(Integer.valueOf(orderList.get(0).get("customerId")+""));
                        //加到流水表
                        BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo=new BusiCustomerBankrollFlowPojo();
                        busiCustomerBankrollFlowPojo.setBankrollId(busiCustomerBankrollPojo.getId());
                        busiCustomerBankrollFlowPojo.setAmount(new BigDecimal(orderList.get(0).get("payAmount")+"").multiply(new BigDecimal("100")));
                        busiCustomerBankrollFlowPojo.setFlowType((short)0);
                        if(Integer.valueOf(orderList.get(0).get("payType")+"")==1){
                            busiCustomerBankrollFlowPojo.setFlowDescription(BankrollInfoEnum.ALIBACK.getValue());
                            busiCustomerBankrollFlowPojo.setFlowCategory((short)5);
                        }else if(Integer.valueOf(orderList.get(0).get("payType")+"")==2){
                            busiCustomerBankrollFlowPojo.setFlowDescription(BankrollInfoEnum.WECHATBACK.getValue());
                            busiCustomerBankrollFlowPojo.setFlowCategory((short)4);
                        }
                        busiCustomerBankrollFlowPojo.setFlowNumber(orderList.get(0).get("ordersNo")+"");
                        busiCustomerBankrollFlowPojo.setRelationId(Integer.valueOf(orderList.get(0).get("id")+""));
                        int num=customerBankrollFlowBusiServiceImpl.insertSelective(busiCustomerBankrollFlowPojo);
                        if(num<1){
                            throw new BusinessException("添加退款流水失败");
                        }
                    }
                }
                //更新库存
                List<Map<String,Object>> orderDetail=orderInfoMapper.findOrderDetail(Integer.valueOf(map.get("orderId")+""),null);
                for(int j=0;j<orderDetail.size();j++){
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("prodId",orderDetail.get(j).get("prodId"));
                    map1.put("skuCode",orderDetail.get(j).get("prodSku"));
                    map1.put("number",Integer.valueOf(orderDetail.get(j).get("number")+""));
                    int k=productDetailBusiServiceImpl.updatePDStorage(map1);
                    if(k<2){
                        throw new BusinessException("商品更新库存失败");
                    }

                    //更新销量(减)
                   /* Map<String,Object> sale=new HashMap<>();
                    sale.put("productId",orderDetail.get(0).get("prodId"));
                    sale.put("number",0-Integer.valueOf(orderDetail.get(0).get("number")+""));
                    orderBusiServiceImpl.updateSales(sale);*/
                }
            }else{
                map.put("orderStatus",2);
            }
            orderBusiServiceImpl.updateOrder(map);
        }
        //退货审核
        if(exchangePojo.getExchangeType()==1){
            //卖家审核。checkState=3审核不通过，订单表售后的状态refundStatus改为已完成
            if(exchangePojo.getCheckState()==3){
                Map<String,Object> map=new HashMap<>();
                map.put("id",exchangePojo.getOrderDetailId());
                map.put("refundStatus",3);
                orderBusiServiceImpl.updateOrderDetail(map);
            }
        }

        //售后单操作记录
        ExchangeOplog exchangeOplog=new ExchangeOplog();
        exchangeOplog.setActorId(customerId);
        exchangeOplog.setExchangeOrderId(exchangePojo.getId());
        exchangeOplog.setOnceExchangeOrderStatus((short)1);
        exchangeOplog.setExchangeOrderStatus(exchangePojo.getExchangeStatus());
        exchangeOplogBusiServiceImpl.add(exchangeOplog);
        return mapper.updateByPrimaryKeySelective(exchangePojo);
    }

    @Override
    public int deliveryExchange(ExchangePojo exchangePojo, Integer customerId) {
        //售后单操作记录
        ExchangeOplog exchangeOplog=new ExchangeOplog();
        exchangeOplog.setActorId(customerId);
        exchangeOplog.setExchangeOrderId(exchangePojo.getId());
        exchangeOplog.setOnceExchangeOrderStatus((short)2);
        exchangeOplog.setExchangeOrderStatus(exchangePojo.getExchangeStatus());
        exchangeOplogBusiServiceImpl.add(exchangeOplog);
        return mapper.updateByPrimaryKeySelective(exchangePojo);
    }

    @Override
    public int collectExchange(ExchangePojo exchangePojo, Integer customerId) {
        //售后单操作记录
        ExchangeOplog exchangeOplog=new ExchangeOplog();
        exchangeOplog.setActorId(customerId);
        exchangeOplog.setExchangeOrderId(exchangePojo.getId());
        exchangeOplog.setOnceExchangeOrderStatus((short)3);
        exchangeOplog.setExchangeOrderStatus(exchangePojo.getExchangeStatus());
        exchangeOplogBusiServiceImpl.add(exchangeOplog);
        return mapper.updateByPrimaryKeySelective(exchangePojo);
    }

    @Override
    public int moneyExchange(ExchangePojo exchangePojo,Integer customerId) throws BusinessException{
        Map<String,Object> map=new HashMap<>();
        map.put("id",exchangePojo.getOrderDetailId());
        if(exchangePojo.getMoneyCheck()==2){
            //退款退星币
            Map<String,Object> parment=new HashMap<>();
            parment.put("ordersNo",exchangePojo.getOrderNo());
            List<Map<String,Object>> orderList=orderInfoMapper.findOrder(parment);
            if(exchangePojo.getRefundStarCoin().compareTo(BigDecimal.ZERO)==1){
                BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

                busiUpdateBankrollModel.setStarCoin(exchangePojo.getRefundStarCoin());
                busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderList.get(0).get("customerId")+""));
                busiUpdateBankrollModel.setStarCoinAddOrMinus((short)0);

                busiCustomerBankrollFlowModel.setFlowType((short)0);
                busiCustomerBankrollFlowModel.setFlowCategory((short)3);
                busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.STARCOINPAYBACK.getValue());
                busiCustomerBankrollFlowModel.setAmount(exchangePojo.getRefundStarCoin());
                busiCustomerBankrollFlowModel.setFlowNumber(orderList.get(0).get("ordersNo")+"");
                busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderList.get(0).get("id")+""));
                list.add(busiCustomerBankrollFlowModel);

                busiUpdateBankrollModel.setList(list);

                String id=customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);

                if(CommonUtil.isEmpty(id)){
                    throw new BusinessException("添加账户星币流水失败");
                }
            }

            if(exchangePojo.getRefundAmount().compareTo(BigDecimal.ZERO)==1){
                //退余额
                if(Integer.valueOf(orderList.get(0).get("payType")+"")==4){
                    BusiUpdateBankrollModel busiUpdateBankrollModel=new BusiUpdateBankrollModel();
                    BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel=new BusiCustomerBankrollFlowModel();
                    List<BusiCustomerBankrollFlowModel> list=new ArrayList<>();

                    busiUpdateBankrollModel.setAmount(exchangePojo.getRefundAmount());
                    busiUpdateBankrollModel.setCustomerId(Integer.valueOf(orderList.get(0).get("customerId")+""));
                    busiUpdateBankrollModel.setAmountAddOrMinus((short)0);

                    busiCustomerBankrollFlowModel.setFlowType((short)0);
                    busiCustomerBankrollFlowModel.setFlowCategory((short)1);
                    busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.AMOUNTPAYBACK.getValue());
                    busiCustomerBankrollFlowModel.setAmount(exchangePojo.getRefundAmount());
                    busiCustomerBankrollFlowModel.setFlowNumber(orderList.get(0).get("ordersNo")+"");
                    busiCustomerBankrollFlowModel.setRelationId(Integer.valueOf(orderList.get(0).get("id")+""));
                    list.add(busiCustomerBankrollFlowModel);

                    busiUpdateBankrollModel.setList(list);

                    customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                }else{
                    //查询账户ID
                    BusiCustomerBankrollPojo busiCustomerBankrollPojo=customerBankrollInfoServiceImpl.selectByCustomerId(Integer.valueOf(orderList.get(0).get("customerId")+""));
                    //加到流水表
                    BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo=new BusiCustomerBankrollFlowPojo();
                    busiCustomerBankrollFlowPojo.setBankrollId(busiCustomerBankrollPojo.getId());
                    busiCustomerBankrollFlowPojo.setAmount(exchangePojo.getRefundAmount().multiply(new BigDecimal("100")));
                    busiCustomerBankrollFlowPojo.setFlowType((short)0);
                    if(Integer.valueOf(orderList.get(0).get("payType")+"")==1){
                        busiCustomerBankrollFlowPojo.setFlowDescription(BankrollInfoEnum.ALIBACK.getValue());
                        busiCustomerBankrollFlowPojo.setFlowCategory((short)5);
                    }else if(Integer.valueOf(orderList.get(0).get("payType")+"")==2){
                        busiCustomerBankrollFlowPojo.setFlowDescription(BankrollInfoEnum.WECHATBACK.getValue());
                        busiCustomerBankrollFlowPojo.setFlowCategory((short)4);
                    }
                    busiCustomerBankrollFlowPojo.setFlowNumber(orderList.get(0).get("ordersNo")+"");
                    busiCustomerBankrollFlowPojo.setRelationId(Integer.valueOf(orderList.get(0).get("id")+""));
                    int num=customerBankrollFlowBusiServiceImpl.insertSelective(busiCustomerBankrollFlowPojo);
                    if(num<1){
                        throw new BusinessException("添加退款流水失败");
                    }
                }
            }
            //更新库存
            List<Map<String,Object>> orderDetail=orderInfoMapper.findOrderDetail(null,exchangePojo.getOrderDetailId());
            Map<String,Object> map1=new HashMap<>();
            map1.put("prodId",orderDetail.get(0).get("prodId"));
            map1.put("skuCode",orderDetail.get(0).get("prodSku"));
            map1.put("number",Integer.valueOf(orderDetail.get(0).get("number")+""));
            int k=productDetailBusiServiceImpl.updatePDStorage(map1);
            if(k<2){
                throw new BusinessException("商品ID为"+orderDetail.get(0).get("prodId")+"的商品更新库存失败");
            }
            map.put("refundStatus",2);

            //更新销量(减)
            /*Map<String,Object> sale=new HashMap<>();
            sale.put("productId",orderDetail.get(0).get("prodId"));
            sale.put("number",0-Integer.valueOf(orderDetail.get(0).get("number")+""));
            orderBusiServiceImpl.updateSales(sale);*/

        }else {
            map.put("refundStatus",3);
        }
        orderBusiServiceImpl.updateOrderDetail(map);
        //售后单操作记录
        ExchangeOplog exchangeOplog=new ExchangeOplog();
        exchangeOplog.setActorId(customerId);
        exchangeOplog.setExchangeOrderId(exchangePojo.getId());
        exchangeOplog.setOnceExchangeOrderStatus((short)4);
        exchangeOplog.setExchangeOrderStatus(exchangePojo.getExchangeStatus());
        exchangeOplogBusiServiceImpl.add(exchangeOplog);

        //更改退货单信息
        return mapper.updateByPrimaryKeySelective(exchangePojo);
    }
}