package com.yinhetianze.business.category.controller;

import com.yinhetianze.business.category.model.*;
import com.yinhetianze.business.product.model.DeleteModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("product/cate")
public class CategoryController extends RestfulController
{
    /**
     * 查询分类列表
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getCategoryList(HttpServletResponse response, HttpServletRequest request,CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getCategoryListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询分类详情
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseData getCategory(HttpServletResponse response, HttpServletRequest request,@RequestBody CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor)
                ApplicationContextFactory.getBean("getCategoryExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 增加分类详情
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData addCategory(HttpServletResponse response, HttpServletRequest request,@RequestBody CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("addCategoryExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改分类详情
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseData modifyCategory(HttpServletResponse response, HttpServletRequest request, @RequestBody CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("modifyCategoryExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除分类详情
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseData deleteCategory(HttpServletResponse response, HttpServletRequest request, @RequestBody CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("deleteCategoryExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 三级分类列表
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "linkage", method = RequestMethod.GET)
    public ResponseData getCategoryLinkage(HttpServletResponse response, HttpServletRequest request,CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getCategoryLinkageExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 三级分类列表
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "mobileCategoryList", method = RequestMethod.GET)
    public ResponseData getMobileCategoryListExecutor(HttpServletResponse response, HttpServletRequest request,CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getMobileCategoryListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 增加、修改、删除 分类轮播图
     */
    @RequestMapping(value = "image/add", method = RequestMethod.POST)
    public ResponseData addCategoryImage(HttpServletResponse response, HttpServletRequest request,@RequestBody ClassifyImgModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("addCategoryImageExecutor");
        return executor.execute(request, response, model);
    }
    /**
     *查询分类轮播图
     */
    @RequestMapping(value = "categoryImage", method = RequestMethod.GET)
    public ResponseData getCategoryImage(HttpServletRequest request, HttpServletResponse response, ClassifyImgModel model) {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getCategoryImageExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 模糊查询商品参数
     */
    @GetMapping(value = "cateParamRela")
    public ResponseData  getCateParamRela(HttpServletRequest request, HttpServletResponse response, ParameterModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getCateParamRelaExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 新增商品参数
     */
    @PostMapping(value = "addCateParamRela")
    public ResponseData  AddCateParamRela(HttpServletRequest request, HttpServletResponse response,@RequestBody CateParamRelaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addCateParamRelaExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品参数
     */
    @PostMapping(value = "updateCateParamRela")
    public ResponseData  UpdateCateParamRela(HttpServletRequest request, HttpServletResponse response,@RequestBody CateParamRelaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateCateParamRelaExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除商品参数
     */
    @PostMapping(value = "deleteCateParamRela")
    public ResponseData  DeleteCateParamRela(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteCateParamRelaExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * PC分类搜索下列表信息
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "getPcCategorySearch", method = RequestMethod.GET)
    public ResponseData getPcCategorySearch(HttpServletResponse response, HttpServletRequest request,  ProductModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getPcCategorySearchExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询1级分类
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "oneCategoryList", method = RequestMethod.GET)
    public ResponseData getOneCategoryListExecutor(HttpServletResponse response, HttpServletRequest request,  CategoryModel model)
    {
        BusinessExecutor<ResponseData> executor = (BusinessExecutor) ApplicationContextFactory.getBean("getOneCategoryListExecutor");
        return executor.execute(request, response, model);
    }


}
