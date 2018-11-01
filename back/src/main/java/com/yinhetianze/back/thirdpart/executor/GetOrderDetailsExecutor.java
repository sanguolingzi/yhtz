package com.yinhetianze.back.thirdpart.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.order.model.GameDataModel;
import com.yinhetianze.business.order.model.OrderDto;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetOrderDetailsExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OrderDto orderModel = (OrderDto)model;
        GameDataModel orderMap=orderInfoServiceImpl.findThirdOrder(orderModel);
        Map dataCheck =new HashMap();
        dataCheck.put("orderDetail",orderMap);
        if(CommonUtil.isNotEmpty(orderMap)){
            dataCheck.put("dataCheck",0);
        }else{
            dataCheck.put("dataCheck",1);
        }
        return dataCheck;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OrderDto orderModel = (OrderDto)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(orderModel.getOrdersNo())){
            errorMessage.rejectNull("ordersNo",null,"订单号");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(orderModel.getChannelCode())){
            errorMessage.rejectNull("channelCode",null,"渠道编码");
            return errorMessage;
        }
        if(CommonUtil.isNull(orderModel.getSign())){
            errorMessage.rejectNull("sign",null,"签名参数");
            return errorMessage;
        }else{
            //签名参数校验
            CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(orderModel.getChannelCode());
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString="channelCode="+orderModel.getChannelCode()+"&channelSecret="+channelSecret+"&ordersNo="+orderModel.getOrdersNo();
            String checkSign=MD5CoderUtil.encode(checkSignString);
            if(!checkSign.equals(orderModel.getSign())){
                errorMessage.rejectError("checkSign","BC0057","签名错误");
                return errorMessage;
            }
        }
        return errorMessage;
    }
}
