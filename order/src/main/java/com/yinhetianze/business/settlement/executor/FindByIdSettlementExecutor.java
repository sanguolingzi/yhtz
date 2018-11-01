package com.yinhetianze.business.settlement.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.order.SettlementPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindByIdSettlementExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private SettlementInfoService settlementInfoServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SettlementModel settlementModel=(SettlementModel)model;
        SettlementPojo settlementPojo=new SettlementPojo();
        settlementPojo.setSettlementId(settlementModel.getSettlementId());
        settlementPojo=settlementInfoServiceImpl.findById(settlementPojo);
        if(CommonUtil.isEmpty(settlementPojo)){
            throw new BusinessException("结算记录ID有误");
        }
        String[] orderIds=settlementPojo.getOrdersIds().split(",");
        List<Map<String,Object>> listOrder=new ArrayList<>();
        for(int i=0;i<orderIds.length;i++){
            Map<String,Object> orderParment=new HashMap<>();
            orderParment.put("orderId",orderIds[i]);
            List<Map<String,Object>> orderList=orderInfoServiceImpl.findBackOrder(orderParment);
            orderParment.put("ordersNo",orderList.get(0).get("ordersNo"));
            orderParment.put("settlementAmount",orderList.get(0).get("settlementAmount"));
            orderParment.put("totalAmount",orderList.get(0).get("totalAmount"));
            orderParment.put("rakeCost",new BigDecimal(orderList.get(0).get("totalAmount")+"").subtract(new BigDecimal(orderList.get(0).get("settlementAmount")+"")));
            listOrder.add(orderParment);
        }
        /*BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(settlementPojo.getCustomerId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);*/
        Map<String,Object> result=new HashMap<>();
        result.put("shopName",settlementPojo.getShopName());
        result.put("reviewer",settlementPojo.getReviewer());
        //result.put("customerName",busiCustomerPojo.getPhone());
        result.put("rakeCost",settlementPojo.getRakeCost());
        result.put("settlementCost",settlementPojo.getFinaltTotalCost());
        result.put("totalCost",settlementPojo.getTotalCost());
        result.put("settlementNo",settlementPojo.getSettlementNo());
        result.put("status",settlementPojo.getStatus());
        result.put("taxAmount",settlementPojo.getTaxAmount());
        result.put("orderList",listOrder);
        return result;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        SettlementModel settlementModel=(SettlementModel)model;
        if(CommonUtil.isEmpty(settlementModel.getSettlementId())){
            error.rejectNull("settlementId",null,"结算记录ID");
        }
        return error;
    }
}
