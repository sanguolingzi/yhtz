package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.service.busi.CustomerDrawrecordBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerDrawrecordInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
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

/**
 * 新增消费者 银行卡提现信息
 */

@Component
public class AddCustomerDrawrecordInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerDrawrecordBusiService customerDrawrecordBusiServiceImpl;

    @Autowired
    private CustomerDrawrecordInfoService customerDrawrecordInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private UserDetailsService redisUserDetails;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerDrawrecordModel.getToken());
        //查询用户余额
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());



        BigDecimal serviceCharge  =sysPropertiesUtils.getDecimalValue("serviceCharge",new BigDecimal("3"));
        BigDecimal drawAmount = busiCustomerDrawrecordModel.getDrawAmount();
        BigDecimal serviceChargeResult = drawAmount.multiply(serviceCharge.movePointLeft(2)).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal finalAmount = drawAmount.add(serviceChargeResult);
        /**
         * 先计算手续费 若 提现金额+手续费 > 账户余额  则通过判断
         */
        if(busiCustomerBankrollPojo.getAmount().movePointLeft(2).compareTo(finalAmount)>=0){

            //提现间隔时间
            int dayInterval = sysPropertiesUtils.getIntValue("dayInterval",2);
            //提现次数
            int drawCount = sysPropertiesUtils.getIntValue("drawCount",5);

            Map<String,Object> paraMap = new HashMap();
            paraMap.put("dayInterval",dayInterval);
            paraMap.put("bankrollId",busiCustomerBankrollPojo.getId());
            int count = customerDrawrecordInfoServiceImpl.selectCount(paraMap);
            if(count >= drawCount){
                throw new BusinessException("您于"+dayInterval+"天内提现次数超过"+drawCount+"次");
            }

            BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo  = new BusiCustomerDrawrecordPojo();
            BeanUtils.copyProperties(model,busiCustomerDrawrecordPojo);
            busiCustomerDrawrecordPojo.setPayType((short)1);
            busiCustomerDrawrecordPojo.setServiceCharge(serviceChargeResult);
            busiCustomerDrawrecordPojo.setFinalAmount(finalAmount);
            customerDrawrecordBusiServiceImpl.AddCustomerDrawrecordInfo(busiCustomerBankrollPojo,busiCustomerDrawrecordPojo);
            return "success";
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

        if(busiCustomerDrawrecordModel.getDrawAmount().compareTo(new BigDecimal(0)) < 0){
            errorMessage.rejectError("drawAmount","BC0032","提现金额","提现金额");
            return errorMessage;
        }

        BigDecimal drawAmountBase = sysPropertiesUtils.getDecimalValue("drawAmountBase",new BigDecimal("50"));
        BigDecimal[] result = busiCustomerDrawrecordModel.getDrawAmount().divideAndRemainder(drawAmountBase);
        if(result[1].compareTo(new BigDecimal("0")) != 0){
            errorMessage.rejectError("dawrAmountBase",null,"提现金额不是"+drawAmountBase+"的整数倍!","");
            return errorMessage;
        }


        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getBankNumber())){
            errorMessage.rejectNull("bankNumber",null,"银行账号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getReceiveUser())){
            errorMessage.rejectNull("receiveUser",null,"收款人");
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
}
