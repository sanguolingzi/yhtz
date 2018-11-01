package com.yinhetianze.business.tag.controller;

import com.yinhetianze.business.tag.model.ProductTagModel;
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
@RequestMapping("product/tag")
public class ProductTagController extends RestfulController
{
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getProductTagList(HttpServletRequest request, HttpServletResponse response, ProductTagModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getProductTagListExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData getProductTag(HttpServletRequest request, HttpServletResponse response, ProductTagModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getProductTagExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData addProductTag(HttpServletRequest request, HttpServletResponse response, ProductTagModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("addProductTagExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseData modifyProductTag(HttpServletRequest request, HttpServletResponse response, ProductTagModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("modifyProductTagExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseData deleteProductTag(HttpServletRequest request, HttpServletResponse response, ProductTagModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("deleteProductTagExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "bind", method = RequestMethod.POST)
    public ResponseData bindProductTag(HttpServletRequest request, HttpServletResponse response, ProductTagModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("bindProductTagExecutor");
        return executor.execute(request, response, model);
    }
}
