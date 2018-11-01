package com.yinhetianze.business.settlement.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.order.SettlementPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindAllExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private SettlementInfoService settlementInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SettlementModel settlementModel=(SettlementModel)model;
        PageHelper.startPage(settlementModel.getCurrentPage(),settlementModel.getPageSize());
        List<SettlementPojo> settlementList=settlementInfoServiceImpl.findSettlements(settlementModel);
        PageInfo<SettlementPojo> pageList = new PageInfo<>(settlementList);
        Map<String,Object> result=new HashMap<>();
        result.put("total",pageList.getTotal());
        result.put("settlementList",pageList.getList());
        return result;
    }
}
