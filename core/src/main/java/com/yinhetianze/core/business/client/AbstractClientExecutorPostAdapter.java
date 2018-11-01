package com.yinhetianze.core.business.client;

import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.security.custom.util.CustomSecurityHeader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.entity.ContentType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

/**
 * 客户端Post请求适配器
 * @param <T>
 */
public abstract class AbstractClientExecutorPostAdapter<T> extends AbstractClientExecutor<T>
{

    protected final HttpEntity<?> handleFileUploadRequestEntity(HttpServletRequest request, String uri) throws Exception
    {
        RequestEntity.BodyBuilder builder = RequestEntity.method(HttpMethod.POST, URI.create(uri));

        // 处理请求头参数
        // 处理前端页面设置的请求头
        builder = requestHeadParams(request, builder);
        // 处理客户端自定义封装请求头
        builder = customHeadParams(request, builder);

        // 文件上传请求体处理
        if (isBatchUpload(request))
        {
            return batchFileUploadRequestBody(request, builder);
        }
        else
        {
            return fileUploadRequestBody(request, builder);
        }

    }
    /**
     * 执行接口调用
     * @param serverUrl
     * @param entity
     * @return
     */
    @Override
    public ResponseData<T> executeInvoke(String serverUrl, HttpEntity<?> entity, Object...uriParams)
    {
//        ResponseEntity<ResponseData> respEntity = restTemplate.exchange(serverUrl, HttpMethod.POST, entity, ResponseData.class, uriParams);
//        return handleResponseData(respEntity);
        try
        {
            ResponseEntity<ResponseData> respEntity = restTemplate.exchange(serverUrl, HttpMethod.POST, entity, ResponseData.class, uriParams);
            return handleResponseData(respEntity);
        }
        // http错误，意味着在过滤器中校验时返回的请求错误
        catch (HttpClientErrorException e)
        {
            ResponseData data = new ResponseData();
            Result result = new Result(String.valueOf(e.getStatusCode()), e.getStatusCode().getReasonPhrase());
            data.setResultInfo(result);
            return data;
        }
        // 统一抛出进行处理
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * 处理post请求参数
     * 使用inputstream流处理请求数据
     * 适用于类型为application/json的请求
     * @param request
     * @param uri
     * @return
     * @throws Exception
     */
    protected final HttpEntity<?> handleInputStreamRequestEntity(HttpServletRequest request, String uri) throws Exception
    {
        // 默认只支持application/json的post
        RequestEntity.BodyBuilder builder = RequestEntity.method(HttpMethod.POST, URI.create(uri))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        // 处理请求头参数
        // 处理前端页面设置的请求头
        builder = requestHeadParams(request, builder);
        // 处理客户端自定义封装请求头
        builder = customHeadParams(request, builder);

        // 获取前台的入参，并转化成string
        // 请求体大小
        int length = request.getContentLength();
        // 请求体字节数组
        byte[] bytes = new byte[length];
        // 将请求体内容都入到字节数组中
        request.getInputStream().read(bytes);
        // 字节数组转string，得到最终的请求参数
        String params = new String(bytes, "UTF-8");
        LoggerUtil.info(AbstractClientExecutorPostAdapter.class, "Request params : [{}]", new Object[]{params});

        // json处理
        return builder.body(params);
    }

    /**
     * 普通地址栏参数请求与表单文本参数提交
     * @param request
     * @param uri
     * @return
     * @throws Exception
     */
    protected final HttpEntity<?> handleParamRequestEntity(HttpServletRequest request, String uri) throws Exception
    {
        RequestEntity.BodyBuilder builder = RequestEntity.method(HttpMethod.POST, URI.create(uri));

        // 处理请求头参数
        builder = requestHeadParams(request, builder); // 处理前端页面设置的请求头
        builder = customHeadParams(request, builder); // 处理客户端自定义封装请求头

        // 请求参数
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

        return builder.body(paramMap);
    }

    /**
     * 文件上传请求体封装，适用于类型为multipart/form-data的请求
     * 单个文件上传封装方法
     * @param req
     * @param builder
     * @return
     * @throws Exception
     */
    private HttpEntity<?> fileUploadRequestBody(HttpServletRequest req, RequestEntity.BodyBuilder builder) throws Exception
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

    /**
     * 文件上传请求体封装，适用于类型为multipart/form-data的请求
     * 批量文件上传封装方法
     * @param req
     * @param builder
     * @return
     * @throws Exception
     */
    private HttpEntity<?> batchFileUploadRequestBody(HttpServletRequest req, RequestEntity.BodyBuilder builder) throws Exception
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
                if ("files".equalsIgnoreCase(k))
                {
                    for (FileSystemResource fn: ((List<FileSystemResource>) fileListMap.get(k)))
                    {
                        // 请求体参数对某一个字段是数组时有支持，统一使用add方法添加字段名与字段值，相同的字段名设置为数组类型的字段值
                        param.add(k, fn);
                    }
                }
                else
                {
                    param.add(k, fileListMap.get(k));
                }

                /*for (FileSystemResource fn: ((List<FileSystemResource>) fileListMap.get(k)))
                {
                    // 请求体参数对某一个字段是数组时有支持，统一使用add方法添加字段名与字段值，相同的字段名设置为数组类型的字段值
                    param.add(k, fn);
                }*/
            }

