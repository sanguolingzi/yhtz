package com.yinhetianze.business.order.executor;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.mall.model.ChannelModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GetSettleOrderExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderDto = (OrderDto) model;
        PageHelper.startPage(orderDto.getCurrentPage(),orderDto.getPageSize());
        PageInfo pageInfo = new PageInfo(orderInfoServiceImpl.GetSettleOrder(orderDto));
        return pageInfo;

    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderDto = (OrderDto) model;
        ErrorMessage error = new ErrorMessage();
        if (CommonUtil.isEmpty(orderDto.getSettlementId())) {
            error.rejectNull("settlementId", null, "结算单id");
        }
        return error;
    }
}
