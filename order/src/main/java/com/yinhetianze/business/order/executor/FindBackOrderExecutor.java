package com.yinhetianze.business.order.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindBackOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto=(OrderDto)model;
        try {
            Map<String, Object> result=new HashMap<String, Object>();
            Map<String,Object> parameter=new HashMap<>();
            if(CommonUtil.isNotEmpty(orderDto.getOrderStatus())){
                parameter.put("orderStatus",orderDto.getOrderStatus());
            }
            if(CommonUtil.isNotEmpty(orderDto.getShopId())){
                parameter.put("shopId",orderDto.getShopId());
            }
            if(CommonUtil.isNotEmpty(orderDto.getCustomerId())){
                parameter.put("customerId",orderDto.getCustomerId());
            }

            if(CommonUtil.isNotEmpty(orderDto.getOrdersNo())){
                parameter.put("ordersNo",orderDto.getOrdersNo());
            }
            if(CommonUtil.isNotEmpty(orderDto.getOrderId())){
                parameter.put("orderId",orderDto.getOrderId());
            }
            if(CommonUtil.isNotEmpty(orderDto.getBeginTime())){
                parameter.put("beginTime",orderDto.getBeginTime());
            }
            if(CommonUtil.isNotEmpty(orderDto.getEndTime())){
                parameter.put("endTime",orderDto.getEndTime());
            }
            if(CommonUtil.isNotEmpty(orderDto.getIsGameOrder())){
                parameter.put("isGameOrder",orderDto.getIsGameOrder());
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
                    //店铺信息
                    BusiShopPojo busiShopPojo = new BusiShopPojo();
                    busiShopPojo.setId(Integer.valueOf(map.get("shopId")+""));
                    busiShopPojo = shopInfoServiceImpl.selectOne(busiShopPojo);
                    if(CommonUtil.isEmpty(busiShopPojo)){
                        throw new BusinessException("没有相关店铺信息");
                    }
                    map.put("shopName",busiShopPojo.getShopName());
                    //消费者信息
                    BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
                    busiCustomerPojo.setId(Integer.valueOf(map.get("customerId")+""));
                    busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
                    if (CommonUtil.isEmpty(busiCustomerPojo)) {
                        throw new BusinessException("没有获取到用户信息");
                    }
                    map.put("userPhone",busiCustomerPojo.getPhone());
                    map.put("nickName",busiCustomerPojo.getNickName());
                    map.put("orderDetailList",orderInfoServiceImpl.findOrderDetail(Integer.valueOf(map.get("id")+""),null));
                    orderList.add(map);
                }
            }
            result.put("total",pageList.getTotal());
            result.put("orderList",orderList);
            return result;
        }catch (Exception e){
            LoggerUtil.error(FindBackOrderExecutor.class,e);
            throw new BusinessException("查询订单失败");
        }
    }

}
