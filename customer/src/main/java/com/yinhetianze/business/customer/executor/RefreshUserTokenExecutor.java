package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 刷新token
 */

@Component
public class RefreshUserTokenExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        Map<String,Object> paraMap = new HashMap<>();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        if(CommonUtil.isNotEmpty(busiCustomerModel.getToken())){
            TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerModel.getToken());
            if(tokenUser != null){
                redisUserDetails.updateUserDetails(tokenUser);
                /**
                 * @see CustomerBusiServiceImpl.login
                 */
                paraMap.put("code","1");//成功
                paraMap.put("token",busiCustomerModel.getToken());
                paraMap.put("hasGameId",(CommonUtil.isEmpty(tokenUser.getUserInfo().get("gameId"))?"1":"0")); // 1未设置 0已设置
                //paraMap.put("role",roleForFront);
                paraMap.put("phone",tokenUser.getUserInfo().get("phone"));
                paraMap.put("isMember",tokenUser.getUserInfo().get("isMember"));
                return paraMap;
            }
        }else{

            if(busiCustomerModel.getPhone() == null || busiCustomerModel.getSign() == null){
                paraMap.put("code","2");//参数异常
                return paraMap;
            }

            if(!busiCustomerModel.getPhone().matches(CustomerConstant.phoneRegex)){
                paraMap.put("code","2");//参数异常
                return paraMap;
            }

            if(MD5CoderUtil.encode(busiCustomerModel.getPhone()+CustomerConstant.salt).equalsIgnoreCase(busiCustomerModel.getSign())){

                try{
                    busiCustomerModel.setCheckPassword(false);
                    Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);
                    paraMap.put("code","1");//成功
                    paraMap.put("token",userInfo.get("token"));
                    return paraMap;
                }catch (Exception e){
                    LoggerUtil.error(RefreshUserTokenExecutor.class,e.getMessage());
                    paraMap.put("code","3");//刷新失败 根据手机号重新获取token异常
                    return paraMap;
                }
            }
        }
        return paraMap;
    }



    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {

        /*
        ErrorMessage errorMessage  = new ErrorMessage();
        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerModel.getPhone())){
            errorMessage.rejectNull("phone",null,"phone");
            return errorMessage;
        }

        if(!busiCustomerModel.getPhone().matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","phone");
            return errorMessage;
        }
        */

        return null;
    }
}
