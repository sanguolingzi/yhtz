package com.yinhetianze.back.user.executor;

import com.yinhetianze.core.utils.SecurityCode;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户登录
 */
@Component
public class LoginExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        busiSysOptorPojo.setAccount(busiSysOptorModel.getAccount());
        busiSysOptorPojo = sysOptorInfoServiceImpl.select(busiSysOptorPojo);
        if(busiSysOptorPojo == null){
            throw new BusinessException("BC0007", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        if(busiSysOptorPojo.getAccountStatus()==1){
            throw new BusinessException("该账号已被冻结");
        }
        String loginPassword = MD5CoderUtil.encode(MD5CoderUtil.encode(busiSysOptorModel.getLoginPassword()));
        if(!loginPassword.equals(busiSysOptorPojo.getLoginPassword())){
            throw new BusinessException("BC0007", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        String token = MD5CoderUtil.encode("yhtz"+String.valueOf(System.nanoTime())+ SecurityCode.getSecurityCode());
        //默认设置 角色为 USER 拥有访问所有链接的权限
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("USER");
        Map<String,Object> info=null;
        try{
            info = CommonUtil.readFromString(CommonUtil.objectToJsonString(busiSysOptorPojo),Map.class);
        }catch (Exception e){
            LoggerUtil.error(LoginExecutor.class,e.getMessage());
        }
        TokenUser tokenUser = new TokenUser(
                busiSysOptorPojo.getId(),
                busiSysOptorPojo.getPhone(),
                busiSysOptorPojo.getLoginPassword(),
                String.valueOf(token),
                roleSet,
                info
        );
        redisUserDetails.saveUserDetails(tokenUser);
        Map<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("userInfo", info);
        userInfo.put("token", token);
        return userInfo;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysOptorModel.getAccount())){
            errorMessage.rejectNull("account",null,"账号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysOptorModel.getLoginPassword())){
            errorMessage.rejectNull("loginPassword",null,"登录密码");
            return errorMessage;
        }

        return errorMessage;
    }
}
