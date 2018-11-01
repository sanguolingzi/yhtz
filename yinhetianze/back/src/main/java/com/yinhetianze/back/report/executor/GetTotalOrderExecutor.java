package com.yinhetianze.back.report.executor;

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
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetTotalOrderExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ReportInfoService reportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        //获取前一天的总订单数
        Map timeMap =CommonUtil.LocalDate(1);
        Map orderMap=reportInfoServiceImpl.getTotalOrder(timeMap);
        return orderMap;
    }
}
