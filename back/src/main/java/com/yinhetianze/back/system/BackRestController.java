package com.yinhetianze.back.system;

import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import com.yinhetianze.systemservice.system.model.*;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luox
 * on 2018/3/28.
 */
@RestController("backRest")
@RequestMapping("back")
public class BackRestController extends RestfulController
{

    /**
     * 新增系统参数
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/insertSys")
    public Object addSysSyspropertiesExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysSyspropertiesModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysSyspropertiesExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改系统参数
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateSys")
    public Object updateSysSyspropertiesExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysSyspropertiesModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysSyspropertiesExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询系统参数
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/sys")
    public Object getSysSyspropertiesExecutor(HttpServletRequest request, HttpServletResponse response,BusiSysSyspropertiesModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysSyspropertiesExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除系统参数
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/deleteSys")
    public Object deleteSysSyspropertiesExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysSyspropertiesExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 刷新系统参数
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="/refreshSys")
    public Object refreshSyspropertiesExecutor(HttpServletRequest request, HttpServletResponse response)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("refreshSyspropertiesExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null);
    }


    /**
     * 查询错误码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/errorCode")
    public Object getSysErrorCodeExecutor(HttpServletRequest request, HttpServletResponse response,SysErrorCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysErrorCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改错误码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateErrorCode")
    public Object updateSysErrorCodeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysErrorCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysErrorCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 新增错误码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/insertErrorCode")
    public Object addSysErrorCodeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysErrorCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysErrorCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除错误码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/deleteErrorCode")
    public Object deleteSysErrorCodeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysErrorCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysErrorCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询数据字典
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/sysDictionary")
    public Object getSysDictionaryExecutor(HttpServletRequest request, HttpServletResponse response,SysDictionaryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysDictionaryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 增加数据字典
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertSysDictionary")
    public Object addSysDictionaryExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysDictionaryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysDictionaryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 修改数据字典
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateSysDictionary")
    public Object updateSysDictionaryExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysDictionaryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysDictionaryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除数据字典
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteSysDictionary")
    public Object deleteSysDictionaryExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysDictionaryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 批量删除数据字典
     * @param request
     * @param response
     * @param
     * @return
     */
    @PostMapping (value = "/deleteListSysDictionary")
    public Object deleteListSysDictionary(HttpServletRequest request, HttpServletResponse response,Object... params)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteListSysDictionary");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,null,params);
    }

    /**
     * 添加轮播图
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertSysBanner")
    public Object addSysBannerExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysBannerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysBannerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改轮播图信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateSysBanner")
    public Object updateSysBannerExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysBannerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysBannerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除轮播图信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/deleteSysBanner")
    public Object deleteSysBannerExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysBannerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询轮播图
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/sysBanner")
    public Object getSysBannerExecutor(HttpServletRequest request, HttpServletResponse response,SysBannerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor  =(BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysBannerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 添加搜索词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertSearchBox")
    public Object addSearchBoxExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SearchBoxModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSearchBoxExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改搜索词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateSearchBox")
    public Object updateSearchBoxExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SearchBoxModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSearchBoxExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除搜索词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteSearchBox")
    public Object deleteSearchBoxExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody  DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSearchBoxExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询搜索词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/searchBox")
    public Object getSearchBoxExecutor(HttpServletRequest request, HttpServletResponse response, SearchBoxModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSearchBoxExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 添加热词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertTopSearch")
    public Object addTopSearchExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody TopSearchModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addTopSearchExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改热词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateTopSearch")
    public Object updateTopSearchExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody TopSearchModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateTopSearchExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除热词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteTopSearch")
    public Object deleteTopSearchExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteTopSearchExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询热词
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/topSearch")
    public Object getTopSearchExecutor(HttpServletRequest request, HttpServletResponse response, TopSearchModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getTopSearchExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 添加菜单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertSysMenu")
    public Object addSysMenuExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改菜单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateSysMenu")
    public Object updateSysMenuExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody SysMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSysMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除菜单
     * @param request
     * @param response
     * @return
     */
    @PostMapping (value = "/deleteSysMenu")
    public Object deleteSysMenuExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSysMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询菜单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/sysMenu")
    public Object getSysMenuExecutor(HttpServletRequest request, HttpServletResponse response, SysMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSysMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询用户拥有菜单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/getOptorMenuList")
    public Object getOptorMenuListExecutor(HttpServletRequest request, HttpServletResponse response, BusiSysOptorModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getOptorMenuListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询父级菜单
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/parentSysMenu")
    public Object getParentSysMenuExecutor(HttpServletRequest request, HttpServletResponse response, SysMenuModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getParentSysMenuExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /*************************************************************************************************************/
    /**
     * 添加协议
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/insertProtocol")
    public Object insertProtocol(HttpServletRequest request, HttpServletResponse response,@RequestBody ProtocolModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("insertProtocolExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改协议
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateProtocol")
    public Object updateProtocol(HttpServletRequest request, HttpServletResponse response,@RequestBody ProtocolModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateProtocolExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询协议
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findProtocol")
    public Object findProtocol(HttpServletRequest request, HttpServletResponse response, ProtocolModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findProtocolExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询注册协议详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findRegeister")
    public Object findProtocolDetail(HttpServletRequest request, HttpServletResponse response, ProtocolModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findRegeisterExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 刷新协议配置
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/refreshProtocol")
    public Object refreshProtocol(HttpServletRequest request, HttpServletResponse response,@RequestBody ProtocolModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("refreshProtocolExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除协议
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteProtocol")
    public Object deleteProtocol(HttpServletRequest request, HttpServletResponse response,@RequestBody ProtocolModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteProtocolExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /*************************************************************************************************************/

    /**
     * 添加帮助分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/addClassify")
    public Object addClassify(HttpServletRequest request, HttpServletResponse response,@RequestBody ClassifyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addClassifyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改帮助分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateClassify")
    public Object updateClassify(HttpServletRequest request, HttpServletResponse response,@RequestBody ClassifyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateClassifyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除帮助分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteClassify")
    public Object deleteClassify(HttpServletRequest request, HttpServletResponse response,@RequestBody ClassifyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteClassifyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询单个帮助分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findClassifyById")
    public Object findClassifyById(HttpServletRequest request, HttpServletResponse response,ClassifyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findClassifyByIdExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询帮助分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findAllClassify")
    public Object findAllClassify(HttpServletRequest request, HttpServletResponse response,ClassifyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findAllClassifyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**********************************************************************************************************/

    /**
     * 添加问答
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/addQuestion")
    public Object addQuestion(HttpServletRequest request, HttpServletResponse response,@RequestBody QuestionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addQuestionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改问答
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateQuestion")
    public Object updateQuestion(HttpServletRequest request, HttpServletResponse response,@RequestBody QuestionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateQuestionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除问答
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteQuestion")
    public Object deleteQuestion(HttpServletRequest request, HttpServletResponse response,@RequestBody QuestionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteQuestionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询问答
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findQuestionById")
    public Object findQuestionById(HttpServletRequest request, HttpServletResponse response,QuestionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findQuestionByIdExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询帮助分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/selectQuestion")
    public Object selectQuestion(HttpServletRequest request, HttpServletResponse response,QuestionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findQuestionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 模糊查询帮助问题
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/getQuestion")
    public Object getQuestion(HttpServletRequest request, HttpServletResponse response, QuestionModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getQuestionExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /***********************************************************************************************************/

    /**
     * 添加短信模板
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/addSms")
    public Object addSms(HttpServletRequest request, HttpServletResponse response,@RequestBody SmsModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSmsExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改短信模板
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateSms")
    public Object updateSms(HttpServletRequest request, HttpServletResponse response,@RequestBody SmsModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateSmsExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除短信模板
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteSms")
    public Object deleteSms(HttpServletRequest request, HttpServletResponse response,@RequestBody SmsModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteSmsExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询短信模板
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findAllSms")
    public Object findAllSms(HttpServletRequest request, HttpServletResponse response,SmsModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findAllSmsExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询热词(phone端搜索栏下面)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/phoneTopSearch")
    public Object getPhoneTopSearchExecutor(HttpServletRequest request, HttpServletResponse response, TopSearchModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getPhoneTopSearchExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 增加问答是否有帮助
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value ="/addSysHelpPraise")
    public  Object addSysHelpPraise(HttpServletRequest request, HttpServletResponse response,@RequestBody SysHelpPraiseModel model){
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addSysHelpPraiseExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台查询跳转链接码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/backstageConcatenatedCodeList")
    public Object getBackstageConcatenatedCodeListExecutor(HttpServletRequest request, HttpServletResponse response, ConcatenatedCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageConcatenatedCodeListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台增加跳转链接码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/addConcatenatedCode")
    public Object addConcatenatedCodeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ConcatenatedCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addConcatenatedCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台删除跳转链接码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/deleteConcatenatedCode")
    public Object deleteConcatenatedCodeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ConcatenatedCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteConcatenatedCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台修改跳转链接码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/updateConcatenatedCode")
    public Object updateConcatenatedCodeExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ConcatenatedCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateConcatenatedCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询跳转链接码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/concatenatedCodeList")
    public Object getConcatenatedCodeListExecutor(HttpServletRequest request, HttpServletResponse response, ConcatenatedCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getConcatenatedCodeListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 根据id查询跳转链接码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/concatenatedCode")
    public Object getConcatenatedCodeExecutor(HttpServletRequest request, HttpServletResponse response, ConcatenatedCodeModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getConcatenatedCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    //------------------ 会员权益 --------------------------

    /**
     * 查询会员权益父级列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/memberInfo/list")
    public Object getParentSysMemberExecutor(HttpServletRequest request, HttpServletResponse response, SysMemberInfoModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getParentSysMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询会员权益子集列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/memberInfo/child/list")
    public Object getChildSysMemberExecutor(HttpServletRequest request, HttpServletResponse response, SysMemberInfoModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getChildSysMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 查询会员权益
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping (value = "/findMemberInfo")
    public Object findMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response, ProtocolModel model,String name)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("findMemberInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model,name);
    }

    /**
     * 新增会员权益父级
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/memberInfo/add")
    public Object addParentSysMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody SysMemberInfoModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addParentSysMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 新增会员权益子集
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/memberInfo/child/add")
    public Object addChildSysMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response,  @RequestBody SysMemberInfoModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addChildSysMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改会员权益父级
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/memberInfo/update")
    public Object updateParentSysMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response,  @RequestBody SysMemberInfoModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateParentSysMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改会员权益子集
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/memberInfo/child/update")
    public Object updateChildSysMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response,  @RequestBody SysMemberInfoModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateChildSysMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除会员权益父级
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/memberInfo/delete")
    public Object deleteParentSystemMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteParentSystemMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除会员权益子集
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping (value = "/memberInfo/child/delete")
    public Object deleteChildSystemMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteChildSystemMemberExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 刷新会员权益缓存
     * @return
     */
    @GetMapping(value = "/refreshMemberInfo")
    public Object refreshMallActivityCacheExecutor(HttpServletRequest request, HttpServletResponse response
            ,String refresh)
    {
        // 执行业务
        BusinessExecutor<Object> executor = (BusinessExecutor<Object>)ApplicationContextFactory.getBean("refreshMemberInfoCacheExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }

}
