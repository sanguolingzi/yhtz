package com.yinhetianze.business.order.executor;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class WxPayResultExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private WxService wxService;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto = (OrderDto) model;
        try {
            WxPayOrderQueryResult wxPayOrderQueryResult=wxService.getWxPayInfo(null,orderDto.getTotalOrderNo());
            return wxPayOrderQueryResult.getTradeState();

        }catch (Exception e){
            LoggerUtil.error(WxPayResultExecutor.class,e.getMessage());
            throw new BusinessException("查询支付结果失败");
        }

    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderDto = (OrderDto) model;
        ErrorMessage error = new ErrorMessage();
        if (CommonUtil.isEmpty(orderDto.getTotalOrderNo())) {
            error.rejectNull("totalOrderNo", null, "总订单号");
        }
        return error;
    }
}
