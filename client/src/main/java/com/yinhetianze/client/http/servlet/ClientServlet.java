package com.yinhetianze.client.http.servlet;

import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.security.custom.util.CustomSecurityHeader;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Order(3)
public class ClientServlet extends HttpServlet
{
    /*@Value("${server.context-path}")
    private String contextPath;
    @Value("${server.intf.address}")
    private String intfAddress;

    @Value("${server.intf.channel-code}")
    private String channelCode;

    @Value("${server.intf.channel-secret}")
    private String channelSecret;*/

    /**
     * 临时文件写入磁盘路径
     */
    /*@Value("${servlet.multipart.location:/var/tmp}")
    private String fileLocation;*/

    /**
     * 内存缓存上传文件的大小，超过则写到磁盘
     */
    /*@Value("${servlet.multipart.file-size-threshold:0}")
    private Integer fileSizeThreshold;*/

    /**
     * 上传文件最大限制
     */
    /*@Value("${servlet.multipart.max-file-size:}")
    private Integer maxFileSize;

    @Value("${servlet.multipart.allowed-file-type:.jpg,.png,.jpeg}")
    private String allowedFileType;

    @Value("${server.encode.disable:true}")
    private Boolean encodeDisabled;*/

    @Autowired
    private ApplicationPropertiesUtil applicationPropertiesUtil;

    @Autowired
    private HttpClientManager manager;

    /* ============================================= POST请求begin ==================================================== */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LoggerUtil.info(ClientServlet.class, "请求的服务端IP地址为：=============================="+ req.getLocalAddr());
        // 请求头参数组装
        Map<String, Object> requestHeaders = handleRequestHeaderParams(req, true);
        // 获取请求地址
        String url = handleRequestUrl(req);

