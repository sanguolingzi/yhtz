package com.yinhetianze.back.coupon;

import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import com.yinhetianze.systemservice.coupon.model.CouponModel;
import com.yinhetianze.systemservice.system.model.DeleteModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * on 2018/07/16.
 */
@RestController
@RequestMapping("back")
public class BackCouponRestController extends RestfulController
{
    /**
     * 后台查询商城优惠券
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/backstageCouponList")
    public Object getBackstageCouponList(HttpServletRequest request, HttpServletResponse response, CouponModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getBackstageCouponListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台新增优惠券
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/addBackstageCoupon")
    public Object addBackstageCoupon(HttpServletRequest request, HttpServletResponse response,@RequestBody CouponModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("addBackstageCouponExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台修改优惠券
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateBackstageCoupon")
    public Object updateBackstageCoupon(HttpServletRequest request, HttpServletResponse response,@RequestBody CouponModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("updateBackstageCouponExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台删除优惠券
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteBackstageCoupon")
    public Object deleteBackstageCoupon(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("deleteBackstageCouponExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

}