            return builder.contentType(MediaType.MULTIPART_FORM_DATA).body(param);
        }

        return builder.contentType(MediaType.MULTIPART_FORM_DATA).build();
    }

    /**
     * 是否是批量上传，依据为请求地址是否包含批量标志“/batch”
     * @param req
     * @return
     */
    private boolean isBatchUpload(HttpServletRequest req)
    {
        String requestUri = req.getRequestURI();
        return requestUri.contains("/batch") ? true : false;
    }

    /*
     * 文件上传至客户端服务器的方法
     * @param request
     * @return
     * @throws Exception
     */
    private Map<String, Object> uploadTempFile(HttpServletRequest request) throws Exception
    {
        List<FileSystemResource> fileList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        // 文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 上传文件大小，内存区大小，超过了写到磁盘
        Integer fileSizeThreshld = applicationPropertiesUtil.getIntProp("servlet.multipart.file-size-threshold", 512);
        factory.setSizeThreshold(fileSizeThreshld * 1024); // 配置单位为k，设置的时为b，所以需要乘以1024
        String location = applicationPropertiesUtil.getStrProp("servlet.multipart.location");
        factory.setRepository(new File(location));

        // 文件上传对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 文件上传最大限制
        Integer maxFileSize = applicationPropertiesUtil.getIntProp("servlet.multipart.max-file-size", 2048);
        upload.setFileSizeMax(maxFileSize * 1024); // 配置单位为k，设置的时为b，所以需要乘以1024

        // 请求中的文件字段参数
        List<FileItem> items = upload.parseRequest(request);
        if (CommonUtil.isNotEmpty(items))
        {
            // 文件上传限制的文件类型，默认限制只能上传png，jpeg，jpg三中图片
            String allowedFileType = applicationPropertiesUtil.getStrProp("servlet.multipart.allowed-file-type", ".jpg,.jpeg,.png,.gif");
            String[] types = allowedFileType.split(",");
            for (FileItem fi : items)
            {
                // 针对文件表单域处理
                if(!fi.isFormField())
                {
                    String fileName = null;
                    for (String t : types)
                    {
                        if (fi.getName().toLowerCase().endsWith(t.trim()))
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
     * @param request
     * @param builder
     */
    protected RequestEntity.BodyBuilder customHeadParams(HttpServletRequest request, RequestEntity.BodyBuilder builder)
    {
        String channelCode = applicationPropertiesUtil.getStrProp("server.intf.channel-code");
        String channelSecret = applicationPropertiesUtil.getStrProp("server.intf.channel-secret");
        Assert.hasText(channelCode, "渠道编码不能为空");
        Assert.hasText(channelSecret, "渠道秘钥不能为空");
        String remoteAddress = request.getRemoteAddr();
        Long timestamp = System.currentTimeMillis();

        StringBuffer sb = new StringBuffer();
        String sign = sb.append(channelCode).append(CommonConstant.CHAR_COLON) // 渠道编码
                .append(channelSecret).append(CommonConstant.CHAR_COLON) // 渠道秘钥
                .append(remoteAddress).append(CommonConstant.CHAR_COLON) // 请求放ip地址
                .append(timestamp) // 请求时间戳
                .toString();

        Boolean encodeDisabled = applicationPropertiesUtil.getBoolProp("server.encode.disable", true);
        builder = builder.header(CustomSecurityHeader.SECURITY_CHANNEL_CODE, channelCode);
        builder = builder.header(CustomSecurityHeader.SECURITY_REQUEST_IP, remoteAddress);
        builder = builder.header(CustomSecurityHeader.SECURITY_CHANNEL_SIGN, encodeDisabled ? sign : MD5CoderUtil.encode(sign)); // 使用MD5加密
        builder = builder.header(CustomSecurityHeader.SECURITY_CHANNEL_TIMESTAMP, String.valueOf(timestamp));

        // 将请求中的token参数设置到请求头中
        builder = builder.header(CustomSecurityHeader.SECURITY_REQUEST_TOKEN,   request.getHeader("token"));
        return builder;
    }

    /*
     * 是否是json的请求类型
     * @param req
     * @return
     */
    protected boolean isJsonType(HttpServletRequest req)
    {
        if (CommonUtil.isNotEmpty(req) && CommonUtil.isNotEmpty(req.getContentType()))
        {
            return req.getContentType().indexOf(ContentType.APPLICATION_JSON.getMimeType()) == 0;
        }
        return false;
    }
}