        try
        {
            // post请求对应的contentType类型较多，有json，多媒体，表单文本数据等等
            String contentType = req.getContentType();
            LoggerUtil.info(ClientServlet.class,"发送post请求。请求地址为：[{}], 请求类型为：[{}]", new Object[]{url, contentType});

            if (isFileUpload(req))
            {
                // 发送文件上传请求
                postFileRequest(req, resp, url, requestHeaders);
            }
            else if (isJsonType(req))
            {
                // 表单以json数据格式提交
                postJsonRequest(req, resp, url, requestHeaders);
            }
            else
            {
                CloseableHttpClient client = manager.getHttpClient();

                // 普通表单提交post请求
                Map<String, Object> requestParams = handleRequestParams(req);// 请求参数组装
//                Map<Integer, String> resultMap = HttpClientUtil.doPostRequest(url, requestParams, requestHeaders);
                Map<Integer, String> resultMap = HttpClientUtil.doPost(client, url, requestParams, requestHeaders);

                if (CommonUtil.isNotEmpty(resultMap))
                {
                    handleResultMap(resp, resultMap);
                }
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ClientServlet.class, e);

            ResponseData respData = new ResponseData();
            respData.setResultInfo(new Result("9997", "系统异常，请联系管理员或稍后再试！"));
            handleResponse(resp, respData);
        }
    }

    private void postFileRequest(HttpServletRequest req, HttpServletResponse resp, String url, Map<String, Object> requestHeaders) throws Exception
    {
        CloseableHttpClient client = manager.getHttpClient();

//        Map<Integer, String> resultMap = HttpClientUtil.doPostRequest(url, req.getInputStream(), requestHeaders);
        Map<Integer, String> resultMap = HttpClientUtil.doPost(client, url, req.getInputStream(), requestHeaders);
        if (CommonUtil.isNotEmpty(resultMap))
        {
            handleResultMap(resp, resultMap);
        }
    }

    /**
     * 发送json数据请求
     * @param req
     * @param resp
     * @param url
     * @param requestHeaders
     */
    private void postJsonRequest(HttpServletRequest req, HttpServletResponse resp, String url, Map<String, Object> requestHeaders) throws Exception
    {
        CloseableHttpClient client = manager.getHttpClient();

//        Map<Integer, String> resultMap = HttpClientUtil.doPostRequest(url, req.getInputStream(), requestHeaders);
        Map<Integer, String> resultMap = HttpClientUtil.doPost(client, url, req.getInputStream(), requestHeaders);

        if (CommonUtil.isNotEmpty(resultMap))
        {
            handleResultMap(resp, resultMap);
        }
    }

    /**
     * 是否是json的请求类型
     * @param req
     * @return
     */
    private boolean isJsonType(HttpServletRequest req)
    {
        if (CommonUtil.isNotEmpty(req) && CommonUtil.isNotEmpty(req.getContentType()))
        {
            return req.getContentType().indexOf(ContentType.APPLICATION_JSON.getMimeType()) == 0;
        }
        return false;
    }

    /**
     * 判断是否是文件上传的请求
     * @param req
     * @return
     */
    private Boolean isFileUpload(HttpServletRequest req)
    {
        if (CommonUtil.isNotEmpty(req) && CommonUtil.isNotEmpty(req.getContentType()))
        {
            return req.getContentType().indexOf(ContentType.MULTIPART_FORM_DATA.getMimeType()) == 0;
        }

        return false;
    }
    /* ========================================================= POST请求end ========================================== */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // 请求头参数组装
        Map<String, Object> requestHeaders = handleRequestHeaderParams(req);
        // 请求参数组装
        Map<String, Object> requestParams = handleRequestParams(req);
        // 接口地址组装
        String url = handleRequestUrl(req);

        try
        {
            String contentType = req.getContentType();
            LoggerUtil.info(ClientServlet.class,"发送GET请求。请求地址为：[{}], 请求类型为：[{}]", new Object[]{url, contentType});

            CloseableHttpClient client = manager.getHttpClient();
            // 远程调用
//            Map<Integer, String> resultMap = HttpClientUtil.doGetRequest(url, requestParams, requestHeaders); // 请求状态码=请求相应内容
            Map<Integer, String> resultMap = HttpClientUtil.doGet(client, url, requestParams, requestHeaders); // 请求状态码=请求相应内容
            if (CommonUtil.isNotEmpty(resultMap))
            {
                // 处理结果
                handleResultMap(resp, resultMap);
            }
        }
        catch (Exception e)
        {
            // 记录异常日志
            LoggerUtil.error(ClientServlet.class, e);

            // 错误处理
            ResponseData data = new ResponseData();
            data.setResultInfo(new Result("9997", "系统错误，请稍后再试"));
            handleResponse(resp, data);
        }
    }

    /**
     * 处理请求地址
     * @param request
     * @return
     */
    private String handleRequestUrl(HttpServletRequest request)
    {
        StringBuffer sb = new StringBuffer();
        if (CommonUtil.isNotEmpty(request))
        {
//            String requestContextPath = contextPath;
            String requestContextPath = applicationPropertiesUtil.getStrProp("server.context-path", CommonConstant.CHAR_SLASH);
            Asserts.notEmpty(requestContextPath, "The configuration [server.context-path] can not be null......");

            String requestURI = request.getRequestURI();
            LoggerUtil.info(ClientServlet.class, "当前请求访问的地址是：{}", new Object[]{requestURI});

            String intfAddress = applicationPropertiesUtil.getStrProp("server.intf.address");
            Asserts.notEmpty(requestContextPath, "The configuration [server.intf.address] can not be null......");
            sb.append(intfAddress);
            // contextPath是否是/，是则直接使用requestUri，否则将contextPath消除
            if (!"/".equalsIgnoreCase(requestContextPath))
            {
                requestURI = requestURI.replace(requestContextPath, "");
            }
            sb.append(requestURI);
        }
        return sb.toString();
    }

    /**
     * 处理请求参数
     * @param request
     * @return
     */
    private Map<String, Object> handleRequestParams(HttpServletRequest request)
    {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        if (CommonUtil.isNotEmpty(request))
        {
            String paramName = null;
            // 是否需要urldecode的标志
            Enumeration<String> params = request.getParameterNames();
            while(params.hasMoreElements())
            {
                paramName = params.nextElement();
                /*try
                {
                    // 对地址参数进行解码处理
                    paramMap.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }*/

                paramMap.put(paramName, request.getParameter(paramName));
            }
        }
        return paramMap;
    }

    /**
     * 处理请求头参数
     * @param request
     * @return
     */
    private Map<String, Object> handleRequestHeaderParams(HttpServletRequest request)
    {
        return handleRequestHeaderParams(request, false);
    }

    /**
     * 处理请求头参数
     * @param request
     * @return
     */
    private Map<String, Object> handleRequestHeaderParams(HttpServletRequest request, Boolean needRequestHead)
    {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        if (needRequestHead)
        {
            // 前端传递的请求头参数
            requestHeadParams(request, paramMap);
        }
        // 系统客户端需要定制传递的请求头参数
        customHeadParams(request, paramMap);

        // 请求时需要剔除Content-Length
        paramMap.remove("content-length");
        return paramMap;
    }

    /**
     * 自定义客户端必传请求头参数
     * 用于客户端校验
     * @param request
     * @param paramMap
     */
    private void customHeadParams(HttpServletRequest request, Map<String, Object> paramMap)
    {
        String channelCode = applicationPropertiesUtil.getStrProp("server.intf.channel-code");
        String channelSecret = applicationPropertiesUtil.getStrProp("server.intf.channel-secret");
        Assert.hasText(channelCode, "渠道编码不能为空");
        Assert.hasText(channelSecret, "渠道秘钥不能为空");
        String remoteAddress = request.getRemoteAddr();
//        String remoteAddress = "127.0.0.1";
        Long timestamp = new Date().getTime();

        StringBuffer sb = new StringBuffer();
        String sign = sb.append(channelCode).append(CommonConstant.CHAR_COLON) // 渠道编码
                .append(channelSecret).append(CommonConstant.CHAR_COLON) // 渠道秘钥
                .append(remoteAddress).append(CommonConstant.CHAR_COLON) // 请求放ip地址
                .append(timestamp) // 请求时间戳
                .toString();

        paramMap.put(CustomSecurityHeader.SECURITY_CHANNEL_CODE, channelCode);
        paramMap.put(CustomSecurityHeader.SECURITY_REQUEST_IP, remoteAddress);
//        paramMap.put(CustomSecurityHeader.SECURITY_CHANNEL_SIGN, sign);
        Boolean encodeDisabled = applicationPropertiesUtil.getBoolProp("server.encode.disable", true);
        paramMap.put(CustomSecurityHeader.SECURITY_CHANNEL_SIGN, encodeDisabled ? sign : MD5CoderUtil.encode(sign)); // 使用MD5加密
        paramMap.put(CustomSecurityHeader.SECURITY_CHANNEL_TIMESTAMP, timestamp);

        // 将请求中的token参数设置到请求头中
        //paramMap.put(CustomSecurityHeader.SECURITY_REQUEST_TOKEN, request.getParameter("token"));
        paramMap.put(CustomSecurityHeader.SECURITY_REQUEST_TOKEN,   request.getHeader("token"));
    }

    /**
     * 前端请求传递的请求头参数封装
     * @param request
     * @param paramMap
     */
    private void requestHeadParams(HttpServletRequest request, Map<String, Object> paramMap)
    {
        if (CommonUtil.isNotEmpty(request))
        {
            String headerName = null;
            Enumeration<String> headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements())
            {
                headerName = headerNames.nextElement();
                paramMap.put(headerName, request.getHeader(headerName));
            }
        }
    }

    /**
     * 处理响应数据
     * 当发生异常时或者业务访问失败时，根据业务编码或者状态码返回相应结果
     * 只有当200时直接返回服务端响应数据
     * @param response
     * @param data
     * @throws Exception
     */
    private void handleResultMap(HttpServletResponse response, Map<Integer, String> data) throws Exception
    {
        if (CommonUtil.isNotEmpty(data))
        {
            // 请求返回码与结果内容
            int code = data.keySet().iterator().next();
            String result = data.get(code);

            if (code == HttpStatus.OK.value())
            {
                // ok = 200可直接展示
                handleResponse(response, result);
            }
            else
            {
                // 非200需要作为错误展示
                ResponseData respData = new ResponseData();
                respData.setResultInfo(new Result(String.valueOf(code), HttpStatus.valueOf(code).getReasonPhrase()));
                handleResponse(response, respData);
            }
        }
    }

    /**
     * 直接处理数据
     * @param obj
     * @return
     */
    private void handleResponse(HttpServletResponse response, Object obj)
    {
        if (CommonUtil.isNotEmpty(response))
        {
            response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            response.setCharacterEncoding("UTF-8");

            PrintWriter pw = null;
            try
            {
                pw = response.getWriter();
                String result = obj instanceof String ? (String) obj : CommonUtil.objectToJsonString(obj);
                pw.write(result);
            }
            catch (IOException e)
            {
                LoggerUtil.error(ClientServlet.class, e);

                ResponseData respData = new ResponseData();
                respData.setResultInfo(new Result("9997", "系统异常，请联系管理员或稍后再试！"));
                handleResponse(response, respData);
            }
            finally
            {
                pw.flush();
                pw.close();
            }
        }
    }

}
