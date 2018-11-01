package com.yinhetianze.business.shoppingcart.executor;

import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import com.yinhetianze.business.shoppingcart.model.ShopcartModel;
import com.yinhetianze.business.shoppingcart.service.ShopcartInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
    public class FindShopcartByIdsExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopcartInfoService shopcartInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> resultInfo=new HashMap<String,Object>();
        ShopcartModel shopcartModel=(ShopcartModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        try {
            //用户信息
            BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
            busiCustomerPojo.setId(tokenUser.getId());
            busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
            if (CommonUtil.isEmpty(busiCustomerPojo)) {
                throw new BusinessException("没有获取到用户信息");
            }
            //账户信息
            BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectBankrollInfo(tokenUser.getId());
            if (CommonUtil.isEmpty(busiCustomerBankrollPojo.getStarCoin())) {
                throw new BusinessException("没有获取到账户信息");
            }

            if(shopcartModel.getShopcartIds()==null){
                //查询商品信息
                ProductPojo prodPojo = new ProductPojo();
                prodPojo.setId(shopcartModel.getProductId());
                prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                if (CommonUtil.isEmpty(prodPojo)){
                    throw new BusinessException("商品信息不存在");
                }
                Map<String, Object> detailParam = new HashMap<>();
                detailParam.put("prodId",shopcartModel.getProductId());
                detailParam.put("skuCode",shopcartModel.getSkuCode());
                detailParam.put("delFlag", 0); // 没有删除的标记
                detailParam.put("status", 0); // 有效状态0
                // 商品详情信息单品列表
                List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
                if(CommonUtil.isEmpty(detailList)){
                    throw new BusinessException("商品规格信息不存在");
                }
                BigDecimal amount=new BigDecimal("0");
                if((busiCustomerPojo.getIsMember()==0||busiCustomerPojo.getIsPartner()==0)&&CommonUtil.isNotEmpty(detailList.get(0).get("vipPrice"))){
                    amount=new BigDecimal(detailList.get(0).get("vipPrice")+"");
                }else{
                    amount=new BigDecimal(detailList.get(0).get("sellPrice")+"");
                }
                Map<String,Object> map=new HashMap<>();
                List<Map<String,Object>> list=new ArrayList<>();
                map.put("shopId",prodPojo.getShopId());
                map.put("prodId",shopcartModel.getProductId());
                map.put("prodSku",shopcartModel.getSkuCode());
                map.put("prodName",prodPojo.getProdName());
                map.put("prodImg",prodPojo.getProductImg());
                map.put("prodPic",amount);
                map.put("prodSpeci",detailList.get(0).get("prodSpeci"));
                map.put("prodStatus",detailList.get(0).get("status"));
                map.put("marketPrice",prodPojo.getMarketPrice());
                map.put("freight",prodPojo.getFreight());
                map.put("number",shopcartModel.getNumber());
                if(CommonUtil.isNotEmpty(detailList.get(0).get("uPrice"))){
                    map.put("gameAmount",new BigDecimal(detailList.get(0).get("uPrice")+"").multiply(new BigDecimal(shopcartModel.getNumber())));
                }
                list.add(map);

                Map<String,Object> m=new HashMap<>();
                List<Map<String,Object>> list1=new ArrayList<>();
                //店铺信息
                if(prodPojo.getDropShipping()==1){
                    ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
                    shopProxyPojo.setId(prodPojo.getDropShippingId());
                    shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                    if(CommonUtil.isEmpty(shopProxyPojo)){
                        throw new BusinessException("没有相关店铺信息");
                    }
                    m.put("shopName",shopProxyPojo.getShopName());
                    m.put("shopLogo",shopProxyPojo.getShopLogo());
                    m.put("shopId",prodPojo.getDropShippingId());
                }else {
                    BusiShopPojo busiShopPojo = new BusiShopPojo();
                    busiShopPojo.setId(prodPojo.getShopId());
                    busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                    if(CommonUtil.isEmpty(busiShopPojo)){
                        throw new BusinessException("没有相关店铺信息");
                    }
                    m.put("shopName",busiShopPojo.getShopName());
                    m.put("shopLogo",busiShopPojo.getShopLogo());
                    m.put("shopId",prodPojo.getShopId());
                }

                BigDecimal totalAmount=amount.multiply(new BigDecimal(shopcartModel.getNumber()));
                BigDecimal sellAmount=amount.multiply(new BigDecimal(shopcartModel.getNumber()));
                BigDecimal freight=prodPojo.getFreight();
                m.put("totalAmount",totalAmount);
                m.put("freight",freight);
                m.put("cheapAmount",totalAmount.subtract(sellAmount));
                m.put("subtotalAmount",sellAmount.add(freight));
                m.put("list",list);
                list1.add(m);

                resultInfo.put("uRatio",sysPropertiesUtils.getDecimalValue("uRatio"));
                resultInfo.put("gameId",busiCustomerPojo.getGameId());
                resultInfo.put("rewardAmount",busiCustomerBankrollPojo.getRewardAmount());
                resultInfo.put("starCoin",busiCustomerBankrollPojo.getStarCoin());
                resultInfo.put("shopcartList", list1);
                resultInfo.put("totalProdAmount",sellAmount.add(freight));
            }else{
                JSONArray json=JSONArray.fromObject(shopcartModel.getShopcartIds());
                List<Integer> idsList=(List<Integer>)json;
                int[] ids = new int[idsList.size()];
                for(int k=0;k<idsList.size();k++){
                    ids[k] = idsList.get(k);
                }
                List<Map<String,Object>> list=shopcartInfoServiceImpl.findShopcartByIds(ids);
                List<Map<String ,Object>> result = new ArrayList<Map<String ,Object>>();
                Map<String, Object> map1 = new TreeMap<String,Object>();
                //合计金额
                BigDecimal totalProdAmount=new BigDecimal("0");
                if(CommonUtil.isNotEmpty(list)){
                    for(int i=0;i<list.size();i++){
                        //查询商品信息
                        ProductPojo prodPojo = new ProductPojo();
                        prodPojo.setId(Integer.valueOf(list.get(i).get("prodId")+""));
                        prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                        if (CommonUtil.isEmpty(prodPojo)){
                            throw new BusinessException("商品ID为"+list.get(i).get("prodId")+"的商品信息不存在");
                        }
                        Map<String, Object> detailParam = new HashMap<>();
                        detailParam.put("prodId",list.get(i).get("prodId"));
                        detailParam.put("skuCode",list.get(i).get("prodSku"));
                        detailParam.put("delFlag", 0); // 没有删除的标记
                        detailParam.put("status", 0); // 有效状态0
                        // 商品详情信息单品列表
                        List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
                        if(CommonUtil.isEmpty(detailList)){
                            throw new BusinessException("商品规格信息不存在");
                        }
                        BigDecimal amount=new BigDecimal("0");
                        if((busiCustomerPojo.getIsMember()==0||busiCustomerPojo.getIsPartner()==0)&&CommonUtil.isNotEmpty(detailList.get(0).get("vipPrice"))){
                            amount=new BigDecimal(detailList.get(0).get("vipPrice")+"");
                        }else{
                            amount=new BigDecimal(detailList.get(0).get("sellPrice")+"");
                        }
                        list.get(i).put("prodName",prodPojo.getProdName());
                        list.get(i).put("shopId",prodPojo.getShopId());
                        list.get(i).put("prodImg",prodPojo.getProductImg());
                        list.get(i).put("prodPic",amount);
                        list.get(i).put("prodSpeci",detailList.get(0).get("prodSpeci"));
                        list.get(i).put("prodStatus",detailList.get(0).get("status"));
                        list.get(i).put("marketPrice",prodPojo.getMarketPrice());
                        list.get(i).put("freight",prodPojo.getFreight());
                        if(CommonUtil.isNotEmpty(detailList.get(0).get("uPrice"))){
                            list.get(i).put("gameAmount",new BigDecimal(detailList.get(0).get("uPrice")+"").multiply(new BigDecimal(list.get(i).get("number")+"")));
                        }
                        if(CommonUtil.isEmpty(list.get(i).get("proxyShopId")+"")){
                            List<Map<String,Object>> t = new ArrayList<Map<String,Object>>();
                            t.add(list.get(i));
                            map1.put(list.get(i).get("shopId")+"", t);
                        }else{
                            if (map1.containsKey(list.get(i).get("proxyShopId")+"")) {
                                List<Map<String,Object>> t1 = (List<Map<String,Object>>)map1.get(list.get(i).get("proxyShopId")+"");
                                t1.add(list.get(i));
                                map1.put(list.get(i).get("proxyShopId")+"", t1);
                            } else {
                                List<Map<String,Object>> t2 = new ArrayList<Map<String,Object>>();
                                t2.add(list.get(i));
                                map1.put(list.get(i).get("proxyShopId")+"", t2);
                            }
                        }
                    }
                    for (Map.Entry<String,Object> entry : map1.entrySet()) {
                        Map<String,Object> m=new HashMap<>();
                        //店铺信息
                        ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
                        shopProxyPojo.setId(Integer.valueOf(entry.getKey()+""));
                        shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                        if(CommonUtil.isNotEmpty(shopProxyPojo)){
                            m.put("shopName",shopProxyPojo.getShopName());
                            m.put("shopLogo",shopProxyPojo.getShopLogo());
                        }else{
                            BusiShopPojo busiShopPojo = new BusiShopPojo();
                            busiShopPojo.setId(Integer.valueOf(entry.getKey()+""));
                            busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                            m.put("shopName",busiShopPojo.getShopName());
                            m.put("shopLogo",busiShopPojo.getShopLogo());
                        }
                        m.put("shopId",entry.getKey());
                        m.put("list",entry.getValue());
                        List<Map<String,Object>> list1=(List<Map<String,Object>>)entry.getValue();
                        BigDecimal totalAmount=new BigDecimal("0");
                        BigDecimal sellAmount=new BigDecimal("0");
                        BigDecimal freight=new BigDecimal("0");
                        for(int i=0;i<list1.size();i++){
                            totalAmount=totalAmount.add((new BigDecimal(list1.get(i).get("prodPic")+"")).multiply(new BigDecimal(list1.get(i).get("number")+"")));
                            sellAmount=sellAmount.add((new BigDecimal(list1.get(i).get("prodPic")+"")).multiply(new BigDecimal(list1.get(i).get("number")+"")));
                            if(new BigDecimal(list1.get(i).get("freight")+"").compareTo(freight)==1){
                                freight=new BigDecimal(list1.get(i).get("freight")+"");
                            }
                        }
                        totalProdAmount=totalProdAmount.add(sellAmount).add(freight);
                        m.put("totalAmount",totalAmount);
                        m.put("freight",freight);
                        m.put("cheapAmount",totalAmount.subtract(sellAmount));
                        m.put("subtotalAmount",sellAmount.add(freight));
                        result.add(m);
                    }
                }
                resultInfo.put("uRatio",sysPropertiesUtils.getDecimalValue("uRatio"));
                resultInfo.put("gameId",busiCustomerPojo.getGameId());
                resultInfo.put("rewardAmount",busiCustomerBankrollPojo.getRewardAmount());
                resultInfo.put("starCoin",busiCustomerBankrollPojo.getStarCoin());
                resultInfo.put("shopcartList", result);
                resultInfo.put("totalProdAmount",totalProdAmount);
            }

        } catch (Exception e) {
            LoggerUtil.error(FindShopcartExecutor.class,e);
            throw new BusinessException("订单确认失败");
        }
        return resultInfo;
    }

}
