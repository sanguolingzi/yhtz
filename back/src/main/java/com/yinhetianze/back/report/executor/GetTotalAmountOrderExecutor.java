package com.yinhetianze.back.report.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetTotalAmountOrderExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ReportInfoService reportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map timeMap =CommonUtil.LocalDate(1);
        Map orderMap=reportInfoServiceImpl.getTotalAmountOrder(timeMap);
        return orderMap;
    }
}
