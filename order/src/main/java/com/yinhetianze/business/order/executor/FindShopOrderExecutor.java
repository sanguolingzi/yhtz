package com.yinhetianze.business.order.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindShopOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser.getId())){
            throw new BusinessException("没有获取到操作人ID");
        }
        //根据操作然查询店铺信息
        ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
        shopProxyPojo.setOptorId(tokenUser.getId());
        shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        if(CommonUtil.isEmpty(shopProxyPojo)){
            throw new BusinessException("没有相关店铺信息");
        }

        Map<String, Object> result=new HashMap<String, Object>();
        Map<String,Object> parameter=new HashMap<>();
        parameter.put("proxyShopId",shopProxyPojo.getId());
        if(CommonUtil.isNotEmpty(orderDto.getOrderStatus())){
            parameter.put("orderStatus",orderDto.getOrderStatus());
        }
        if(CommonUtil.isNotEmpty(orderDto.getOrdersNo())){
            parameter.put("ordersNo",orderDto.getOrdersNo());
        }
        if(CommonUtil.isNotEmpty(orderDto.getBeginTime())){
            parameter.put("beginTime",orderDto.getBeginTime());
        }
        if(CommonUtil.isNotEmpty(orderDto.getEndTime())){
            parameter.put("endTime",orderDto.getEndTime());
        }
        if(orderDto.getCurrentPage()==0){
            orderDto.setCurrentPage(1);
            orderDto.setPageSize(10);
        }
        PageHelper.startPage(orderDto.getCurrentPage(),orderDto.getPageSize());
        List<Map<String,Object>> orderList=orderInfoServiceImpl.findBackOrder(parameter);
        PageInfo<Map<String,Object>> pageList = new PageInfo<Map<String,Object>>(orderList);
        orderList=new ArrayList<>();
        if(CommonUtil.isNotEmpty(pageList.getList())){
            for(Map<String,Object> map:pageList.getList()){
                map.put("orderDetailList",orderInfoServiceImpl.findOrderDetail(Integer.valueOf(map.get("id")+""),null));
                orderList.add(map);
            }
        }
        result.put("total",pageList.getTotal());
        result.put("orderList",orderList);
        return result;
    }

}
