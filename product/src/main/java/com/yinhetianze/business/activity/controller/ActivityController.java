package com.yinhetianze.business.activity.controller;

import com.yinhetianze.business.activity.model.ActivityProductImgModel;
import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("product")
public class ActivityController  extends RestfulController {


    /**
     * 后台查询商品列表
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageActivityProductList", method = RequestMethod.GET)
    public ResponseData getBackstageActivityProductListExecutor(HttpServletResponse response, HttpServletRequest request, ActivityProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageActivityProductListExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 新增活动商品
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "addActivityProduct", method = RequestMethod.POST)
    public ResponseData addActivityProductExecutor(HttpServletResponse response, HttpServletRequest request,@RequestBody ActivityProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addActivityProductExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 新增活动商品
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "updateActivityProduct", method = RequestMethod.POST)
    public ResponseData updateActivityProductExecutor(HttpServletResponse response, HttpServletRequest request,@RequestBody ActivityProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateActivityProductExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 新增活动商品
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteActivityProduct", method = RequestMethod.POST)
    public ResponseData deleteActivityProductExecutor(HttpServletResponse response, HttpServletRequest request,@RequestBody ActivityProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteActivityProductExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询活动商品图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageActivityProductImg", method = RequestMethod.GET)
    public ResponseData getBackstageActivityProductImgExecutor(HttpServletRequest request, HttpServletResponse response, ActivityProductImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageActivityProductImgExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台增加、修改、删除 活动商品滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addActivityProductImg", method = RequestMethod.POST)
    public ResponseData addActivityProductImgExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ActivityProductImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addActivityProductImgExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询活动商品列表
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "activityProductList", method = RequestMethod.GET)
    public ResponseData getActivityProductListExecutor(HttpServletResponse response, HttpServletRequest request, ActivityProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getActivityProductListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询活动商品详情
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "activityProductDetails", method = RequestMethod.GET)
    public ResponseData getActivityProductDetailsExecutor(HttpServletResponse response, HttpServletRequest request, ActivityProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getActivityProductDetailsExecutor");
        return executor.execute(request, response, model);
    }
}
