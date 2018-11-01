package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerOrderModel;
import com.yinhetianze.business.customer.model.BusiCustomerPageModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 消费者/会员 分页获取消费者信息列表
 */

@Component
public class GetCustomerListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerPageModel busiCustomerPageModel = (BusiCustomerPageModel)model;

        PageHelper.startPage(busiCustomerPageModel.getCurrentPage(),busiCustomerPageModel.getPageSize());
        List<BusiCustomerOrderModel> list = customerInfoServiceImpl.selectList(busiCustomerPageModel);
        for(BusiCustomerOrderModel busiCustomerOrderModel:list){
            Map<String,Object> m = orderInfoServiceImpl.countAmount(busiCustomerOrderModel.getId());
            busiCustomerOrderModel.setOrderCount(String.valueOf(m.getOrDefault("orderNumber","0")));
            busiCustomerOrderModel.setOrderPrice(new BigDecimal(String.valueOf(m.getOrDefault("totalAmount","0"))));
        }
        PageInfo<BusiCustomerOrderModel> pageInfo = new PageInfo<BusiCustomerOrderModel>(list);
        return pageInfo;
    }
}
