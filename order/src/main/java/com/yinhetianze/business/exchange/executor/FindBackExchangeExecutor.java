package com.yinhetianze.business.exchange.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.exchange.model.ExchangeModel;
import com.yinhetianze.business.exchange.service.ExchangeInfoService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.ExchangePojo;
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
public class FindBackExchangeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ExchangeInfoService exchangeInfoServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(model.getToken());
        if (CommonUtil.isEmpty(tokenUser)) {
            throw new BusinessException("用户没有登录");
        }
        ExchangeModel exchangeModel = (ExchangeModel) model;

        if (exchangeModel.getCurrentPage() == 0) {
            exchangeModel.setCurrentPage(1);
            exchangeModel.setPageSize(10);
        }
        PageHelper.startPage(exchangeModel.getCurrentPage(), exchangeModel.getPageSize());
        PageInfo<ExchangePojo> pageList = new PageInfo<>(exchangeInfoServiceImpl.findExchange(exchangeModel));
        List<Map<String, Object>> listExchange = new ArrayList<>();
        if (CommonUtil.isNotEmpty(pageList.getList())) {
            for (ExchangePojo exchangePojo1 : pageList.getList()) {
                List<Map<String, Object>> listProd = new ArrayList<>();
                Map<String, Object> parameter = new HashMap<>();
                if (exchangePojo1.getExchangeType() == 2) {
                    parameter.put("ordersNo", exchangePojo1.getOrderNo());
                    listProd = orderInfoServiceImpl.findOrder(parameter);
                    if (CommonUtil.isNotEmpty(listProd)) {
                        listProd = orderInfoServiceImpl.findOrderDetail(Integer.valueOf(listProd.get(0).get("id") + ""), null);
                    }
                } else {
                    listProd = orderInfoServiceImpl.findOrderDetail(null, exchangePojo1.getOrderDetailId());
                }
                parameter = new HashMap<>();
                parameter.put("prodList", listProd);
                parameter.put("exchangeInfo", exchangePojo1);
                listExchange.add(parameter);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageList.getTotal());
        result.put("exchangeList", listExchange);
        return result;
    }
}
