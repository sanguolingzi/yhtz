package com.yinhetianze.business.brand.controller;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.product.model.ProductModel;
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
@RequestMapping("product/brand")
public class BrandController extends RestfulController
{
    /**
     * 查询品牌列表
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getBrandList(HttpServletResponse response, HttpServletRequest request, BrandModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getBrandListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询品牌详情
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData getBrand(HttpServletResponse response, HttpServletRequest request, BrandModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor)
                ApplicationContextFactory.getBean("getBrandExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 添加商品品牌
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData addBrand(HttpServletResponse response, HttpServletRequest request, @RequestBody  BrandModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("addBrandExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品品牌
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseData modifyBrand(HttpServletResponse response, HttpServletRequest request, @RequestBody BrandModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("modifyBrandExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除品牌
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseData deleteBrand(HttpServletResponse response, HttpServletRequest request, @RequestBody BrandModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("deleteBrandExecutor");
        return executor.execute(request, response, model);
    }

}
