package com.yinhetianze.business.evaluate.controller;

import com.yinhetianze.business.evaluate.model.EvaluateDto;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
@RestController
@RequestMapping("order")
public class EvaluateController extends RestfulController
{
    /**
     * 添加评价
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="evaluate/add",method= RequestMethod.POST)
    public Object addEvaluate(HttpServletRequest request, HttpServletResponse response, @RequestBody EvaluateDto model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("addEvaluateExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商家回复评价
     */
    @RequestMapping(value="evaluate/answer",method= RequestMethod.POST)
    public Object answerEvaluate(HttpServletRequest request, HttpServletResponse response,@RequestBody EvaluateModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("answerEvaluateExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询商品评价
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="evaluate/findEvaluate",method= RequestMethod.GET)
    public Object findOrder(HttpServletRequest request, HttpServletResponse response,EvaluateModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findEvaluateExecutor");
        return executor.execute(request, response, model);
    }



    /**************************************************后台**********************************************/

    /**
     * 后台查询商品评价
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="evaluate/findBackEvaluate",method= RequestMethod.GET)
    public Object findBackEvaluate(HttpServletRequest request, HttpServletResponse response,EvaluateModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("findBackEvaluateExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台更新商品评价
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="evaluate/updateBackEvaluate",method= RequestMethod.POST)
    public Object updateBackEvaluate(HttpServletRequest request, HttpServletResponse response,@RequestBody EvaluateModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("updateBackEvaluateExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value="evaluate/modify",method= RequestMethod.POST)
    public Object modifyBackEvaluate(HttpServletRequest request, HttpServletResponse response,@RequestBody EvaluateModel model){

        BusinessExecutor executor = (BusinessExecutor) ApplicationContextFactory.getBean("modifyBackEvaluateExecutor");
        return executor.execute(request, response, model);
    }

}
