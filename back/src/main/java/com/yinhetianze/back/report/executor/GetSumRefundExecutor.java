package com.yinhetianze.back.report.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetSumRefundExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ReportInfoService reportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Integer sumRefund=reportInfoServiceImpl.selectSumRefund();
        Map map=new HashMap();
        map.put("sumRefund",sumRefund);
        return map;
    }
}
