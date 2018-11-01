package com.yinhetianze.business.shop;

import com.yinhetianze.business.shop.model.*;
import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.model.BusiShopCompanyPageModel;
import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.model.BusiShopSearchModel;
import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditModel;
import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditPageModel;
import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.configurations.annotation.JSON;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import com.yinhetianze.springmvc.controller.RestfulController;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
@RestController
@RequestMapping("shop")
public class ShopRestController extends RestfulController
{
    /**---------------------------------POST START   ------------------------------------------------------------**/
    /**
     * 新增店铺信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/addAudit")
    public Object addShopInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 新增店铺品牌信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/addShopBrand")
    public Object addBrandForShopExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiShopBrandModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addBrandForShopExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 新增店铺访问量
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/addShopVisitCount")
    public Object addShopVisitCountExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopVisitCountExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**
     * 修改店铺品牌是否显示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateShopBrand/isShow")
    public Object updateShopBrandIsShowExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiShopBrandModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopBrandIsShowExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**
     * 新增企业信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/company")
    public Object addCompanyInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopCompanyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCompanyInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改店铺信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateShop")
    public Object updateShopInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改店铺装饰图
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateShop/decorate")
    public Object updateShopDecorateExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopDecorateExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 修改企业信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateCompany")
    public Object updateCompanyInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopCompanyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCompanyInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 删除店铺信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/deleteShop")
    public Object deleteShopInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteShopInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除企业信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/deleteCompany")
    public Object deleteCompanyInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopCompanyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteCompanyInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 企业店铺综合审核信息提交
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/audit/update")
    public Object updateAuditExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiSysShopAuditModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateAuditExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改企业审核信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/company/updateAudit")
    public Object updateCompanyAuditExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopCompanyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCompanyAuditExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改店铺审核信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/updateAudit")
    public Object updateShopAuditInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopAuditInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 新增商城推荐店铺
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/addShopGuess")
    public Object addShopGuessExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopGuessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopGuessExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 删除商城推荐店铺
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/deleteShopGuess")
    public Object deleteShopGuessExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopGuessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteShopGuessExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 修改商城推荐店铺
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/updateShopGuess")
    public Object updateShopGuessExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopGuessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopGuessExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    /**---------------------------------POST END ------------------------------------------------------------**/








