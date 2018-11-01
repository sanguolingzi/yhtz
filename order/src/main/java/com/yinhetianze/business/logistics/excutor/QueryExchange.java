package com.yinhetianze.business.logistics.excutor;

import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.logistics.model.LogisticsInformationModel;
import com.yinhetianze.business.logistics.service.info.LogisticsInformationInfoService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.permission.model.BusiSysPermissionModel;
import com.yinhetianze.systemservice.permission.service.info.SysPermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物流查询
 */
@Component
public class QueryExchange extends AbstractRestBusiExecutor {

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private LogisticsInformationInfoService logisticsInformationInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse respone, BasicModel model, Object... params) throws BusinessException {

        LogisticsInformationModel logisticsModel = (LogisticsInformationModel)model;
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(logisticsModel.getToken());
        Map order = new HashMap();
        order.put("orderId",logisticsModel.getOrdersId());
        order.put("customerId",tokenUser.getId());
        List orderList=orderInfoServiceImpl.findOrder(order);
        if(CommonUtil.isNotEmpty(orderList)&& orderList.size()==1){
            LogisticsInformationPojo  logisticsPojo = new LogisticsInformationPojo();
            logisticsPojo.setOrdersId(logisticsModel.getOrdersId());
           Map logisticsMap = logisticsInformationInfoServiceImpl.getLogistics(logisticsPojo);
            return logisticsMap;
        }else{
            throw new BusinessException("查询错误");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        LogisticsInformationModel LogisticsModel = (LogisticsInformationModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(LogisticsModel.getOrdersId())){
            errorMessage.rejectError("ordersId",null,"订单ID");
        }
        return errorMessage;
    }
}
