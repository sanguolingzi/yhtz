package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiSmsCodeModel;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验短信验证码
 */

@Component
public class CheckSmsCodeExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private RedisManager redisManager;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSmsCodeModel busiSmsCodeModel = (BusiSmsCodeModel)model;
        String busiType = busiSmsCodeModel.getBusiType();
        Object cacheCode = null;
        String key = "";
        //------------------ 注册短信验证码 -----------------------------
        if("1".equalsIgnoreCase(busiType)){
                key = busiSmsCodeModel.getPhone()+ CustomerConstant.regeisterSufixKey;
        //------------------ 修改密码短信验证码 -----------------------------
        }else if("2".equalsIgnoreCase(busiType)){
                key = busiSmsCodeModel.getPhone()+ CustomerConstant.updPasswordSufixKey;
        //------------------ 微信绑定短信验证码 -----------------------------
        }else if("3".equalsIgnoreCase(busiType)){
                key = busiSmsCodeModel.getPhone()+ CustomerConstant.wechatBindSufixKey;
        //------------------ 忘记密码短信验证码 -----------------------------
        }else if("4".equalsIgnoreCase(busiType)){
                key = busiSmsCodeModel.getPhone()+ CustomerConstant.forgetPassSufixKey;
        //------------------ 修改支付密码短信验证码 -----------------------------
        }else if("5".equalsIgnoreCase(busiType)){
            key = busiSmsCodeModel.getPhone()+ CustomerConstant.updPayPasswordSufixKey;
        //------------------ 修改短信验证码 -----------------------------
        }else if("6".equalsIgnoreCase(busiType)){
            key = busiSmsCodeModel.getPhone()+CustomerConstant.updPhoneSufixKey;
        }

        if(busiSmsCodeModel.getSmsCode().equals(CustomerConstant.commonSmsCode)){

        }else{
            cacheCode = redisManager.getValue(key);
            if(cacheCode == null){
                throw new BusinessException("BC0029", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }else if(!cacheCode.equals(busiSmsCodeModel.getSmsCode())){
                throw new BusinessException("BC0053", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }


        //标记短信验证已通过 增加一个短信校验通过标志 业务处理需要从redis中获取这个校验标志 防止直接调用业务处理接口
        if("2".equalsIgnoreCase(busiType)
                || "4".equalsIgnoreCase(busiType)
                || "5".equalsIgnoreCase(busiType)){
            redisManager.setValue(busiSmsCodeModel.getPhone()+CustomerConstant.currentUserOperator,busiSmsCodeModel.getPhone(),new Long(1000*180));
        }

        redisManager.deleteValue(key);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiSmsCodeModel busiSmsCodeModel = (BusiSmsCodeModel)model;
        String phone = busiSmsCodeModel.getPhone();
        if(CommonUtil.isEmpty(phone)){
            errorMessage.rejectNull("phone",null,"手机号");
            return errorMessage;
        }

        if(!phone.matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSmsCodeModel.getBusiType())){
            errorMessage.rejectNull("busiType",null,"短信业务类型");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSmsCodeModel.getSmsCode())){
            errorMessage.rejectNull("smsCode",null,"验证码");
            return errorMessage;
        }
        return errorMessage;
    }
}
