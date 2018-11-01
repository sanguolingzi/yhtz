package com.yinhetianze.business.settlement.controller;

import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("order")
public class SettlementController extends RestfulController {


    /**
     * 结算申请
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/add",method= RequestMethod.POST)
    public Object addSettlement(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addSettlementExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 结算审核
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/check",method= RequestMethod.POST)
    public Object checkSettlement(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("checkSettlementExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台打款
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/payment",method= RequestMethod.POST)
    public Object paymentSettlement(HttpServletRequest request, HttpServletResponse response, @RequestBody SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("paymentSettlementExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询审核记录详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/findById",method= RequestMethod.GET)
    public Object findByIdSettlement(HttpServletRequest request, HttpServletResponse response,SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findByIdSettlementExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询审核记录详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/findByShopId",method= RequestMethod.GET)
    public Object findSettlementByShopId(HttpServletRequest request, HttpServletResponse response, SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findSettlementByShopIdExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询审核记录详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/findAll",method= RequestMethod.GET)
    public Object findAll(HttpServletRequest request, HttpServletResponse response, SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findAllExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商家查询结算列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="settlement/SettlementList",method= RequestMethod.GET)
    public Object SettlementList(HttpServletRequest request, HttpServletResponse response, SettlementModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("getSettlementListExecutor");
        return executor.execute(request, response, model);
    }

}
