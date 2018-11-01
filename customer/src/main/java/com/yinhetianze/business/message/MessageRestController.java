package com.yinhetianze.business.message;

import com.yinhetianze.business.message.model.*;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
@RestController
@RequestMapping("customer/message")
public class MessageRestController extends RestfulController
{
    /**---------------------------------POST START   ------------------------------------------------------------**/
    /**
     * 新增 消息总表
     * @param request
     * @param response
     * @param busiMessageModel
     * @return
     */
    @PostMapping(value="addRelation")
    public Object addMessageRelationExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody  BusiMessageModel busiMessageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addMessageRelationExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiMessageModel);
    }


    /**
     * 新增消息详情
     * @param request
     * @param response
     * @param busiMessageDetailModel
     * @return
     */
    @PostMapping(value="/detail")
    public Object addMessageDetailExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiMessageDetailModel busiMessageDetailModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addMessageDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiMessageDetailModel);
    }


    /**
     * 新增消息详情(集合)
     * @param request
     * @param response
     * @param busiMessageDetailListModel
     * @return
     */
    @PostMapping(value="/detailList")
    public Object addMessageDetailExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiMessageDetailListModel busiMessageDetailListModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addMessageDetailExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiMessageDetailListModel);
    }

    /**---------------------------------POST END ------------------------------------------------------------**/









     /**---------------------------------GET  START   ------------------------------------------------------------**/
    /**
     * 查询消息中心 商城公告/商城活动/通知消息 等等 分类条目
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="/getMessageCenter")
    public Object getMessageCenterInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiMessageCenterModel busiMessageCenterModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getMessageCenterInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiMessageCenterModel);
    }

    /**
     * 查询消息中心 商城公告/商城活动/通知消息 等等 某一类型的消息列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="page")
    public Object getMessageDetailListExecutor(HttpServletRequest request, HttpServletResponse response, BusiMessageDetailPageModel busiMessageDetailPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getMessageDetailListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiMessageDetailPageModel);
    }

    /**---------------------------------GET  END   ------------------------------------------------------------**/
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
}
