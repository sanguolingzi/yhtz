package com.yinhetianze.core.business;

import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
public abstract class TemplateBusinessExecutor<T> implements BusinessExecutor<T>
{
    public T execute(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params)
    {
        // 入口日志
        String busiCode = entryLog(request, model, params);

        // 校验
        ErrorMessage errorMessage = validate(request, model, params);
        if (CommonUtil.isNotEmpty(errorMessage) && errorMessage.hasError())
        {
            // 对校验信息进行处理
            return handleErrorMessage(request, response, errorMessage);
        }

        try
        {
            // 执行业务
            T t = handleBusiness(request, response, model, params);

            // 记录出口日志
            exitLog(request, busiCode);
            return t;
        }
        catch (Exception e)
        {
            if( !(e instanceof BusinessException)){
                LoggerUtil.error(getClass(), e);
            }
            return handleException(request, response, e);
        }
    }

    protected abstract T handleException(HttpServletRequest request, HttpServletResponse response, Exception e);

    /**
     * 处理入参校验结果
     * @param request
     * @param response
     * @param errorMessage
     * @return T
     */
    protected abstract T handleErrorMessage(HttpServletRequest request, HttpServletResponse response, ErrorMessage errorMessage);

    /**
     * 执行业务前的请求入参校验方法
     * 默认不做校验，返回空
     * 子类添加校验重写此方法
     * @param request
     * @param model
     * @param params
     * @return
     */
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        return null;
    }

    /**
     * 结果处理
     * @param request
     * @param response
     * @param model
     * @param params
     * @return T
     */
    protected abstract T handleBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object[] params) throws BusinessException;

    /*
     * 记录业务入口日志
     * @param request
     * @param model
     * @param params
     */
    private String entryLog(HttpServletRequest request, BasicModel model, Object...params)
    {
        try
        {
            String busiCode = UUID.randomUUID().toString();
            LoggerUtil.debug(getClass(), "Executor begin：URL={}，busiCode={}, Model={}，Params={}", new Object[]{request.getRequestURI(), busiCode, CommonUtil.isNotEmpty(model) ? CommonUtil.objectToJsonString(model) : null, CommonUtil.isNotEmpty(params) ? CommonUtil.objectToJsonString(params) : null});
            return busiCode;
        }
        catch (Exception e)
        {
            LoggerUtil.info(getClass(), e);
            return null;
        }
    }

    private void exitLog(HttpServletRequest request, String busiCode)
    {
        if (CommonUtil.isEmpty(busiCode))
        {
            return;
        }

        try
        {
            LoggerUtil.debug(getClass(), "Executor done：URL={}，busiCode={}", new Object[]{request.getRequestURI(), busiCode});
        }
        catch (Exception e)
        {
            LoggerUtil.info(getClass(), e);
        }
    }

}
