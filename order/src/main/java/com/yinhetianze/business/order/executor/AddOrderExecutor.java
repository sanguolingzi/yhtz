package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerReceiveaddressInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.business.order.model.OrderModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shoppingcart.service.ShopcartBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AddOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private ShopcartBusiService shopcartBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerReceiveaddressInfoService customerReceiveaddressInfoServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderModel orderModel=(OrderModel) model;
        //存入数据库倍率
        //BigDecimal ratio=new BigDecimal("100");
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }

        //查询推荐人ID
        /*BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo=customerRecommendRelationInfoServiceImpl.selectRecommendUser(tokenUser.getId());*/

        //校验用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }
        //BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        //校验账户积分(使用星币应小于账户星币)
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
        if (CommonUtil.isEmpty(busiCustomerBankrollPojo.getStarCoin())) {
            throw new BusinessException("没有获取到账户信息");
        }
        if(orderModel.getPayIntegral().compareTo(BigDecimal.ZERO)==-1){
            throw  new BusinessException("友旗币数量有误");
        }
        if(orderModel.getPayIntegral().compareTo(busiCustomerBankrollPojo.getStarCoin())==1){
            throw  new BusinessException("友旗币数量不足");
        }

        //查询收货地址
        BusiCustomerReceiveaddressPojo busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        busiCustomerReceiveaddressPojo.setId(orderModel.getReceiveaddressId());
        busiCustomerReceiveaddressPojo.setCustomerId(tokenUser.getId());
        busiCustomerReceiveaddressPojo = customerReceiveaddressInfoServiceImpl.selectOne(busiCustomerReceiveaddressPojo);
        if(CommonUtil.isEmpty(busiCustomerReceiveaddressPojo)){
            throw new BusinessException("收货地址无效");
        }


        //发票信息
        if(CommonUtil.isNotEmpty(orderModel.getTaxType())){
            if(CommonUtil.isEmpty(orderModel.getReceiptType())&&orderModel.getTaxType()!=0){
                throw new BusinessException("开票方式不能为空");
            }
            if(orderModel.getTaxType()==2&&(CommonUtil.isEmpty(orderModel.getTaxNo())||CommonUtil.isEmpty(orderModel.getTaxCompany()))){
                throw new BusinessException("公司开发票时税号和公司名称不能为空");
            }
        }

        //商品总金额
        BigDecimal totalAmount=new BigDecimal("0");
        //累计使用友旗币数量
        BigDecimal usePoints=new BigDecimal("0");
        //累计支付金额
        BigDecimal useAmounts=new BigDecimal("0");
        //抵扣的B币的金额
        BigDecimal sharePrice=new BigDecimal("0");
        //生成订单的参数
        List<Map<String,Object>> parameterList=new ArrayList<>();
        String totalOrderNo = CommonUtil.getSerialnumber();
        for(int i=0;i<orderModel.getShopList().size();i++){
            String ordersNo=CommonUtil.getSerialnumber();
            //订单的信息
            Map<String,Object> order=new HashMap<>();
            order.put("customerId",tokenUser.getId());
            order.put("orderType",orderModel.getOrderType());
            order.put("orderSourse",orderModel.getOrderSourse());
            if(CommonUtil.isNotEmpty(orderModel.getReceiveaddressId())){
                order.put("receiveAddressId",orderModel.getReceiveaddressId());
                order.put("receiver",busiCustomerReceiveaddressPojo.getReceiveName());
                order.put("address",busiCustomerReceiveaddressPojo.getRegionLocation()+busiCustomerReceiveaddressPojo.getCity()+busiCustomerReceiveaddressPojo.getAreaCounty()+busiCustomerReceiveaddressPojo.getAddress());
                order.put("phone",busiCustomerReceiveaddressPojo.getPhone());
                order.put("addressDefault",busiCustomerReceiveaddressPojo.getDefaultStatus());
            }else{
                order.put("receiver",busiCustomerPojo.getNickName());
                order.put("phone",busiCustomerPojo.getPhone());
            }
            //存放订单和订详情的map
            Map<String,Object> parameterOrder=new HashMap<>();

            order.put("ordersNo",ordersNo);
            order.put("proxyShopId",orderModel.getShopList().get(i).get("shopId"));

            //校验 商家不能买自己店铺的商品
            /*if(CommonUtil.isNotEmpty(shopPojo)){
                if(shopPojo.getId()==Integer.valueOf(orderModel.getShopList().get(i).get("shopId")+"")){
                    throw new BusinessException("商家不能购买买自己店铺的商品");
                }
            }*/

            //一个订单总金额
            BigDecimal totalAmounts=new BigDecimal("0");
            //一个订单使用积分
            BigDecimal payIntegrals=new BigDecimal("0");
            //一个订单商品总数
            BigDecimal count=new BigDecimal("0");
            //一个订单便宜金额
            BigDecimal discount=new BigDecimal("0");
            //一个订单商品总金额
            BigDecimal proAmount=new BigDecimal("0");
            //一个订单总运费
            BigDecimal freight=new BigDecimal(orderModel.getShopList().get(i).get("freight")+"");

            //一个订单赠送总积分
            BigDecimal giveIntegral=new BigDecimal("0");

            List<Map<String,Object>> list=(List<Map<String,Object>>)orderModel.getShopList().get(i).get("proList");
            //订单详情
            List<Map<String,Object>> orderList=new ArrayList<>();
            //是否为游戏商品0不是
            int isGameOrder=0;
            //推广价
            BigDecimal promotionPrice=new BigDecimal("0");

            BigDecimal settlementAmount=new BigDecimal("0");

            //一个订单使用的U币
            BigDecimal gameAmounts=new BigDecimal("0");
            //一个订单的支付金额
            BigDecimal usePayAmount=new BigDecimal("0");
            if(CommonUtil.isNotEmpty(list)){
                int shopId=0;
                for(int j=0;j<list.size();j++){
                    BigDecimal number=new BigDecimal(list.get(j).get("number")+"");
                    Map<String,Object> orderDetail=new HashMap<>();
                    ProductPojo prodPojo = new ProductPojo();
                    prodPojo.setId(Integer.valueOf(list.get(j).get("prodId")+""));
                    prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                    if (CommonUtil.isEmpty(prodPojo)){
                        throw new BusinessException("商品信息不存在");
                    }
                    shopId=prodPojo.getShopId();

                    //第一个是否为游戏商品
                    /*if(j==0&&prodPojo.getProductDistinction()==0&&CommonUtil.isEmpty(orderModel.getReceiveaddressId())){
                        throw new BusinessException("收货地址不能为空");
                    }*/
                    Map<String, Object> detailParam = new HashMap<>();
                    detailParam.put("prodId",list.get(j).get("prodId")+"");
                    detailParam.put("skuCode", list.get(j).get("prodSku"));
                    detailParam.put("delFlag", 0); // 没有删除的标记
                    detailParam.put("status", 0); // 有效状态0
                    // 商品详情信息单品列表
                    List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
                    if(CommonUtil.isEmpty(detailList)){
                        throw new BusinessException("商品规格信息不存在");
                    }

                    //判断库存
                    if(Integer.valueOf(detailList.get(0).get("storage")+"")<Integer.valueOf(number+"")){
                        throw new BusinessException("商品库存不足");
                    }

                    /*if(CommonUtil.isNotEmpty(detailList.get(0).get("promotionPrice"))){
                        promotionPrice=promotionPrice.add(new BigDecimal(detailList.get(0).get("promotionPrice")+"").multiply(number));
                    }*/

                    orderDetail.put("uPrice",detailList.get(0).get("uPrice"));
                    //商品使用的U币
                    if(CommonUtil.isNotEmpty(detailList.get(0).get("uPrice"))&&CommonUtil.isNotEmpty(list.get(j).get("gameAmount"))){
                        BigDecimal gameAmount=number.multiply(new BigDecimal(detailList.get(0).get("uPrice")+""));
                        if(gameAmount.compareTo(new BigDecimal(list.get(j).get("gameAmount")+""))==-1){
                            throw new BusinessException("使用游戏币不能大于商品可使用的数量");
                        }
                        if(new BigDecimal(list.get(j).get("gameAmount")+"").compareTo(BigDecimal.ZERO)==-1){
                            throw new BusinessException("使用游戏币不能小于0");
                        }
                        gameAmounts=gameAmounts.add(new BigDecimal(list.get(j).get("gameAmount")+""));
                        sharePrice=sharePrice.add(new BigDecimal(list.get(j).get("gameAmount")+""));
                        orderDetail.put("gameAmount",new BigDecimal(list.get(j).get("gameAmount")+""));
                    }

                    settlementAmount=settlementAmount.add(new BigDecimal(detailList.get(0).get("costPrice")+"").multiply(number));

                    count=count.add(number);
                    //一种商品销售总金额
                    BigDecimal sellAmount=new BigDecimal("0");
                    /*if((busiCustomerPojo.getIsMember()==0||busiCustomerPojo.getIsPartner()==0)&&CommonUtil.isNotEmpty(detailList.get(0).get("vipPrice"))){
                        sellAmount=new BigDecimal(detailList.get(0).get("vipPrice")+"");
                    }else{*/
                        sellAmount=new BigDecimal(detailList.get(0).get("sellPrice")+"");
                    /*}*/
                    BigDecimal amount=sellAmount.multiply(number);
                    //一种商品原价总金额
                    BigDecimal originalPrice=prodPojo.getMarketPrice().multiply(number);
                    discount=discount.add(originalPrice.subtract(amount));
                    proAmount=proAmount.add(originalPrice);
                    totalAmounts=totalAmounts.add(amount);
                    //一种商品使用积分
                    BigDecimal usePoint=new BigDecimal("0");
                    //一种商品支付金额
                    BigDecimal useAmount=new BigDecimal("0");
                    //如果支付金额小于支付友旗币，先算均分的支付金额
                    if(orderModel.getPayAmount().compareTo(orderModel.getPayIntegral())==-1){
                        if(i==(orderModel.getShopList().size()-1)&&j==(list.size()-1)){
                            useAmount=orderModel.getPayAmount().subtract(useAmounts);
                        }else{
                            if(orderModel.getPayAmount().compareTo(BigDecimal.ZERO)==0){
                                useAmount=BigDecimal.ZERO;
                            }else{
                                useAmount=amount.multiply(orderModel.getPayAmount()).divide(orderModel.getTotalAmount(),2, RoundingMode.HALF_UP);
                            }
                        }
                        useAmounts=useAmounts.add(useAmount);
                        usePayAmount=usePayAmount.add(useAmount);
                        if(CommonUtil.isNotEmpty(list.get(j).get("gameAmount"))){
                            usePoint=amount.subtract(new BigDecimal(list.get(j).get("gameAmount")+"").multiply(sysPropertiesUtils.getDecimalValue("uRatio"))).subtract(useAmount);
                        }else{
                            usePoint=amount.subtract(useAmount);
                        }
                        usePoints=usePoints.add(usePoint);
                        payIntegrals=payIntegrals.add(usePoint);
                    }else{
                        if(i==(orderModel.getShopList().size()-1)&&j==(list.size()-1)){
                            usePoint=orderModel.getPayIntegral().subtract(usePoints);
                        }else{
                            if(orderModel.getPayIntegral().compareTo(BigDecimal.ZERO)==0){
                                usePoint=BigDecimal.ZERO;
                            }else{
                                usePoint=amount.multiply(orderModel.getPayIntegral()).divide(orderModel.getTotalAmount(),2, RoundingMode.HALF_UP);
                            }
                        }
                        payIntegrals=payIntegrals.add(usePoint);
                        usePoints=usePoints.add(usePoint);
                        if(CommonUtil.isNotEmpty(list.get(j).get("gameAmount"))){
                            useAmount=amount.subtract(new BigDecimal(list.get(j).get("gameAmount")+"").multiply(sysPropertiesUtils.getDecimalValue("uRatio"))).subtract(usePoint);
                        }else{
                            useAmount=amount.subtract(usePoint);
                        }
                        useAmounts=useAmounts.add(useAmount);
                        usePayAmount=usePayAmount.add(useAmount);
                    }

                    orderDetail.put("prodId",list.get(j).get("prodId"));
                    orderDetail.put("prodSku",list.get(j).get("prodSku"));
                    orderDetail.put("prodTitle",prodPojo.getpTitle());
                    orderDetail.put("productName",prodPojo.getProdName());
                    orderDetail.put("photoUrl",prodPojo.getProductImg());
                    orderDetail.put("originalPrice",prodPojo.getMarketPrice());
                    orderDetail.put("sellPrice",sellAmount);
                    orderDetail.put("number",number);
                    orderDetail.put("productSpec",detailList.get(0).get("prodSpeci"));
                    orderDetail.put("payIntegral",usePoint);
                    orderDetail.put("integralAmount",usePoint);
                    orderDetail.put("productDistinction",prodPojo.getProductDistinction());
                    orderDetail.put("productType",prodPojo.getProductType());
                    orderDetail.put("finalPrice",detailList.get(0).get("costPrice"));
                    /*orderDetail.put("promotionPrice",new BigDecimal(detailList.get(0).get("promotionPrice")+"").multiply(number));
                    orderDetail.put("upgradePromotionPrice",new BigDecimal(detailList.get(0).get("promotionPrice")+"").multiply(number)
                            .multiply(sysPropertiesUtils.getDecimalValue("upgradePromotionPercent")).divide(new BigDecimal("100")));*/
                    orderList.add(orderDetail);
                }
              /*  if(discount.compareTo(BigDecimal.ZERO)==-1){
                    throw new BusinessException("商品市场价不能小于销售价");
                }*/
                //giveIntegral=giveIntegral.subtract(giveIntegral.multiply(payIntegrals.add(freight)).divide(totalAmounts,2, RoundingMode.HALF_UP));
                /*order.put("promotionPrice",promotionPrice);
                order.put("upgradePromotionPrice",promotionPrice.multiply(sysPropertiesUtils.getDecimalValue("upgradePromotionPercent")).divide(new BigDecimal("100")));*/
                order.put("freight",freight);
                order.put("payAmount",usePayAmount);
                order.put("payIntegral",payIntegrals);
                order.put("integralAmount",payIntegrals);
                order.put("number",count);
                order.put("totalAmount",(totalAmounts.add(freight)));
                order.put("giveIntegral",giveIntegral);
                order.put("discount",discount);
                order.put("proAmount",proAmount);
                order.put("isGameOrder",isGameOrder);
                order.put("rewardAmount",gameAmounts.multiply(sysPropertiesUtils.getDecimalValue("uRatio")));
                order.put("shopId",shopId);
                order.put("settlementAmount",settlementAmount);
                order.put("buyerMessage",orderModel.getBuyerMessage());

                /*if(CommonUtil.isNotEmpty(busiCustomerRecommendRelationPojo)){
                    order.put("promotionUser",busiCustomerRecommendRelationPojo.getRecomCustomerId());
                    BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo1=customerRecommendRelationInfoServiceImpl.selectRecommendUser(busiCustomerRecommendRelationPojo.getRecomCustomerId());
                    if(CommonUtil.isNotEmpty(busiCustomerRecommendRelationPojo1)){
                        order.put("upgradePromotionUser",busiCustomerRecommendRelationPojo1.getRecomCustomerId());
                    }
                }*/
                if(usePayAmount.compareTo(BigDecimal.ZERO)==0){
                    order.put("orderStatus",2);
                    order.put("payType",3);
                    order.put("createTime",new Date());
                    order.put("payTime",new Date());
                    order.put("totalOrderNo", totalOrderNo);
                }else{
                    order.put("createTime",new Date());
                    order.put("orderStatus",1);
                }
                if(CommonUtil.isNotEmpty(orderModel.getTaxType())){
                    order.put("taxType",orderModel.getTaxType());
                }else{
                    order.put("taxType",0);
                }
                order.put("receiptType",orderModel.getReceiptType());
                order.put("taxNo",orderModel.getTaxNo());
                order.put("taxCompany",orderModel.getTaxCompany());
            }
            totalAmount=totalAmount.add(totalAmounts).add(freight);

            parameterOrder.put("order",order);
            parameterOrder.put("orderDetail",orderList);
            parameterList.add(parameterOrder);
        }
        if(sharePrice.compareTo(busiCustomerBankrollPojo.getRewardAmount())==1){
            throw  new BusinessException("游戏币数量不足");
        }
        //校验总金额
        if(totalAmount.compareTo(orderModel.getTotalAmount())!=0||totalAmount.compareTo(BigDecimal.ZERO)==-1){
            throw new BusinessException("传入的总金额有误");
        }
        //校验付款金额
        BigDecimal payAmount=totalAmount.subtract(orderModel.getPayIntegral()).subtract(sharePrice.multiply(sysPropertiesUtils.getDecimalValue("uRatio")));
        if(payAmount.compareTo(orderModel.getPayAmount())!=0||payAmount.compareTo(BigDecimal.ZERO)==-1){
            throw new BusinessException("传入的最终支付金额有误");
        }

        //生成订单
        try{
            int[] orderIds=orderBusiServiceImpl.addOrder(parameterList);
            //删除购物车信息
            if(CommonUtil.isNotEmpty(orderModel.getShopcartIds())){
                shopcartBusiServiceImpl.deleteShopcart(orderModel.getShopcartIds());
            }
            return orderIds;
        }catch (Exception e){
            LoggerUtil.error(AddOrderExecutor.class, e);
            throw new BusinessException("生成订单失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderModel orderModel=(OrderModel) model;
        ErrorMessage error=new ErrorMessage();
        if(CommonUtil.isEmpty(orderModel.getOrderType())){
            error.rejectNull("orderType",null,"下单来源");
        }
        if(CommonUtil.isEmpty(orderModel.getOrderSourse())){
            error.rejectNull("orderSourse",null,"下单方式");
        }
        if(CommonUtil.isEmpty(orderModel.getPayAmount())){
            error.rejectNull("payAmount",null,"付款金额");
        }
        if(CommonUtil.isEmpty(orderModel.getPayIntegral())){
            error.rejectNull("payIntegral",null,"使用星币");
        }
        if(CommonUtil.isEmpty(orderModel.getShopList())){
            error.rejectNull("shopList",null,"商品信息");
        }
        if(CommonUtil.isEmpty(orderModel.getTotalAmount())){
            error.rejectNull("totalAmount",null,"总金额");
        }
        if(CommonUtil.isEmpty(orderModel.getReceiveaddressId())){
            error.rejectNull("receiveaddressId",null,"收货地址ID");
        }
        return error;
    }
}
