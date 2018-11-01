package com.yinhetianze.business.unit.controller;

import com.yinhetianze.business.unit.model.UnitModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("product/unit")
public class UnitController extends RestfulController
{

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getUnitList(HttpServletRequest request, HttpServletResponse response, UnitModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getUnitListExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData getUnit(HttpServletRequest request, HttpServletResponse response, UnitModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getUnitExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData addUnit(HttpServletRequest request, HttpServletResponse response, UnitModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("addUnitExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseData modifyUnit(HttpServletRequest request, HttpServletResponse response, UnitModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("modifyUnitExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseData deleteUnit(HttpServletRequest request, HttpServletResponse response, UnitModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("deleteUnitExecutor");
        return executor.execute(request, response, model);
    }
}
