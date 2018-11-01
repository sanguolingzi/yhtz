package com.yinhetianze.back.report.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.report.model.ReportModel;
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetSumExchangeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ReportInfoService reportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ReportModel reportModel=(ReportModel)model;
        Map dbmap=new HashMap();
        if (CommonUtil.isNotEmpty(reportModel.getStartDate())){
            dbmap.put("startDate",reportModel.getStartDate());
        }
        if(CommonUtil.isNotEmpty(reportModel.getEndDate())){
            dbmap.put("endDate",reportModel.getEndDate());
        }
        int sumExchange=reportInfoServiceImpl.selectSumExchange(dbmap);
        Map map=new HashMap();
        map.put("sumExchange",sumExchange);
        return map;
    }
}
