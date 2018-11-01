package com.yinhetianze.business.order.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FindOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //校验商家信息
        BusiShopPojo shopPojo=shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        try {
            Map<String, Object> result=new HashMap<String, Object>();
            if(orderDto.getCurrentPage()==0){
                orderDto.setCurrentPage(1);
                orderDto.setPageSize(10);
            }
            Map<String,Object> orderParment=new HashMap<>();
            orderParment.put("orderId",orderDto.getOrderId());
            orderParment.put("ordersNo",orderDto.getOrdersNo());
            orderParment.put("orderStatus",orderDto.getOrderStatus());
            if(CommonUtil.isNotEmpty(orderDto.getIsShop())&&orderDto.getIsShop()==1){
                orderParment.put("shopId",shopPojo.getId());
            }else{
                orderParment.put("customerId",tokenUser.getId());
            }

            //金额排序
            if(CommonUtil.isNotEmpty(orderDto.getTotalAmountSort())){
                orderParment.put("totalAmountSort",orderDto.getTotalAmountSort());
            }
            //时间排序
            if(CommonUtil.isNotEmpty(orderDto.getCreateTimeSort())) {
                orderParment.put("createTimeSort", orderDto.getCreateTimeSort());
            }
            //筛选的下单时间
            if(CommonUtil.isNotEmpty(orderDto.getBeginTime())){
                orderParment.put("beginTime",orderDto.getBeginTime());
            }
            if(CommonUtil.isNotEmpty(orderDto.getEndTime())){
                orderParment.put("endTime",orderDto.getEndTime());
            }
            PageHelper.startPage(orderDto.getCurrentPage(),orderDto.getPageSize());
            List<Map<String,Object>> orderList=orderInfoServiceImpl.findOrder(orderParment);
            List<Map<String,Object>> sortList=new ArrayList<>();
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(CommonUtil.isNotEmpty(orderList)){
                for(Map<String,Object> map:orderList){
                    //店铺信息
                    if(CommonUtil.isNotEmpty(map.get("proxyShopId"))){
                        ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
                        shopProxyPojo.setId(Integer.valueOf(map.get("proxyShopId")+""));
                        shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                        if(CommonUtil.isEmpty(shopProxyPojo)){
                            throw new BusinessException("没有相关店铺信息");
                        }
                        map.put("shopName",shopProxyPojo.getShopName());
                        map.put("shopLogo",shopProxyPojo.getShopLogo());
                    }else{
                        BusiShopPojo busiShopPojo = new BusiShopPojo();
                        busiShopPojo.setId(Integer.valueOf(map.get("shopId")+""));
                        busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                        if(CommonUtil.isEmpty(busiShopPojo)){
                            throw new BusinessException("没有相关店铺信息");
                        }
                        map.put("shopName",busiShopPojo.getShopName());
                        map.put("shopLogo",busiShopPojo.getShopLogo());
                    }
                    map.put("orderDetailList",orderInfoServiceImpl.findOrderDetail(Integer.valueOf(map.get("id")+""),null));
                    if(Integer.valueOf(map.get("orderStatus")+"")==1){
                        sortList.add(0,map);
                    }else{
                        sortList.add(map);
                    }
                }
            }
            PageInfo<Map<String,Object>> pageList = new PageInfo<Map<String,Object>>(orderList);
            result.put("total",pageList.getTotal());
            result.put("orderList",sortList);
            return result;
        }catch (Exception e){
            LoggerUtil.error(FindOrderExecutor.class,e);
            throw new BusinessException("查询订单列表失败");
        }
    }

}
