package com.yinhetianze.business.parameter.controller;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.model.SpecialParametersModel;
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
@RequestMapping("product/param")
public class ProductParameterController extends RestfulController
{

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData addProductParameter(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductParameterExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseData deleteProductParameter(HttpServletRequest request, HttpServletResponse response, ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductParameterExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseData modifyProductParameter(HttpServletRequest request, HttpServletResponse response, ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("modifyProductParameterExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData getProductParameter(HttpServletRequest request, HttpServletResponse response, ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductParameterExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getProductParameterList(HttpServletRequest request, HttpServletResponse response, ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductParameterListExecutor");
        return executor.execute(request, response, model);
    }

    @RequestMapping(value = "bind", method = RequestMethod.POST)
    public ResponseData bindProductCateParamList(HttpServletRequest request, HttpServletResponse response, ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("bindProductCateParamRelaExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 增加特殊属性参数
     */
    @RequestMapping(value ="/special/add", method = RequestMethod.POST)
    public ResponseData addProductSpecialParameter(HttpServletRequest request, HttpServletResponse response, @RequestBody SpecialParametersModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductSpecialParameterExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改特殊属性参数
     */
    @RequestMapping(value = "/special/modify", method = RequestMethod.POST)
    public ResponseData modifyProductSpecialParameter(HttpServletRequest request, HttpServletResponse response,  @RequestBody SpecialParametersModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("modifyProductSpecialParameterExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除特殊属性参数
     */
    @RequestMapping(value = "/special/delete", method = RequestMethod.POST)
    public ResponseData deleteProductSpecialParameter(HttpServletRequest request, HttpServletResponse response, @RequestBody SpecialParametersModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductSpecialParameterExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询特殊属性参数
     */
    @RequestMapping(value = "/special", method = RequestMethod.GET)
    public ResponseData getProductSpecialParameter(HttpServletRequest request, HttpServletResponse response,SpecialParametersModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductSpecialParameterExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 根据分类Id查询商品参数
     */
    @RequestMapping(value = "categoryId", method = RequestMethod.GET)
    public ResponseData getProductCategoryIdParameter(HttpServletRequest request, HttpServletResponse response, ProductParameterModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductCategoryIdParameterExecutor");
        return executor.execute(request, response, model);
    }
}
