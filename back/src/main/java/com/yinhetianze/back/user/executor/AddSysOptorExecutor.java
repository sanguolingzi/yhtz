package com.yinhetianze.back.user.executor;

import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.systemservice.user.model.BusiSysOptorModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.user.service.busi.SysOptorBusiService;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增系统用户
 */
@Component
public class AddSysOptorExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysOptorBusiService sysOptorBusiServiceImpl;

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        int result = sysOptorBusiServiceImpl.addInfo(busiSysOptorModel);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysOptorModel busiSysOptorModel = (BusiSysOptorModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysOptorModel.getAccount())){
            errorMessage.rejectNull("account",null,"账号");
            return errorMessage;
        }


        BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
        busiSysOptorPojo.setAccount(busiSysOptorModel.getAccount());
        busiSysOptorPojo=sysOptorInfoServiceImpl.select(busiSysOptorPojo);
        if(busiSysOptorPojo != null){
            errorMessage.rejectError("account","BC0055","账号","账号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysOptorModel.getPhone())){
           errorMessage.rejectNull("phone",null,"手机号码");
           return errorMessage;
        }
        String phoneRegex = "^[1][3,4,5,6,7,8][0-9]{9}$";
        if(!busiSysOptorModel.getPhone().matches(phoneRegex)){
            errorMessage.rejectError("phone","BC0017","");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysOptorModel.getOptorName())){
               errorMessage.rejectNull("optorName",null,"姓名");
               return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysOptorModel.getRoleIds())){
           errorMessage.rejectNull("role",null,"角色信息");
           return errorMessage;
        }
        return errorMessage;
    }
}
