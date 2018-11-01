package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.SysHelpPraisePojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.system.model.SysHelpPraiseModel;
import com.yinhetianze.systemservice.system.service.busi.SysHelpPraiseBusiService;
import com.yinhetianze.systemservice.system.service.info.SysHelpPraiseInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class AddSysHelpPraiseExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private SysHelpPraiseBusiService sysHelpPraiseBusiServiceImpl;

    @Autowired
    private SysHelpPraiseInfoService sysHelpPraiseInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysHelpPraiseModel sysHelpPraiseModel=(SysHelpPraiseModel)model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        sysHelpPraiseModel.setCustomerId(tokenUser.getId());
        if(sysHelpPraiseInfoServiceImpl.selectSysHelpPraiseId(sysHelpPraiseModel)==0){
            SysHelpPraisePojo sysHelpPraisePojo=new SysHelpPraisePojo();
            BeanUtils.copyProperties(sysHelpPraiseModel,sysHelpPraisePojo);
            int result=sysHelpPraiseBusiServiceImpl.insertSelective(sysHelpPraisePojo);
            if(result <= 0)
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysHelpPraiseModel sysHelpPraiseModel=(SysHelpPraiseModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(sysHelpPraiseModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(sysHelpPraiseModel.getQuestionId())){
            errorMessage.rejectNull("questionId",null,"问答ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(sysHelpPraiseModel.getPraiseStatus())){
            errorMessage.rejectNull("praiseStatus",null,"点赞状态");
            return errorMessage;
        }
        return errorMessage;
    }
}
