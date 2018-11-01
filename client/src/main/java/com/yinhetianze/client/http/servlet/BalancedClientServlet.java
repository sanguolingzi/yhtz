package com.yinhetianze.client.http.servlet;

import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.ResponseDataHandler;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.security.custom.util.CustomSecurityHeader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

//@Component
@Order(3)
public class BalancedClientServlet extends HttpServlet
{
    @Value("${server.context-path}")
    private String contextPath;

    @Value("${server.intf.channel-code}")
    private String channelCode;

    @Value("${server.intf.channel-secret}")
    private String channelSecret;

    @Value("${server.encode.disable:true}")
    private Boolean encodeDisabled;

    @Value("${server.is-client-balance:false}")
    private Boolean isClientBalance;

    /**
     * 服务端接口访问地址
     */
    @Value("${server.intf.address}")
    private String intfAddress;

    /**
     * 临时路径
     */
    @Value("${servlet.multipart.location}")
    private String location;

    /**
     * 单位K=1024B
     */
    @Value("${servlet.multipart.file-size-threshold:512}")
    private Integer fileSizeThreshld;

    /**
     * 上传文件最大限制，单位K=1024B
     */
    @Value("${servlet.multipart.max-file-size:2048}")
    private Integer maxFileSize;

    /**
     * 允许上传的文件类型
     */
    @Value("${servlet.multipart.allowed-file-type:.jpg,.jpeg,.png}")
    private String allowedFileType;

    /* ===================================================== 静态属性开始 ============================================= */
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";
    /* ===================================================== 静态属性结束 ============================================= */

    /**
     * 环境配置
     */
    @Autowired
    private Environment environment;

    @Autowired
    private ResponseDataHandler clientResponseDataHandler;

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ApplicationContextFactory.init(config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            // 获取请求地址
            String url = handleRequestUrl(req);
            LoggerUtil.debug(BalancedClientServlet.class, "当前请求的访问地址为：{}，方法为：{}", new Object[]{url, req.getMethod()});

            // 请求实体封装，请求头与请求体
            RequestEntity entity = handleRequestEntity(req, url, false);
            LoggerUtil.debug(BalancedClientServlet.class, "请求提封装完毕：{}", new Object[]{CommonUtil.objectToJsonString(entity)});

            // 服务调用，返回响应数据
            ResponseData data = clientResponseDataHandler.handleResponseData(url, entity, HttpMethod.GET);
            LoggerUtil.debug(BalancedClientServlet.class, "请求调用结束，结果为：{}", new Object[]{CommonUtil.objectToJsonString(data.getResultInfo())});

            // 输出结果
            writeResponseData(resp, data);
        }
        catch (Exception e)
        {
            LoggerUtil.error(BalancedClientServlet.class, e);
            writeResponseData(resp, new ResponseData(new Result("9996", "系统异常，请稍后再试")));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try
        {
            // 请求服务地址封装
            String url = handleRequestUrl(req);
            LoggerUtil.debug(BalancedClientServlet.class, "当前请求的访问地址为：{}，方法为：{}", new Object[]{url, req.getMethod()});

            // 请求实体封装
            RequestEntity entity = handleRequestEntity(req, url, true);
            LoggerUtil.debug(BalancedClientServlet.class, "请求体封装完毕：{}", new Object[]{entity.getBody()});

            // 服务调用
            ResponseData data = clientResponseDataHandler.handleResponseData(url, entity, HttpMethod.POST);
            LoggerUtil.debug(BalancedClientServlet.class, "请求{}调用结束，结果为：{}", new Object[]{url, CommonUtil.objectToJsonString(data.getResultInfo())});

            // 结果输出
            writeResponseData(resp, data);
        }
        catch (Exception e)
        {
            LoggerUtil.error(BalancedClientServlet.class, e);
            writeResponseData(resp, new ResponseData(new Result("9996", "系统异常，请稍后再试")));
        }
    }

    /* ================================================== 私有方法封装开始 =================================================================*/
    /*
     * 处理请求地址，根据请求uri获取对应的服务接口地址
     * @param request
     * @return
     */
    private String handleRequestUrl(HttpServletRequest request)
    {
        String requestUri = request.getRequestURI();
        StringBuffer sb = new StringBuffer();

        // 将请求地址去除客户端上下文，得到真实接口请求地址
        String clientContextPath = environment.getProperty("server.context-path", CommonConstant.CHAR_SLASH);
        if (CommonUtil.isNotEmpty(clientContextPath) && !CommonConstant.CHAR_SLASH.equals(clientContextPath))
        {
            requestUri = requestUri.replace(clientContextPath, "");
        }

        // 是否使用客户端负载均衡
        if (isClientBalance)
        {
            // 判断是否是使用https
            Boolean isSsl = environment.getProperty("server.ssl.enabled", Boolean.class, false);
            sb.append(isSsl ? HTTPS_PROTOCOL : HTTP_PROTOCOL); // 请求协议

            // 根据地址中的label信息获取服务名称
            String[] requestLabel = requestUri.split(CommonConstant.CHAR_SLASH); // 将请求地址分解，其中[1]为接口对应的服务信息
            if (CommonUtil.isNotEmpty(requestLabel))
            {
                // requestLable-server : customer-server
                String reqService = requestLabel[1];
                sb.append(reqService).append(CommonConstant.CHAR_HYPHEN).append("server");
            }

            // 根据环境profile配置，获取对应环境的服务名称
            String env = environment.getProperty("spring.profiles.active", ""); // 加载的环境信息，默认为空对应生产环境
            if (CommonUtil.isNotEmpty(env) && !env.equals("prod"))
            {
                sb.append(CommonConstant.CHAR_HYPHEN).append(env);
            }
        }
        else
        {
            sb.append(intfAddress);
        }

        sb.append(requestUri);
        return sb.toString();
    }

