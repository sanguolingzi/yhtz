package com.yinhetianze.business.exchange.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeInfoService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.impl.ShopProxyInfoServiceImpl;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.order.ExchangePojo;

import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindExchangeByIdExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ExchangeInfoService exchangeInfoServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopProxyInfoServiceImpl shopProxyInfoServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ExchangePojo exchangePojo=new ExchangePojo();
        Map<String,Object> result=new HashMap<>();
        BeanUtils.copyProperties(model, exchangePojo);

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        //查询售后记录
        exchangePojo.setDelFlag((short)0);
        exchangePojo=exchangeInfoServiceImpl.findById(exchangePojo);
        if(CommonUtil.isEmpty(exchangePojo)){
            throw new BusinessException("传入的ID有误");
        }
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> parameter=new HashMap<>();
        if(exchangePojo.getExchangeType()==2){
            parameter.put("ordersNo",exchangePojo.getOrderNo());
            list=orderInfoServiceImpl.findOrder(parameter);
            result.put("proAmount",list.get(0).get("proAmount"));
            result.put("freight",list.get(0).get("freight"));
            result.put("discount",list.get(0).get("discount"));
            result.put("totalAmount",list.get(0).get("totalAmount"));
            result.put("payAmount",list.get(0).get("payAmount"));
            result.put("giveIntegral",list.get(0).get("giveIntegral"));
            result.put("payIntegral",list.get(0).get("payIntegral"));
            list=orderInfoServiceImpl.findOrderDetail(Integer.valueOf(list.get(0).get("id")+""),null);
        }else{
            list=orderInfoServiceImpl.findOrderDetail(null,exchangePojo.getOrderDetailId());
        }
        ShopProxyPojo shopProxyPojo = new ShopProxyPojo();
        shopProxyPojo.setId(exchangePojo.getProxyShopId());
        shopProxyPojo = shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        if(CommonUtil.isEmpty(shopProxyPojo)){
            throw new BusinessException("没有相关店铺信息");
        }
       /* BusiShopPojo busiShopPojo=new BusiShopPojo();
        busiShopPojo.setId(exchangePojo.getShopId());
        busiShopPojo=shopInfoServiceImpl.selectOne(busiShopPojo);*/

        //用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(exchangePojo.getCustomerId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);


        /*BusiCustomerPojo busiCustomerPojo1 = new BusiCustomerPojo();
        busiCustomerPojo1.setId(exchangePojo.getUserId());
        busiCustomerPojo1 = customerInfoServiceImpl.selectOne(busiCustomerPojo1);*/


        result.put("customerName",busiCustomerPojo.getPhone());
        //result.put("userName",busiCustomerPojo1.getPhone());
        result.put("shopName",shopProxyPojo.getShopName());
        result.put("prodList",list);
        result.put("exchangeInfo",exchangePojo);
        return result;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ExchangeModel exchangeModel=(ExchangeModel) model;
        if(CommonUtil.isEmpty(exchangeModel.getId())){
            errorMessage.rejectNull("id",null,"售后记录ID");
        }
        return errorMessage;
    }
}
