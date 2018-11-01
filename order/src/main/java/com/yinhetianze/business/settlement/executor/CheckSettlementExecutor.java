package com.yinhetianze.business.settlement.executor;

import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.settlement.service.SettlementBusiService;
import com.yinhetianze.business.settlement.service.SettlementInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.SettlementPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Service
public class CheckSettlementExecutor  extends AbstractRestBusiExecutor {

    @Autowired
    private SettlementInfoService settlementInfoServiceImpl;

    @Autowired
    private SettlementBusiService settlementBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        if(CommonUtil.isEmpty(tokenUser)){
            throw new BusinessException("用户没有登录");
        }
        SettlementModel settlementModel=(SettlementModel)model;
        SettlementPojo settlementPojo=new SettlementPojo();
        settlementPojo.setSettlementId(settlementModel.getSettlementId());
        settlementPojo=settlementInfoServiceImpl.findById(settlementPojo);
        if(CommonUtil.isEmpty(settlementPojo)){
            throw new BusinessException("结算记录ID有误");
        }
        settlementPojo.setStatus(settlementModel.getStatus());
        settlementPojo.setRefuseReason(settlementModel.getRefuseReason());
        settlementPojo.setReviewer(tokenUser.getUsername());
        settlementPojo.setReviewerTime(new Date());
        int i=settlementBusiServiceImpl.checkSettlement(settlementPojo);
        if(i<1){
            throw new BusinessException("审核失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        SettlementModel settlementModel=(SettlementModel)model;
        if(CommonUtil.isEmpty(settlementModel.getSettlementId())){
            error.rejectNull("settlementId",null,"结算记录ID");
        }
        if(CommonUtil.isEmpty(settlementModel.getStatus())){
            error.rejectNull("status",null,"审核状态");
        }else{
            if(settlementModel.getStatus()==3&&CommonUtil.isEmpty(settlementModel.getRefuseReason())){
                error.rejectNull("refuseReason",null,"审核拒绝时拒绝理由");
            }
        }
        return error;
    }
}
