package com.yinhetianze.business.shoppingcart.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import com.yinhetianze.business.shoppingcart.model.ShopcartModel;
import com.yinhetianze.business.shoppingcart.service.ShopcartInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.Map.Entry;

@Service
public class FindShopcartExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopcartInfoService shopcartInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopcartModel shopcartModel=(ShopcartModel)model;
        Map<String,Object> resultInfo=new HashMap<String,Object>();
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

            if(CommonUtil.isEmpty(shopcartModel.getCurrentPage())){
                shopcartModel.setCurrentPage(1);
            }
            if(CommonUtil.isEmpty(shopcartModel.getPageSize())){
                shopcartModel.setPageSize(10);
            }

            PageHelper.startPage(shopcartModel.getCurrentPage(),shopcartModel.getPageSize());
            List<Map<String,Object>> list=shopcartInfoServiceImpl.findShopcartByCustomerId(tokenUser.getId());
            PageInfo<Map<String,Object>> pageList = new PageInfo<Map<String,Object>>(list);

            //有效的商品集合
            List<Map<String ,Object>> result = new ArrayList<>();
            //无效的商品集合
            List<Map<String ,Object>> result1 = new ArrayList<>();
            //有效的普通商品分组
            Map<String, Object> map1 = new TreeMap<>();
            //有效的游戏商品分组
            Map<String, Object> map2 = new TreeMap<>();
            if(CommonUtil.isNotEmpty(list)){
                for(int i=0;i<list.size();i++){
                    //查询商品信息
                    ProductPojo prodPojo = new ProductPojo();
                    prodPojo.setId(Integer.valueOf(list.get(i).get("prodId")+""));
                    prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                    if (CommonUtil.isEmpty(prodPojo)){
                        throw new BusinessException("商品信息不存在");
                    }
                    Map<String, Object> detailParam = new HashMap<>();
                    detailParam.put("prodId",list.get(i).get("prodId"));
                    detailParam.put("skuCode",list.get(i).get("prodSku"));
                    // 商品详情信息单品列表
                    List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
                    if(CommonUtil.isEmpty(detailList)){
                        throw new BusinessException("商品规格信息不存在");
                    }
                    list.get(i).put("prodName",prodPojo.getProdName());
                    list.get(i).put("prodImg",prodPojo.getProductImg());
                    if((busiCustomerPojo.getIsMember()==0||busiCustomerPojo.getIsPartner()==0)&&CommonUtil.isNotEmpty(detailList.get(0).get("vipPrice"))){
                        list.get(i).put("prodPic",detailList.get(0).get("vipPrice"));
                    }else{
                        list.get(i).put("prodPic",detailList.get(0).get("sellPrice"));
                    }
                    list.get(i).put("oldPic",prodPojo.getMarketPrice());
                    list.get(i).put("prodSpeci",detailList.get(0).get("prodSpeci"));

                    //商品是否下架0上架，1下架
                    if(prodPojo.getpStatus()!=0||prodPojo.getDelFlag()!=0||Integer.valueOf(detailList.get(0).get("status")+"")!=0){
                        result1.add(list.get(i));
                        //list.get(i).put("prodStatus",1);
                    }else{
                        if(CommonUtil.isEmpty(list.get(i).get("proxyShopId")+"")){
                            if (map2.containsKey(list.get(i).get("shopId")+"")) {
                                List<Map<String,Object>> t1 = (List<Map<String,Object>>)map2.get(list.get(i).get("shopId")+"");
                                t1.add(list.get(i));
                                map2.put(list.get(i).get("shopId")+"", t1);
                            } else {
                                List<Map<String,Object>> t2 = new ArrayList<Map<String,Object>>();
                                t2.add(list.get(i));
                                map2.put(list.get(i).get("shopId")+"", t2);
                            }
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
                        //list.get(i).put("prodStatus",0);
                    }

                }
                for (Entry<String,Object> entry : map1.entrySet()) {
                    Map<String,Object> m=new HashMap<>();
                    //店铺信息
                    ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
                    shopProxyPojo.setId(Integer.valueOf(entry.getKey()+""));
                    shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                    if(CommonUtil.isEmpty(shopProxyPojo)){
                        throw new BusinessException("没有相关店铺信息");
                    }
                    m.put("shopName",shopProxyPojo.getShopName());
                    m.put("shopLogo",shopProxyPojo.getShopLogo());
                    m.put("shopId",entry.getKey());
                    m.put("list",entry.getValue());
                    result.add(m);
                }
                for (Entry<String,Object> entry : map2.entrySet()) {
                    Map<String,Object> m=new HashMap<>();
                    //店铺信息
                    BusiShopPojo busiShopPojo = new BusiShopPojo();
                    busiShopPojo.setId(Integer.valueOf(entry.getKey()+""));
                    busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                    if(CommonUtil.isEmpty(busiShopPojo)){
                        throw new BusinessException("没有相关店铺信息");
                    }
                    m.put("shopName",busiShopPojo.getShopName());
                    m.put("shopLogo",busiShopPojo.getShopLogo());
                    m.put("shopId",entry.getKey());
                    m.put("list",entry.getValue());
                    result.add(m);
                }
            }

            resultInfo=new HashMap<>();
            resultInfo.put("shopcartList", result);
            resultInfo.put("loseList", result1);
            resultInfo.put("pageNum", pageList.getPageNum());
            resultInfo.put("pageSize", pageList.getPageSize());
            resultInfo.put("total", pageList.getTotal());
        } catch (Exception e) {
            LoggerUtil.error(FindShopcartExecutor.class,e.getMessage());
            throw new BusinessException("查询购物车失败");
        }
        return resultInfo;
    }

}
