package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.business.order.model.OrderModel;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
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
import java.util.*;

@Service
public class AddGameOrderExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

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
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }

        //查询推荐人ID
        BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo=customerRecommendRelationInfoServiceImpl.selectRecommendUser(tokenUser.getGameId());

        //校验用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }
        BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        //校验账户积分(使用星币应小于账户星币)
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
        if (CommonUtil.isEmpty(busiCustomerBankrollPojo.getStarCoin())) {
            throw new BusinessException("没有获取到账户信息");
        }
        if(orderModel.getPayIntegral().compareTo(busiCustomerBankrollPojo.getStarCoin())==1){
            throw  new BusinessException("消费券数量不足");
        }
        //校验 商家不能买自己店铺的商品
        if(CommonUtil.isNotEmpty(shopPojo)){
            if(shopPojo.getId()==Integer.valueOf(orderModel.getShopList().get(0).get("shopId")+"")){
                throw new BusinessException("商家不能购买自己店铺的商品");
            }
        }
        List<Map<String,Object>> list=(List<Map<String,Object>>)orderModel.getShopList().get(0).get("proList");
        if(CommonUtil.isEmpty(list)) {
            throw new BusinessException("商品参数为空");
        }

        //生成订单的参数
        List<Map<String,Object>> parameterList=new ArrayList<>();
        //存放订单和订详情的map
        Map<String,Object> parameterOrder=new HashMap<>();

        //订单的信息
        Map<String,Object> order=new HashMap<>();
        String totalOrderNo = CommonUtil.getSerialnumber();
        String ordersNo=CommonUtil.getSerialnumber();
        order.put("customerId",tokenUser.getId());
        order.put("orderType",orderModel.getOrderType());
        order.put("orderSourse",orderModel.getOrderSourse());
        order.put("receiver",busiCustomerPojo.getNickName());
        order.put("phone",busiCustomerPojo.getPhone());
        order.put("ordersNo",ordersNo);
        order.put("shopId",orderModel.getShopList().get(0).get("shopId"));
        //推荐人
        BigDecimal proportion=BigDecimal.ZERO;
        if(CommonUtil.isNotEmpty(busiCustomerRecommendRelationPojo)){
            /*order.put("promotionUser",busiCustomerRecommendRelationPojo.getRecomCustomerId());*/
            order.put("partnerId",busiCustomerRecommendRelationPojo.getPartnerId());
            /*BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo1=customerRecommendRelationInfoServiceImpl.selectRecommendUser(busiCustomerRecommendRelationPojo.getRecomCustomerId());
            if(CommonUtil.isNotEmpty(busiCustomerRecommendRelationPojo1)){
                order.put("upgradePromotionUser",busiCustomerRecommendRelationPojo1.getRecomCustomerId());
            }*/
            BusiCustomerPojo busiCustomerPojo1 = new BusiCustomerPojo();
            if(CommonUtil.isNotEmpty(busiCustomerRecommendRelationPojo.getPartnerId())){
                busiCustomerPojo1.setGameId(busiCustomerRecommendRelationPojo.getPartnerId());
                busiCustomerPojo1 = customerInfoServiceImpl.selectOne(busiCustomerPojo1);
                if(CommonUtil.isNotEmpty(busiCustomerPojo1)&&CommonUtil.isNotEmpty(busiCustomerPojo1.getProportion())){
                    proportion=busiCustomerPojo1.getProportion();
                }
            }
        }

        //订单详情
        List<Map<String,Object>> orderList=new ArrayList<>();
        Map<String,Object> orderDetail=new HashMap<>();
        //商品数量
        BigDecimal number=new BigDecimal(list.get(0).get("number")+"");
        Integer prodId=Integer.valueOf(list.get(0).get("prodId")+"");
        String prodSku= list.get(0).get("prodSku")+"";
        //查询商品信息
        ProductPojo prodPojo = new ProductPojo();
        prodPojo.setId(prodId);
        prodPojo = productInfoServiceImpl.findProduct(prodPojo);
        if (CommonUtil.isEmpty(prodPojo)){
            throw new BusinessException("商品信息不存在");
        }
        if(prodPojo.getProductDistinction()!=1){
            throw new BusinessException("购买的不是游戏商品");
        }
        Map<String, Object> detailParam = new HashMap<>();
        detailParam.put("prodId",prodId);
        detailParam.put("skuCode",prodSku);
        detailParam.put("delFlag", 0); // 没有删除的标记
        detailParam.put("status", 0); // 有效状态0
        // 商品详情信息单品列表
        List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
        if(CommonUtil.isEmpty(detailList)){
            throw new BusinessException("商品规格信息不存在");
        }
        //判断库存
        if(Integer.valueOf(detailList.get(0).get("storage")+"")<Integer.valueOf(list.get(0).get("number")+"")){
            throw new BusinessException("商品库存不足");
        }

        //推广价
        /*BigDecimal promotionPrice=new BigDecimal("0");
        if(CommonUtil.isNotEmpty(detailList.get(0).get("promotionPrice"))){
            promotionPrice=promotionPrice.add(new BigDecimal(detailList.get(0).get("promotionPrice")+"").multiply(number));
        }*/
        //商品销售总金额
        BigDecimal sellPrice=new BigDecimal("0");
        /*if((busiCustomerPojo.getIsMember()==0||busiCustomerPojo.getIsPartner()==0)&&CommonUtil.isNotEmpty(detailList.get(0).get("vipPrice"))){
            sellPrice=new BigDecimal(detailList.get(0).get("vipPrice")+"");
        }else{*/
            sellPrice=new BigDecimal(detailList.get(0).get("sellPrice")+"");
        /*}*/
        BigDecimal amount=sellPrice.multiply(number);
        BigDecimal originalPrice=prodPojo.getMarketPrice().multiply(number);
        BigDecimal discount=originalPrice.subtract(amount);
        BigDecimal payIntegral=orderModel.getPayIntegral();
        BigDecimal freight=new BigDecimal(orderModel.getShopList().get(0).get("freight")+"");
        BigDecimal payAmount=amount.add(freight).subtract(payIntegral);
        BigDecimal totalAmount=amount.add(freight);
        BigDecimal partnerAmount=BigDecimal.ZERO;
        BigDecimal memberAmount=BigDecimal.ZERO;
        BigDecimal rewardAmount=BigDecimal.ZERO;
        /*if(CommonUtil.isNotEmpty(prodPojo.getSharePrice())&&CommonUtil.isNotEmpty(list.get(0).get("gameAmount"))){
            BigDecimal gameAmount=new BigDecimal(list.get(0).get("number")+"").multiply(prodPojo.getSharePrice());
            rewardAmount=new BigDecimal(list.get(0).get("gameAmount")+"");
            if(gameAmount.compareTo(rewardAmount)==-1){
                throw new BusinessException("使用游戏币不能大于商品可使用的数量");
            }
            orderDetail.put("gameAmount",rewardAmount);
            payAmount=payAmount.subtract(rewardAmount);
        }*/
        if(payAmount.subtract(freight).compareTo(BigDecimal.ZERO)==1){
            //giveIntegral=(payAmount.subtract(freight)).multiply(new BigDecimal(sysPropertiesUtils.getStringValue("integralPercent")).divide(new BigDecimal("100")));
            partnerAmount=(payAmount.add(payIntegral).subtract(freight)).multiply(proportion).divide(new BigDecimal("100"));
            //memberAmount=(payAmount.add(payIntegral).subtract(freight)).multiply(new BigDecimal(sysPropertiesUtils.getStringValue("memberPercent")).divide(new BigDecimal("100")));
        }
        BigDecimal giveIntegral=BigDecimal.ZERO;
        /*if(busiCustomerPojo.getIsMember()==0||busiCustomerPojo.getIsPartner()==0){
            giveIntegral=new BigDecimal(detailList.get(0).get("flagPrice")+"").multiply(number);
        }*/
        orderDetail.put("uPrice",detailList.get(0).get("uPrice"));
        orderDetail.put("prodId",list.get(0).get("prodId"));
        orderDetail.put("prodSku",list.get(0).get("prodSku"));
        orderDetail.put("prodTitle",prodPojo.getpTitle());
        orderDetail.put("productName",prodPojo.getProdName());
        orderDetail.put("photoUrl",prodPojo.getProductImg());
        orderDetail.put("originalPrice",prodPojo.getMarketPrice());
        orderDetail.put("sellPrice",sellPrice);
        orderDetail.put("number",list.get(0).get("number"));
        orderDetail.put("productSpec",detailList.get(0).get("prodSpeci"));
        orderDetail.put("payIntegral",payIntegral);
        orderDetail.put("integralAmount",payIntegral);
        orderDetail.put("productDistinction",prodPojo.getProductDistinction());
        orderDetail.put("productType",prodPojo.getProductType());
        orderDetail.put("finalPrice",detailList.get(0).get("costPrice"));
        /*orderDetail.put("promotionPrice",promotionPrice);
        orderDetail.put("upgradePromotionPrice",promotionPrice.multiply(new BigDecimal(sysPropertiesUtils.getStringValue("upgradePromotionPercent")).divide(new BigDecimal("100"))));*/
        orderList.add(orderDetail);


        /*order.put("promotionPrice",promotionPrice);
        order.put("upgradePromotionPrice",promotionPrice.multiply(new BigDecimal(sysPropertiesUtils.getStringValue("upgradePromotionPercent")).divide(new BigDecimal("100"))));*/
        Integer roomCardNum=Integer.valueOf(detailList.get(0).get("number")+"");
        order.put("roomCardNum",new BigDecimal(roomCardNum).multiply(number));
        order.put("freight",freight);
        order.put("payAmount",payAmount);
        order.put("payIntegral",payIntegral);
        order.put("integralAmount",payIntegral);
        order.put("number",number);
        order.put("totalAmount",totalAmount);
        order.put("giveIntegral",giveIntegral);
        order.put("partnerAmount",partnerAmount);
        order.put("memberAmount",memberAmount);
        order.put("discount",discount);
        order.put("proAmount",amount);
        order.put("isGameOrder",1);
        order.put("gameId",busiCustomerPojo.getGameId());
        //order.put("rewardAmount",rewardAmount);

        if(payAmount.compareTo(BigDecimal.ZERO)==0){
            order.put("orderStatus",4);
            order.put("payType",3);
            order.put("createTime",new Date());
            order.put("payTime",new Date());
            order.put("totalOrderNo", totalOrderNo);
        }else{
            order.put("createTime",new Date());
            order.put("orderStatus",1);
        }

        parameterOrder.put("order",order);
        parameterOrder.put("orderDetail",orderList);
        parameterList.add(parameterOrder);

        if(rewardAmount.compareTo(busiCustomerBankrollPojo.getRewardAmount())==1){
            throw  new BusinessException("游戏币数量不足");
        }
        //校验总金额
        if(totalAmount.compareTo(orderModel.getTotalAmount())!=0||totalAmount.compareTo(BigDecimal.ZERO)==-1){
            throw new BusinessException("传入的总金额有误");
        }
        //校验付款金额
        if(payAmount.compareTo(orderModel.getPayAmount())!=0||payAmount.compareTo(BigDecimal.ZERO)==-1){
            throw new BusinessException("传入的最终支付金额有误");
        }

        //生成订单
        int[] orderIds=orderBusiServiceImpl.addOrder(parameterList);
        return orderIds;
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
        return error;
    }
}