    /**---------------------------------GET  START   ------------------------------------------------------------**/
    /**
     * 公众浏览-店铺主页 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/shopIndex",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type= BusiShopModel.class,include = "id,shopName,shopLogo,shopMainPhoto,shopDecorate,shopMemo,shopWapPhoto,shopCollectCount,shopQrcode,place,createTime,businessLicense")
    public Object getShopIndexExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopModel busiShopModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopIndexExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopModel);
    }
    /**
     * 查询店铺 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type= BusiShopModel.class,include = "id,shopName,shopLogo,shopMainPhoto,shopMainProduct,shopDecorate,shopMemo,shopType,shopPhone,phoneShow,postageFree,auditStatus,shopWapPhoto,postageFree")
    public Object getShopInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopModel busiShopModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopModel);
    }

    /**
     * 商家中心-店铺主页 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/shopHome",produces = MediaType.APPLICATION_JSON_VALUE)
    //@JSON(type= BusiShopModel.class,include = "id,shopName,shopLogo,shopMainPhoto,shopMainProduct,shopDecorate,shopMemo,shopType,shopPhone,phoneShow,postageFree")
    public Object GetShopHomeInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopModel busiShopModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopHomeInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopModel);
    }




    /**
     * 查询店铺 信息 (搜索)
     * @param request
     * @param response
     * @return list
     */
    @GetMapping(value = "/page",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type=BusiShopSearchModel.class,include = "id,shopName,shopLogo,shopMemo,regionLocation,productTotal,productList,shopMainProduct")
    public Object getShopInfoListExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopSearchModel busiShopSearchModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopInfoListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopSearchModel);
    }

    /**
     * 查询企业 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/company",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type=BusiShopCompanyModel.class,filter = "token,modelName,customerId")
    public Object getCompanyInfoExecutor(HttpServletRequest request, HttpServletResponse response,BusiShopCompanyModel busiShopCompanyModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCompanyInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopCompanyModel);
    }

    /**
     * 查询企业 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/companyByShop")
    public Object getCompanyInfoByShopExecutor(HttpServletRequest request, HttpServletResponse response,BusiShopCompanyModel busiShopCompanyModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCompanyInfoByShopExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopCompanyModel);
    }

    /**
     * 查询企业列表 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/company/page")
    public Object getCompanyListExecutor(HttpServletRequest request, HttpServletResponse response,BusiShopCompanyPageModel busiShopCompanyPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCompanyListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopCompanyPageModel);
    }

    /**
     * 查询店铺列表 信息 (后台审核用)
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/manage/page",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type=BusiShopPageModel.class,include = "id,shopName,auditStatus,customerId")
    public Object getShopListExecutor(HttpServletRequest request, HttpServletResponse response,BusiShopPageModel busiCompanyPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCompanyPageModel);
    }


    /**
     * 查询店铺品牌列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/brand/page",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type=BusiShopBrandModel.class,include = "id,brandName,shopBrandImg,isShow,sort")
    public Object getShopBrandListExecutor(HttpServletRequest request, HttpServletResponse response,BusiShopBrandModel busiShopBrandModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopBrandListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiShopBrandModel);
    }


    /**
     * 查询企业店铺审核信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/company/audit")
    public Object getShopAuditInfoExecutor(HttpServletRequest request, HttpServletResponse response,BusiSysShopAuditModel busiSysShopAuditModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopAuditInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiSysShopAuditModel);
    }



    /**
     * 查询用户企业店铺已有审核信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/company/auditResult")
    public Object getShopAuditResultExecutor(HttpServletRequest request, HttpServletResponse response,BusiSysShopAuditModel busiSysShopAuditModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopAuditResultExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiSysShopAuditModel);
    }

    /**
     * 查询企业店铺审核信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/company/audit/manage")
    public Object getShopAuditInfoForManagerExecutor(HttpServletRequest request, HttpServletResponse response,BusiSysShopAuditModel busiSysShopAuditModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopAuditInfoForManagerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiSysShopAuditModel);
    }

    /**
     * 查询企业店铺审核信息列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/company/auditList",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type = BusiSysShopAuditPojo.class,include = "id,auditStatus,createTime,updateTime")
    public Object getShopAuditListExecutor(HttpServletRequest request, HttpServletResponse response,BusiSysShopAuditPageModel busiSysShopAuditPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopAuditListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiSysShopAuditPageModel);
    }
    /**
     * 查询商城推荐店铺
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/shopGuessList")
    public Object GetShopGuessListExecutor(HttpServletRequest request, HttpServletResponse response,ShopGuessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopGuessListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台查询商城推荐店铺
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/backstageShopGuessList")
    public Object GetBackstageShopGuessListExecutor(HttpServletRequest request, HttpServletResponse response,ShopGuessModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageShopGuessListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台查询店铺基本信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/backstageShop")
    public Object GetBackstageShopExecutor(HttpServletRequest request, HttpServletResponse response,BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageShopExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询店铺内分类
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/shopCategory",produces = MediaType.APPLICATION_JSON_VALUE)
    @JSON(type= ShopCategoryPojo.class,include = "id,cateName")
    public Object getShopCategory(HttpServletRequest request, HttpServletResponse response,ShopCategoryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopCategoryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 增加店铺内分类
     */
    @PostMapping(value="/add/shopCategory")
    public Object AddShopCategory(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopCategoryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopCategoryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

	
    /**
     * 修改店铺内分类
     */
    @PostMapping(value="/update/shopCategory")
    public Object UpdateShopCategory(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopCategoryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopCategoryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除店铺内分类
     */
    @PostMapping(value="/delete/shopCategory")
    public Object deleteShopCategoryExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopCategoryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteShopCategoryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询店铺内分类(不需要登录)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/notLoginShopCategory")
    public Object getNotLoginShopCategory(HttpServletRequest request, HttpServletResponse response,ShopCategoryModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getNotLoginShopCategoryExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 游戏端访问页面gameToken校验
     */
    @GetMapping(value="/checkGameToken")
    public Object getCheckGameToken(HttpServletRequest request, HttpServletResponse response,GameRecordModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCheckGameTokenExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

     /**---------------------------------GET  END   ------------------------------------------------------------**/











     /**---------------------------------DELETE  START   ------------------------------------------------------------**/

     /**---------------------------------DELETE  END   ------------------------------------------------------------**/


    /*
    @RequestMapping(value = "{api_path}", method = RequestMethod.GET)
    public Object get(HttpServletRequest request, HttpServletResponse response, BasicModel model, @PathVariable("api_path") String path)
    {
        // 执行业务
        BusinessExecutor<?> executor = getExecutor(path, RequestMethod.GET);
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    @RequestMapping(value = "{api_path}", method = RequestMethod.POST)
    public Object post(HttpServletRequest request, HttpServletResponse response, BasicModel model, @PathVariable("api_path") String path)
    {
        BusinessExecutor<?> executor = getExecutor(path, RequestMethod.POST);
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 根据请求路径获取匹配的接口业务执行器
     * @param path
     * @param method
     * @return

    private BusinessExecutor<?> getExecutor(String path, RequestMethod method)
    {
        // 获取接口列表映射
        ApiListProperties properties = (ApiListProperties) ApplicationContextFactory.getBean("apiListProperties");
        if (CommonUtil.isNull(properties))
        {
            LoggerUtil.warn(CustomerRestController.class, "没有获取到配置的接口列表：[apiListProperties]");
            return null;
        }

        // 根据请求方法获取对应的api列表
        Properties apiList = getPropertiesByRequestMethod(method, properties);
        if (CommonUtil.isEmpty(apiList))
        {
            LoggerUtil.warn(CustomerRestController.class, "没有配置Get方法对应的API列表");
            return null;
        }

        // 获取业务处理类
        String executorId = apiList.getProperty(path);
        if (CommonUtil.isEmpty(executorId))
        {
            LoggerUtil.warn(CustomerRestController.class, "没有匹配到Executor：{}", new Object[]{executorId});
            return null;
        }

        BusinessExecutor<?> executor = (BusinessExecutor<?>) ApplicationContextFactory.getBean(executorId);
        return executor;
    }
     */
    /**
     * 根据请求方法获对应的取接口列表
     * @param method
     * @param properties
     * @return
    private Properties getPropertiesByRequestMethod(RequestMethod method, ApiListProperties properties)
    {
        switch (method)
        {
            case GET:
                return properties.getGet();
            case POST:
                return properties.getPost();
            case PUT:
                return properties.getPut();
            case DELETE:
                return properties.getDelete();
            default:
                return null;
        }
    }
    */
    /**
     * 后台查询代发店铺列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/backstageShopProxyList")
    public Object getBackstageShopProxyListExecutor(HttpServletRequest request, HttpServletResponse response, ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getBackstageShopProxyListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台新增代发店铺列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/addShopProxy")
    public Object addShopProxyExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopProxyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台修改代发店铺列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateShopProxy")
    public Object updateShopProxyExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopProxyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 后台删除代发店铺列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/deleteShopProxy")
    public Object deleteShopProxyExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteShopProxyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台查询代发店铺地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/shopProxyAddress")
    public Object getShopProxyAddressExecutor(HttpServletRequest request, HttpServletResponse response,ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getShopProxyAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询代发货店铺
     */
    @GetMapping(value = "/shopProxy")
    public Object getShopProxy(HttpServletRequest request, HttpServletResponse response,ShopProxyModel model){
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("getShopProxyExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 商家查询自己店铺信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/businessShopProxy")
    public Object getBusinessShopProxyExecutor(HttpServletRequest request, HttpServletResponse response, ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getBusinessShopProxyExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 后台查询自营店铺信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/selfSupportShop")
    public Object getSelfSupportShopExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSelfSupportShopExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 查询企业地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value="/companyinfoAddress")
    public Object getCompanyinfoAddressExecutor(HttpServletRequest request, HttpServletResponse response, BusiShopCompanyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCompanyinfoAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改店铺和企业信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateShopCompanyinfo")
    public Object updateShopCompanyinfoExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody ShopCompanyPageModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopCompanyinfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改店铺二维码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateShopQrcode")
    public Object updateShopQrcodeExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiShopModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateShopQrcodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 点击发送短消息通知商家账号与密码
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/shopProxyMessage")
    public Object addShopProxyMessageExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody ShopProxyModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addShopProxyMessageExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

}