    /*
     * 设置请求实体，包括请求头参数和请求体
     * GET方法不会设置请求体，POST方法会通过请求contentType区分设置请求参数类型为json，输入流或者键值对
     * @param req
     * @param url
     * @param isUseBody
     * @return
     */
    private RequestEntity handleRequestEntity(HttpServletRequest req, String url, Boolean isUseBody) throws Exception
    {
        RequestEntity.BodyBuilder builder = RequestEntity.method(getRequestMethod(req), URI.create(url));

        // 处理请求头参数
        builder = requestHeadParams(req, builder); // 处理前端页面设置的请求头
        builder = customHeadParams(req, builder); // 处理客户端自定义封装请求头

        if (isUseBody)
        {
            // 处理请求体
            return handleRequestBody(req, builder);
        }

        return builder.build();
    }

    /*
     * 请求方法选择
     * 当前除了post就是get
     * @param req
     * @return
     */
    private HttpMethod getRequestMethod(HttpServletRequest req)
    {
        if ("post".equalsIgnoreCase(req.getMethod()))
        {
            return HttpMethod.POST;
        }
        else
        {
            return HttpMethod.GET;
        }
    }

    /*
     * 前端请求传递的请求头参数封装
     * @param request
     * @param builder
     */
    private RequestEntity.BodyBuilder requestHeadParams(HttpServletRequest request, RequestEntity.BodyBuilder builder)
    {
        if (CommonUtil.isNotEmpty(request) && CommonUtil.isNotEmpty(builder))
        {
            String headerName = null;
            Enumeration<String> headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements())
            {
                headerName = headerNames.nextElement();
                builder = builder.header(headerName, request.getHeader(headerName));
            }
        }

