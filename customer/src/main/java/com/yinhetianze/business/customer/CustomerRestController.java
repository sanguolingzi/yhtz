package com.yinhetianze.business.customer;

import com.yinhetianze.business.customer.model.*;
import com.yinhetianze.business.product.model.ProductFresherRewardModel;
import com.yinhetianze.configurations.annotation.JSON;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by Administrator
 * on 2018/1/28.
 */
@RestController
@RequestMapping("customer")
public class CustomerRestController extends RestfulController
{
    /**---------------------------------POST START   ------------------------------------------------------------**/
    /**
     * 新增消费者/会员 信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping
    public Object addCustomerInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 修改消费者/会员 信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateCustomerInfo")
    public Object updateCustomerInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改消费者/会员 头像
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateCustomerPhoto")
    public Object updateCustomerPhotoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerPhotoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 删除消费者/会员 信息
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/deleteCustomer")
    public Object deleteCustomerInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody  BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteCustomerInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }

    /**
     * 消费者/会员 实名认证
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/checkRealName")
    public Object updateRealNameInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateRealNameInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 新增 消费者/会员 收货地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/addAddress")
    public Object addCustomerAddressExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerReceiveaddressModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 修改 消费者/会员 收货地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateAddress")
    public Object updateCustomerAddressExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerReceiveaddressModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 设置 消费者/会员 默认收货地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/updateDefaultAddress")
    public Object updateDefaultAddressExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerReceiveaddressModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateDefaultAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 删除 消费者/会员 收货地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/deleteCustomerAddress")
    public Object deleteCustomerAddressExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerReceiveaddressModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteCustomerAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 消费者/会员 注册
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value="/regeister")
    public Object regeisterExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiRegeisterModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("regeisterExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 登录
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/login")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object loginExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("loginExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }

    /**
     * 刷新token
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/refreshToken")
    public Object refreshUserTokenExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("refreshUserTokenExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }

    /**
     * 刷新用户二维码
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/refreshUserQrcode")
    public Object refreshUserQrcodeExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("refreshUserQrcodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }



    /**
     * 手机号直接登录/注册
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/loginBySmsCode")
    public Object loginBySmsCodeExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiRegeisterModel busiRegeisterModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("loginBySmsCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiRegeisterModel);
    }


    /**
     * qrcodesecret 登录
     * @param request
     * @param response
     * @return
     */
    //@PostMapping(value = "/loginBySign")
    public Object loginByQrcodeSecret(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiRegeisterModel busiRegeisterModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("loginBySignExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiRegeisterModel);
    }





    /**
     * 退出系统
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/loginOut")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object loginOutExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("loginOutExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }

    /**
     * 校验短信验证码
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/checkSmsCode")
    public Object checkSmsCodeExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiSmsCodeModel busiSmsCodeModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("checkSmsCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiSmsCodeModel);
    }


    /**
     * 消费者 忘记密码
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/forgetLoginPassword")
    public Object forgetLoginPasswordExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiUpdatePasswordModel busiUpdatePasswordModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("forgetLoginPasswordExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiUpdatePasswordModel);
    }

    /**
     * 消费者 修改登录密码
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/updateLoginPassword")
    public Object updateLoginPasswordExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiUpdatePasswordModel busiUpdatePasswordModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateLoginPasswordExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiUpdatePasswordModel);
    }

    /**
     * 消费者 修改支付密码
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/updatePayPassword")
    public Object updatePayPasswordExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiUpdatePasswordModel busiUpdatePasswordModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updatePayPasswordExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiUpdatePasswordModel);
    }

    /**
     * 消费者 修改手机号
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/updateCustomerPhone")
    public Object updateCustomerPhoneExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiUpdatePhoneModel busiUpdatePhoneModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerPhoneExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiUpdatePhoneModel);
    }

    /**
     * 消费者 新增收藏
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/addCollector")
    public Object addCustomerCollectorExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiCustomerCollectorModel busiCustomerCollectorModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerCollectorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCollectorModel);
    }


    /**
     * 消费者 删除收藏
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/delCollector")
    public Object deleteCustomerCollectorExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerCollectorModel busiCustomerCollectorModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteCustomerCollectorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCollectorModel);
    }


    /**
     * 消费者 新增反馈
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/feedback")
    public Object addCustomerFeedbackInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerFeedbackModel busiCustomerFeedbackModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("addCustomerFeedbackInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerFeedbackModel);
    }

    /**
     * 消费者 删除反馈
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/delFeedback")
    public Object deleteCustomerFeedbackExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody BusiCustomerFeedbackModel busiCustomerFeedbackModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteCustomerFeedbackExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerFeedbackModel);
    }

    /**
     * 批量 消费者 删除反馈
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/delFeedbackBatch")
    public Object deleteFeedbackBatchExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody  DeleteModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("deleteFeedbackBatchExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 处理用户合伙人状态
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/handlePartner")
    public Object updateCustomerPartnerExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody  BusiCustomerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerPartnerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 处理用户会员状态
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/handleMember")
    public Object updateCustomerMemberInfoExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody  BusiCustomerModel model)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("updateCustomerMemberInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**---------------------------------POST END ------------------------------------------------------------**/









     /**---------------------------------GET  START   ------------------------------------------------------------**/
    /**
     * 查询消费者/会员 信息
     * @param request
     * @param response
     * @return
     */
    @GetMapping
    @JSON(type=BusiCustomerModel.class,include = "id,account,phone,realName,nickName,sex,email,photoUrl,personQrcode,isMember,recommendCode")
    public Object getCustomerInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerModel busiCustomerModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerModel);
    }


    /**
     * 分页查询消费者/会员列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value="/page",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@JSON(type=BusiCustomerOrderModel.class,include = "id,account,phone,createTime,accountStatus,realName,nickName,orderCount,orderPrice")
    public Object getCustomerListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerPageModel busiCustomerPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerPageModel);
    }

    /**
     * 查询推荐信息
     * @param request
     * @param response
     * @param {"pageSize":10,"currentPage":1,"params":{"recommendId":22}}
     * @return
     */
    @GetMapping(value = "/recommend/page")
    @JSON(type= BusiCustomerRecommendRelationModel.class,include = "phone,id,createTime,isMember,showId")
    public Object getCustomerRecommendInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerRecommendRelationModel busiCustomerRecommendRelationModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerRecommendInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerRecommendRelationModel);
    }



    /**
     * 查询消费者/会员 收货地址
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/address")
    @JSON(type= BusiCustomerBankrollPojo.class,filter = "id,idCard,loginPassword,payPassword,cancelStatus,createTime,delFlag,updateTime,accountStatus,personQrcode")
    public Object getCustomerAddressExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerReceiveaddressModel busiCustomerReceiveaddressModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerAddressExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerReceiveaddressModel);
    }

    /**
     * 查询消费者/会员 收货地址列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/addressList")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object getCustomerAddressListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerReceiveaddressModel busiCustomerReceiveaddressModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerAddressListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerReceiveaddressModel);
    }


    /**
     * 获取短信验证码
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getSmsCode")
    public Object getSmsCodeExecutor(HttpServletRequest request, HttpServletResponse response, BusiSmsCodeModel busiSmsCodeModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getSmsCodeExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiSmsCodeModel);
    }

    /**
     * 查询消费者个人中心
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/center")
    //@JSON(type= BusiCustomerBankrollPojo.class,filter = "createTime,delFlag,updateTime")
    public Object getCustomerCenterInfoExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerCenterModel busiCustomerCenterModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerCenterInfoExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCenterModel);
    }

    /**
     * 查询用户参数设置完善情况
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/checkCustomerParam")
    public Object checkCustomerParamExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerCenterModel busiCustomerCenterModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("checkCustomerParamExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCenterModel);
    }




    /**
     * 查询 个人-奖励金
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getRecommendAmount",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @JSON(type= BusiCustomerRecommendRelationModel.class,include = "phone,id,createTime")
    public Object getRecommendAmountExecutor(HttpServletRequest request, HttpServletResponse response, BusiRecommendAmountInfoModel busiRecommendAmountInfoModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getRecommendAmountExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiRecommendAmountInfoModel);
    }


    /**
     * 查询 我的收藏
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/collector/page")
    public Object getCustomerCollectorListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerCollectorModel busiCustomerCollectorModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerCollectorListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCollectorModel);
    }

    /**
     * 查询 某商品或者店铺 是否收藏
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/collector")
    public Object getCustomerCollector(HttpServletRequest request, HttpServletResponse response, BusiCustomerCollectorModel busiCustomerCollectorModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerCollectorExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCollectorModel);
    }

    /**
     * 查询 用户商品收藏数量
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/collectorCount")
    public Object getCustomerCollectorCount(HttpServletRequest request, HttpServletResponse response, BusiCustomerCollectorModel busiCustomerCollectorModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerCollectorCountExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerCollectorModel);
    }



    /**
     * 查询 反馈详情
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/feedback")
    public Object getCustomerFeedbackExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerFeedbackModel busiCustomerFeedbackModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerFeedbackExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerFeedbackModel);
    }


    /**
     * 查询 反馈列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/feedback/page")
    //@JSON(type= BusiCustomerRecommendRelationModel.class,include = "phone,id,createTime")
    public Object getCustomerFeedbackListExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getCustomerFeedbackListExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerFeedbackPageModel);
    }


    /**
     * 后台管理 查询 反馈列表
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/feedbackManage/page")
    //@JSON(type= BusiCustomerRecommendRelationModel.class,include = "phone,id,createTime")
    public Object getFeedbackForManageExecutor(HttpServletRequest request, HttpServletResponse response, BusiCustomerFeedbackPageModel busiCustomerFeedbackPageModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getFeedbackForManageExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiCustomerFeedbackPageModel);
    }

    /**
     * 校验 手机号码是否已注册 1 未注册 0 已注册
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/checkPhone")
    public Object checkPhoneExecutor(HttpServletRequest request, HttpServletResponse response, BusiRegeisterModel busiRegeisterModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("checkPhoneExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiRegeisterModel);
    }

    //---------------------------------------wechat--------------------------------------------------------


    /**
     * 获取微信openId
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getWeChatOpenId")
    public Object getWechatOpenIdExecutor(HttpServletRequest request, HttpServletResponse response, BusiWechatModel busiWechatModel)
    {
        // 执行业务
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getWechatOpenIdExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiWechatModel);
    }

    /**
     * 获取微信授权url
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getWechatLoginUrl")
    public Object getWechatLoginUrlExecutor(HttpServletRequest request, HttpServletResponse response,BusiWechatModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getWechatLoginUrlExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);

    }

    /**
     * 获取微信授权与平台用户绑定结果
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getWechatAuthResult")
    public Object getWechatAuthResult(HttpServletRequest request, HttpServletResponse response, BusiWechatModel busiWechatModel)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getWechatAuthResultExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiWechatModel);
    }


    /**
     * 微信分享获取必要参数
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/wxShare")
    public Object getWechatShareUrlExecutor(HttpServletRequest request, HttpServletResponse response,BusiWechatModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getWechatShareUrlExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response,model);
    }

    /**
     * 微信登陆
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/wechatLogin")
    public Object wechatLoginExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiWechatModel busiWechatModel)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("wechatLoginExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiWechatModel);
    }


    /**
     * 微信注册
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/WechatRegeister")
    public Object wechatRegweisterExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody  BusiRegeisterModel busiRegeisterModel)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("wechatRegeisterExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, busiRegeisterModel);
    }


    /**
     * 微信绑定平台用户
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/wxBind")
    public Object wechatBindExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiWechatModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("wechatBindExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 微信首次登录需要绑定手机号
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/wxBindPhone")
    public Object wechatBindPhoneExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiRegeisterModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("wechatBindPhoneExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 解除微信绑定
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/wxCancleBind")
    public Object wechatCancleBindExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiWechatModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("wechatCancleBindExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }


    /**
     * 游戏用户绑定已有账号
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/gameBindCustomer")
    public Object gameIdBindCustomerExecutor(HttpServletRequest request, HttpServletResponse response, @RequestBody BusiRegeisterModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("gameIdBindCustomerExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 查询账户是否兑换过U币兑换
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/productFresherReward")
    public Object getProductFresherRewardExecutor(HttpServletRequest request, HttpServletResponse response,  ProductFresherRewardModel model)
    {
        BusinessExecutor<String> executor = (BusinessExecutor<String>)ApplicationContextFactory.getBean("getProductFresherRewardExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }



    //---------------------------------------wechat--------------------------------------------------------

    //test


    /**
     * 测试微信授权回调以及登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/weChatCallBack")
    public void testWxCallBack(HttpServletRequest request, HttpServletResponse response,@RequestParam("code") String code)
    {
        StringBuilder sb = new StringBuilder();
        //sb.append("<html lang=\"en\">");
        //sb.append("<head>");
        //sb.append("<title>模拟扫码登陆</title>");
        //sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        //sb.append("</head>");
        //样式内容
        sb.append("<script src=\"https://code.jquery.com/jquery-3.1.1.min.js\"></script>");
        //样式结尾
        //.append("<body>");
        //sb.append("<input type=\"button\" value=\"\" id=\"wx\"/>");
        //sb.append("</body>");
        //一个table
        sb.append("<script>");
        sb.append("console.info(\"href:\"+window.location.href);");
        sb.append("var localUrl = window.location.href;");
        sb.append("var arr = localUrl.split('code=');");
        sb.append("if(arr.length <= 1) {");
        //sb.append("    //return;");
        sb.append("}");
        sb.append("arr = arr[1].split('&')[0];");
        sb.append("console.info(\"arr:\"+arr);");
        sb.append("$.ajax({");
        sb.append("url:\"http://h6ez5t.natappfree.cc/customer/getWechatAuthResult\",");
        sb.append("data:\"code=\"+arr,");
        sb.append("success:function(returnData){");
        sb.append("if(returnData.resultInfo.code == '0000'){");
        sb.append("var customerCode = returnData.data.customerCode;");
        sb.append("console.info(\"customerCode:\"+customerCode);");
        sb.append("var state = returnData.data.state;");
        sb.append("console.info(\"state:\"+state);");
        sb.append("if(state == \"3\"){");
        sb.append("       alert(\"授权失败!\");");
        sb.append("}else if(state == \"2\"){");
        sb.append("$.ajax({");
        sb.append("        type:\"post\",");
        sb.append("        headers: {\"Content-Type\":\"application/json\"},");
        sb.append("        url:\"http://cavfcz.natappfree.cc/customer/wechatRegeister\",");
        sb.append("        data:JSON.stringify({");
        sb.append("            customerCode:customerCode,");
        sb.append("            phone:\"15873452258\"");
        sb.append("          }),");
        sb.append("        dataType:\"json\",");
        sb.append("success:function(regeisterData){");
        sb.append("    console.info(\"注册结果:\"+JSON.stringify(regeisterData));");
        sb.append("}");
        sb.append("	 })");
        sb.append(" }else if(state == \"4\"){");
        sb.append("    console.info(\"需要绑定手机号\");");
        sb.append(" }else if(state == \"1\"){");
        sb.append("     console.info(\"可以登录\");");
        sb.append("}");
        sb.append(" }else{");
        sb.append("    console.info(\"returnData:\"+JSON.stringify(returnData));");
        sb.append("}");
        sb.append(" }");
        sb.append("})");
        sb.append("</script>");
        PrintWriter out = null;
        try{

            sb = new StringBuilder();
            sb.append("<script  language='javascript'>alert('123')</script>");
            response.setContentType("text/javascript;charset=utf-8");
            out = response.getWriter();
            out.print(sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 微信测试用
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping(value = "/testToken")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        //if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
            System.out.println("微信服务验证成功！"+echostr);
        //}else {
        //    out.print(echostr);
        //    System.out.println("微信服务验证失败！"+echostr);
        //}
        // out.flush();
        //out.close();
        //out = null;
    }

    public static void main(String[] args) throws Exception{

        System.out.println(URLEncoder.encode("http://x26n22.natappfree.cc/customer/weChatCallBack","utf-8"));

    }





    /**---------------------------------GET  END   ------------------------------------------------------------**/


    /**---------------------------------PUT  START   ------------------------------------------------------------**/

    /**---------------------------------PUT  START   ------------------------------------------------------------**/



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
}
