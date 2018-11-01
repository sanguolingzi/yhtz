package com.yinhetianze.client.business;

import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.client.ClientExecutor;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ClientController
{
    /** ===================== 客户接口 ================================*/
//    @RequestMapping(value = "/customer/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @RequestMapping(value = "/cust/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData customerPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("customerGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "/cust", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData customerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("customerGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "customer/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "/cust/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData customerGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("customerPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "customer/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
@RequestMapping(value = "/cust/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData customerUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("customerUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    /** ===================== 商品接口 ================================*/
//    @RequestMapping(value = "product/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @RequestMapping(value = "/prod/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData productPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("productGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "/prod", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData productRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("productGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "product/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "/prod/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData productGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("productPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "product/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @RequestMapping(value = "/prod/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData productUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("productUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    /** ===================== 订单接口 ================================*/
    @RequestMapping(value = "/ord/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
//    @RequestMapping(value = "order/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData orderPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("orderGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "/ord", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData orderRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("orderGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "order/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "/ord/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData orderGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("orderPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "order/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @RequestMapping(value = "/ord/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData orderUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("orderUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    /** ===================== 店铺接口 ================================*/
//    @RequestMapping(value = "shop/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @RequestMapping(value = "/sp/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData shopPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("shopGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "/sp", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData shopRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("shopGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "shop/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "/sp/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData shopGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("shopPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "shop/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @RequestMapping(value = "/sp/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData shopUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("shopUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    /** ===================== 系统接口 ================================*/
//    @RequestMapping(value = "back/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @RequestMapping(value = "/sys/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData backPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("sysGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "/sys", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData backRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("sysGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "back/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "/sys/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData backGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("sysPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "back/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @RequestMapping(value = "/sys/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData backUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("sysUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    /** ===================== 定时任务接口 ================================*/
//    @RequestMapping(value = "task/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @RequestMapping(value = "/sche/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData taskPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("taskGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "/sche", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData taskRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("taskGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "task/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "sche/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData taskGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("taskPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "task/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @RequestMapping(value = "sche/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData taskUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("taskUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    /** ===================== 账务接口 ================================*/
//    @RequestMapping(value = "billing/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @RequestMapping(value = "bill/{pathParam}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData billingPostRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("billingGetRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

    @RequestMapping(value = "bill", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData billingRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("billingGetRequestExecutor");
        return executor.execute(request, response, null);
    }

//    @RequestMapping(value = "billing/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @RequestMapping(value = "bill/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseData billingGetRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("billingPostRequestExecutor");
        return executor.execute(request, response, pathParam);
    }

//    @RequestMapping(value = "billing/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @RequestMapping(value = "bill/{pathParam}", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseData billingUploadRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathParam)
    {
        ClientExecutor executor = (ClientExecutor) ApplicationContextFactory.getBean("billingUploadRequestExecutor");
        return executor.execute(request, response, pathParam);
    }
}
