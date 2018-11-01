package com.yinhetianze.business.customer.executor;

import com.github.binarywang.wxpay.bean.request.WxEntPayRequest;
import com.github.binarywang.wxpay.bean.result.WxEntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.service.busi.CustomerDrawQueueBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerDrawrecordBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerDrawrecordInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.*;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 新增消费者 微信提现信息
 * 1 首先增加一条 状态为 未成功的 提现记录
 * 2 调用微信支付扣款成功之后 修改 提现记录状态 同时 修改账户资金
 */

@Component
public class AddCustomerDrawrecordWeChatExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerDrawrecordBusiService customerDrawrecordBusiServiceImpl;

    @Autowired
    private CustomerDrawrecordInfoService customerDrawrecordInfoServiceImpl;

    @Autowired
    private CustomerDrawQueueBusiService customerDrawQueueBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private WxPayService payService;

    @Autowired
    private WxPayService appPayService;

    @Autowired
    private RedisManager redisManager;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerDrawrecordModel.getToken());
        Map<String,Object> paraMap = new HashMap<>();

        BusiCustomerBankrollPojo busiCustomerBankrollPojo = null;
        BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo = null;
        String openId = null;
        try {
            long timeToLive = redisManager.getRedisTemplate().getExpire(tokenUser.getId()+"_draw");
            //已超时
            if(timeToLive == -1 || timeToLive == -2){
                boolean setIfAbsent = redisManager.getRedisTemplate().
                        opsForValue().setIfAbsent(tokenUser.getId()+"_draw",System.currentTimeMillis());
                if(setIfAbsent) {
                    redisManager.getRedisTemplate().expire(tokenUser.getId() + "_draw", 20, TimeUnit.SECONDS);
                }else {
                    paraMap.put("result","falied");
                    paraMap.put("code","2");//有正在处理的操作
                    paraMap.put("msg","操作频繁");
                    return paraMap;
                }
            }else{
                paraMap.put("result","falied");
                paraMap.put("code","2");//有正在处理的操作
                paraMap.put("msg","操作频繁");
                return paraMap;
            }
            BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
            busiCustomerWechatPojo.setCustomerId(tokenUser.getId());
            busiCustomerWechatPojo.setIdType(busiCustomerDrawrecordModel.getIdType());
            busiCustomerWechatPojo = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);
            //判断当前用用户是否有绑定的微信openId
            if(busiCustomerWechatPojo == null){
                paraMap.put("result","falied");
                paraMap.put("code","3");//未绑定微信openid
                return paraMap;
            }
             //查询用户余额
            busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());

            // 先计算手续费 若 提现金额+手续费 > 账户余额  则通过判断
            //手续费百分比
            BigDecimal serviceCharge  =sysPropertiesUtils.getDecimalValue("serviceCharge",new BigDecimal("1"));
            //用户发起提现金额
            BigDecimal drawAmount = busiCustomerDrawrecordModel.getDrawAmount();
            //计算手续费
            BigDecimal serviceChargeResult = drawAmount.multiply(serviceCharge.movePointLeft(2)).setScale(2,BigDecimal.ROUND_HALF_UP);
            //最终账户需要扣除的金额
            BigDecimal finalAmount = drawAmount.add(serviceChargeResult);

            //用于属性复制
            busiCustomerDrawrecordModel.setFinalAmount(finalAmount);
            busiCustomerDrawrecordModel.setServiceCharge(serviceChargeResult);


            /**
             * 判断账户余额 和 提现申请额度
             */
            if(busiCustomerBankrollPojo.getAmount().movePointLeft(2).compareTo(finalAmount)>=0) {

                //提现间隔时间
                int dayInterval = sysPropertiesUtils.getIntValue("dayInterval",120);
                //提现次数
                int drawCount = sysPropertiesUtils.getIntValue("drawCount",5);

                Map<String,Object> pMap = new HashMap();
                pMap.put("dayInterval",dayInterval);
                pMap.put("bankrollId",busiCustomerBankrollPojo.getId());
                int count = customerDrawrecordInfoServiceImpl.selectCount(pMap);
                if(count >= drawCount){
                    throw new BusinessException("您于"+dayInterval+"天内提现超过"+drawCount+"次");
                }


                busiCustomerDrawrecordPojo = new BusiCustomerDrawrecordPojo();
                BeanUtils.copyProperties(model, busiCustomerDrawrecordPojo);
                busiCustomerDrawrecordPojo.setPayType((short)0);
                customerDrawrecordBusiServiceImpl.AddCustomerDrawrecordInfoForWechat(busiCustomerBankrollPojo, busiCustomerDrawrecordPojo);
                //openId
                openId = busiCustomerWechatPojo.getOpenId();
                String drawNumber = busiCustomerDrawrecordPojo.getDrawNumber();

                WxEntPayRequest wxEntPayRequest = WxEntPayRequest.newBuilder().openid(openId).
                        partnerTradeNo(drawNumber).
                        checkName("NO_CHECK").amount(busiCustomerDrawrecordModel.getDrawAmount().movePointRight(2).intValue()).description("友旗有品").spbillCreateIp("192.168.8.196").build();
                //busiCustomerDrawrecordModel.getDrawAmount().movePointRight(2).intValue()

                WxEntPayResult wxEntPayResult = null;

                if(busiCustomerDrawrecordModel.getIdType() == 1){
                    wxEntPayResult = payService.entPay(wxEntPayRequest);
                }else if(busiCustomerDrawrecordModel.getIdType() == 2){
                    wxEntPayResult = appPayService.entPay(wxEntPayRequest);
                }

                if(wxEntPayResult == null){
                    //busiCustomerDrawrecordPojo.setErr_code(wxEntPayResult.getErrCode());
                    busiCustomerDrawrecordPojo.setErr_code_des("wxEntPayResult is null");
                    busiCustomerDrawrecordPojo.setAuditStatus((short)2);
                    busiCustomerDrawrecordPojo.setOpenId(openId);
                    customerDrawrecordBusiServiceImpl.updateInfo(busiCustomerDrawrecordPojo);

                    paraMap.put("result", "falied");
                    paraMap.put("code", "1");//微信支付失败
                    paraMap.put("msg","提现失败!");
                    return paraMap;
                }

                //微信接口返回状态

                if ("SUCCESS".equals(wxEntPayResult.getReturnCode())) {

                    //微信业务返回状态
                    if("SUCCESS".equals(wxEntPayResult.getResultCode())){
                        //修改 账户余额 修改提现状态
                        busiCustomerDrawrecordPojo.setPaymentNo(wxEntPayResult.getPaymentNo());
                        busiCustomerDrawrecordPojo.setPaymentTime(wxEntPayResult.getPaymentTime());
                        busiCustomerDrawrecordPojo.setAuditStatus((short)0);
                        busiCustomerDrawrecordPojo.setOpenId(openId);
                        customerDrawrecordBusiServiceImpl.
                                updateCustomerDrawrecordInfoForWechat(busiCustomerBankrollPojo, busiCustomerDrawrecordPojo);
                        paraMap.put("result", "success");
                        paraMap.put("code", "0");
                        return paraMap;
                    }
                } else {
                    busiCustomerDrawrecordPojo.setErr_code(wxEntPayResult.getErrCode());
                    busiCustomerDrawrecordPojo.setErr_code_des(wxEntPayResult.getErrCodeDes());
                    busiCustomerDrawrecordPojo.setAuditStatus((short)2);
                    busiCustomerDrawrecordPojo.setOpenId(openId);
                    customerDrawrecordBusiServiceImpl.updateInfo(busiCustomerDrawrecordPojo);

                    paraMap.put("result", "falied");
                    paraMap.put("code", "1");//微信支付失败
                    paraMap.put("msg",wxEntPayResult.getErrCodeDes());
                    return paraMap;
                }
            }

        }catch (Exception e){
            LoggerUtil.error(AddCustomerDrawrecordWeChatExecutor.class,e.getMessage());
            if(e instanceof WxPayException){
                WxPayException wxPayException = (WxPayException)e;

                String errorCode = wxPayException.getErrCode();
                String error = null;
                if("SYSTEMERROR".equals(errorCode)){

                    busiCustomerDrawrecordPojo.setAuditStatus((short)1);
                    busiCustomerDrawrecordPojo.setOpenId(openId);
                    customerDrawrecordBusiServiceImpl.
                            updateCustomerDrawrecordInfoForWechat(busiCustomerBankrollPojo, busiCustomerDrawrecordPojo);

                    BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo = new BusiCustomerDrawQueuePojo();
                    busiCustomerDrawQueuePojo.setDrawId(busiCustomerDrawrecordPojo.getId());
                    busiCustomerDrawQueuePojo.setDrawNumber(busiCustomerDrawrecordPojo.getDrawNumber());
                    busiCustomerDrawQueuePojo.setIdType(busiCustomerDrawrecordPojo.getIdType());
                    customerDrawQueueBusiServiceImpl.addInfo(busiCustomerDrawQueuePojo);

                    paraMap.put("result", "success");
                    paraMap.put("code", "4"); //提现处理中
                    return paraMap;
                }else if("AMOUNT_LIMIT".equals(errorCode)){
                    error="金额超限";
                }else if("SEND_FAILED".equals(errorCode)){
                    error="付款错误";
                }else if("NOTENOUGH".equals(errorCode)) {
                    error = "付款账号余额不足";
                }else if("FREQ_LIMIT".equals(errorCode)){
                    error="超过频率限制，请稍后再试。";
                }else if("MONEY_LIMIT".equals(errorCode)){
                    error="已经达到今日付款总额上限";
                }else if("SENDNUM_LIMIT".equals(errorCode)){
                    error="该用户今日付款次数超过限制";
                }

                //统一记录失败原因
                busiCustomerDrawrecordPojo.setErr_code(errorCode);
                busiCustomerDrawrecordPojo.setErr_code_des(error==null?wxPayException.toString():error);
                busiCustomerDrawrecordPojo.setOpenId(openId);
                busiCustomerDrawrecordPojo.setAuditStatus((short)2);
                customerDrawrecordBusiServiceImpl.updateInfo(busiCustomerDrawrecordPojo);
                paraMap.put("msg",error==null?"提现失败!":error);
                paraMap.put("result", "falied");
                paraMap.put("code", "1");//
                return paraMap;
            }else if(e instanceof BusinessException){
                paraMap.put("result", "falied");
                paraMap.put("code", "1");//
                paraMap.put("msg", e.getMessage());//
                return paraMap;
            }
            paraMap.put("result", "falied");
            paraMap.put("code", "1");//
            paraMap.put("msg", "系统异常!");//
            return paraMap;
        }finally{
            redisManager.getRedisTemplate().delete(tokenUser.getId()+"_draw");
        }
        throw new BusinessException("BC0051", ResponseConstant.RESULT_DESCRIPTION_FAILED);
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;

        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getToken())){
            errorMessage.rejectNull("token",null,"提现申请用户信息");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getDrawAmount())){
            errorMessage.rejectNull("drawAmount",null,"提现金额不能为空");
            return errorMessage;
        }

        if(busiCustomerDrawrecordModel.getDrawAmount().compareTo(new BigDecimal(5)) < 0){
            errorMessage.rejectError("drawAmount",null,"提现金额不能小于5元","提现金额不能小于5元");
            return errorMessage;
        }

        BigDecimal maxLimit = new BigDecimal("20000");
        if(busiCustomerDrawrecordModel.getDrawAmount().compareTo(maxLimit) >= 0){
            errorMessage.rejectError("drawAmount",null,"提现金额不能超过"+maxLimit.toString(),"提现金额不能超过"+maxLimit.toString());
            return errorMessage;
        }

        BigDecimal drawAmountBase = sysPropertiesUtils.getDecimalValue("drawAmountBase",new BigDecimal("1"));
        BigDecimal[] result = busiCustomerDrawrecordModel.getDrawAmount().divideAndRemainder(drawAmountBase);
        if(result[1].compareTo(new BigDecimal("0")) != 0){
            errorMessage.rejectError("dawrAmountBase",null,"提现金额不是"+drawAmountBase+"的整数倍!","");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getIdType())){
            errorMessage.rejectNull("idType",null,"idType");
            return errorMessage;
        }else if(busiCustomerDrawrecordModel.getIdType() != 1 && busiCustomerDrawrecordModel.getIdType() != 2){
            errorMessage.rejectErrorMessage("idType","idType数据异常","idType数据异常");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerDrawrecordModel.getToken());
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(tokenUser.getId());

        //若未设置密码 则提示设置密码
        if(CommonUtil.isEmpty(busiCustomerPojo.getPayPassword())){
            errorMessage.rejectErrorMessage("payPassword","请先设置支付密码!","请先设置支付密码!");
            return errorMessage;
            //页面未提交密码
        }else if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getPayPassword())){
            errorMessage.rejectNull("payPassword",null,"支付密码");
            return errorMessage;
        }else{
            //判断密码是否相同
            String encodePass = MD5CoderUtil.encode(MD5CoderUtil.encode(busiCustomerDrawrecordModel.getPayPassword()));
            //密码不一致 提示
            if(!encodePass.equals(busiCustomerPojo.getPayPassword())){
                errorMessage.rejectErrorMessage("payPassword","支付密码不正确!","支付密码不正确!");
                return errorMessage;
            }
        }
        return errorMessage;
    }

    public static void main(String[] args) throws Exception{
        BigDecimal[] result = new BigDecimal("5.3").divideAndRemainder(new BigDecimal("1"));
        System.out.println(result[0]);
        System.out.println(result[1]);
    }
}