        return builder;
    }

    /*
     * 自定义客户端必传请求头参数
     * 用于客户端校验
     * @param request
     * @param builder
     */
    private RequestEntity.BodyBuilder customHeadParams(HttpServletRequest request, RequestEntity.BodyBuilder builder)
    {
        Assert.hasText(channelCode, "渠道编码不能为空");
        Assert.hasText(channelSecret, "渠道秘钥不能为空");
        String remoteAddress = request.getRemoteAddr();
        Long timestamp = new Date().getTime();

        StringBuffer sb = new StringBuffer();
        String sign = sb.append(channelCode).append(CommonConstant.CHAR_COLON) // 渠道编码
                .append(channelSecret).append(CommonConstant.CHAR_COLON) // 渠道秘钥
                .append(remoteAddress).append(CommonConstant.CHAR_COLON) // 请求放ip地址
                .append(timestamp) // 请求时间戳
                .toString();

        builder = builder.header(CustomSecurityHeader.SECURITY_CHANNEL_CODE, channelCode);
        builder = builder.header(CustomSecurityHeader.SECURITY_REQUEST_IP, remoteAddress);
        builder = builder.header(CustomSecurityHeader.SECURITY_CHANNEL_SIGN, encodeDisabled ? sign : MD5CoderUtil.encode(sign)); // 使用MD5加密
        builder = builder.header(CustomSecurityHeader.SECURITY_CHANNEL_TIMESTAMP, String.valueOf(timestamp));

        // 将请求中的token参数设置到请求头中
        builder = builder.header(CustomSecurityHeader.SECURITY_REQUEST_TOKEN,   request.getHeader("token"));
        return builder;
    }

    /*
     * 请求体封装
     * @param req
     * @param builder
     * @return
     * @throws Exception
     */
    private RequestEntity handleRequestBody(HttpServletRequest req, RequestEntity.BodyBuilder builder) throws Exception
    {
        try
        {
            if (isJsonType(req))
            {
                return jsonRequestBoty(req, builder);
            }
            else if (isFileUpload(req))
            {
                return fileRequestBody(req, builder);
            }
            else
            {
                // 普通参数拼接
                Map<String, Object> params = handleRequestParams(req);
                return builder.body(params);
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(BalancedClientServlet.class, e);
            throw e;
        }
    }

    /*
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

    /*
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

    /*
     * 封装请求入参，json格式数据封装
     * @param req
     * @param builder
     * @return
     * @throws Exception
     */
    private RequestEntity jsonRequestBoty(HttpServletRequest req, RequestEntity.BodyBuilder builder) throws Exception
    {
        // 获取前台的入参，并转化成string
        int length = req.getContentLength(); // 请求体大小
        byte[] bytes = new byte[length]; // 请求体字节数组
        req.getInputStream().read(bytes); // 将请求体内容都入到字节数组中
        String params = new String(bytes); // 字节数组转string，得到最终的请求参数

        // json处理
        return builder.body(params);
    }

    /*
     * 文件上传请求体封装
     * @param req
     * @param builder
     * @return
     * @throws Exception
     */
    private RequestEntity fileRequestBody(HttpServletRequest req, RequestEntity.BodyBuilder builder) throws Exception
    {
        // 批量文件上传
        if (isBatchUpload(req))
        {
            // 文件临时上传处理，字段名=字段值
            Map<String, Object> fileListMap = uploadTempFile(req);
            if (CommonUtil.isNotEmpty(fileListMap))
            {
                // restTemplate的请求体参数
                MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
                // 将文件上传处理返回的字段与字段值设置到请求体参数中
                Set<String> keys = fileListMap.keySet();
                for (String k : keys)
                {
                    // 批量上传时只有一个files字段，其中files是多个文件对象，restTemplate使用FileSysResource作为文件对象传输
                    for (FileSystemResource fn: ((List<FileSystemResource>) fileListMap.get(k)))
                    {
                        // 请求体参数对某一个字段是数组时有支持，统一使用add方法添加字段名与字段值，相同的字段名设置为数组类型的字段值
                        param.add(k, fn);
                    }
                }

                return builder.contentType(MediaType.MULTIPART_FORM_DATA).body(param);
            }
        }
        else
        {
            // 单个文件上传
            Map<String, Object> fileListMap = uploadTempFile(req);// 文件临时上传处理
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            if (CommonUtil.isNotEmpty(fileListMap))
            {
                // 单个文件上传时有额外的文件名参数，遍历文件处理返回的参数集合map，将参数与参数值一一对应的设置到请求体中
                Set<String> keys = fileListMap.keySet();
                for (String k : keys)
                {
                    // 集合字段只取第一个值，因为文件处理方法同时处理多文件上传的情况，统一返回的集合类型，单个文件就是集合中的第一个
                    param.add(k, fileListMap.get(k) instanceof List ? ((List) fileListMap.get(k)).get(0) : fileListMap.get(k));
                }
            }
            return builder.contentType(MediaType.MULTIPART_FORM_DATA).body(param);
        }

        return builder.build();
    }

    /*
     * 是否是批量上传，依据为请求地址是否包含批量标志“/batch”
     * @param req
     * @return
     */
    private boolean isBatchUpload(HttpServletRequest req)
    {
        String requestUri = req.getRequestURI();
        return requestUri.contains("/batch") ? true : false;
    }

    public Map<String, Object> uploadTempFile(HttpServletRequest request) throws Exception
    {
        List<FileSystemResource> fileList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        // 文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 上传文件大小，内存区大小，超过了写到磁盘
        factory.setSizeThreshold(fileSizeThreshld * 1024); // 配置单位为k，设置的时为b，所以需要乘以1024
        factory.setRepository(new File(location));

        // 文件上传对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 文件上传最大限制
        upload.setFileSizeMax(maxFileSize * 1024); // 配置单位为k，设置的时为b，所以需要乘以1024

        // 请求中的文件字段参数
        List<FileItem> items = upload.parseRequest(request);
        if (CommonUtil.isNotEmpty(items))
        {
            // 文件上传限制的文件类型，默认限制只能上传png，jpeg，jpg三中图片
            String[] types = allowedFileType.split(",");
            for (FileItem fi : items)
            {
                // 针对文件表单域处理
                if(!fi.isFormField())
                {
                    String fileName = null;
                    for (String t : types)
                    {
                        if (fi.getName().endsWith(t.trim()))
                        {
                            fileName = location + "/" + fi.getName();
                            fi.write(new File(fileName));// 输出文件
                            fileList.add(new FileSystemResource(fileName)); // 添加文件到参数中
                            result.put(fi.getFieldName(), fileList);
                            break;
                        }
                    }
                }
                else
                {
                    result.put(fi.getFieldName(), fi.getString("UTF-8"));
                }
            }
        }

        return result;
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
                try
                {
                    // 对地址参数进行解码处理
                    paramMap.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return paramMap;
    }

    /*
     * 将内容输出到响应结果中
     * @param response
     * @param data
     */
    private void writeResponseData(HttpServletResponse response, ResponseData data)
    {
        try
        {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter write = response.getWriter();
            write.write(CommonUtil.objectToJsonString(data));
        }
        catch (Exception e)
        {
            LoggerUtil.error(ClientResponseDataHandler.class, e);
        }
    }

}
