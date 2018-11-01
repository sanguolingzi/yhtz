package com.yinhetianze.back.report.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.report.service.info.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetTotalAmountDetailExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private ReportInfoService reportInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
       String type= request.getParameter("type");
        Map timeMap =new HashMap();
        if(CommonUtil.isNotEmpty(type)){
            if("0".equals(type)){
                //获取前一天的总订单数
                timeMap =CommonUtil.LocalDate(1);
            }
        }
        PageHelper.startPage(Integer.parseInt(request.getParameter("tableCurrentPage")),Integer.parseInt(request.getParameter("pageSize")));
        PageInfo pageInfo=new PageInfo(reportInfoServiceImpl.getTotalAmountDetail(timeMap));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
