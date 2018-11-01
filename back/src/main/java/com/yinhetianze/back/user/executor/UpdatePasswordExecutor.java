package com.yinhetianze.back.user.executor;

import com.yinhetianze.systemservice.user.model.BusiSysUpdatepasswordModel;
import com.yinhetianze.systemservice.user.service.busi.SysOptorBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改系统用户登录密码
 */
@Component
public class UpdatePasswordExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysOptorBusiService sysOptorBusiServiceImpl;

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysUpdatepasswordModel busiSysUpdatepasswordModel = (BusiSysUpdatepasswordModel)model;

        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        busiSysOptorPojo.setId(busiSysUpdatepasswordModel.getId());
        busiSysOptorPojo.setLoginPassword(MD5CoderUtil.encode(MD5CoderUtil.encode(busiSysUpdatepasswordModel.getNewPassword())));
        int result = sysOptorBusiServiceImpl.updateByPrimaryKeySelective(busiSysOptorPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysUpdatepasswordModel busiSysUpdatepasswordModel = (BusiSysUpdatepasswordModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysUpdatepasswordModel.getId())){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysUpdatepasswordModel.getLoginPassword())){
            errorMessage.rejectNull("loginPassword",null,"原密码");
            return errorMessage;
        }

        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        busiSysOptorPojo.setId(busiSysUpdatepasswordModel.getId());
        busiSysOptorPojo = sysOptorInfoServiceImpl.select(busiSysOptorPojo);
        if(busiSysOptorPojo == null){
            errorMessage.rejectErrorMessage("info","用户信息不存在!","用户信息不存在!");
            return errorMessage;
        }

        String loginPassword = MD5CoderUtil.encode(MD5CoderUtil.encode(busiSysUpdatepasswordModel.getLoginPassword()));
        if(!busiSysOptorPojo.getLoginPassword().equals(loginPassword)){
            errorMessage.rejectErrorMessage("info","密码输入不正确!","密码输入不正确!");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysUpdatepasswordModel.getNewPassword())){
            errorMessage.rejectNull("newPassword",null,"新密码");
            return errorMessage;
        }

        //比较 原密码和新密码
        if(busiSysUpdatepasswordModel.getLoginPassword().equals(busiSysUpdatepasswordModel.getConfirmPassword())){
            errorMessage.rejectErrorMessage("info","原密码与新密码不能一样!","原密码与新密码不能一样!");
            return errorMessage;
        }




        if(CommonUtil.isEmpty(busiSysUpdatepasswordModel.getConfirmPassword())){
            errorMessage.rejectNull("confirmPassword",null,"确认密码");
            return errorMessage;
        }

        //比较新密码和确认密码
        if(!busiSysUpdatepasswordModel.getNewPassword().equals(busiSysUpdatepasswordModel.getConfirmPassword())){
            errorMessage.rejectErrorMessage("info","密码输入不一致!","密码输入不一致!");
            return errorMessage;
        }



        return errorMessage;
    }
}
