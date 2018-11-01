package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerDrawrecordInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 获取用户提现详情
 */

@Component
public class GetCustomerDrawRecordDetailExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerDrawrecordInfoService customerDrawrecordInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerDrawrecordModel.getToken());
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo  = customerDrawrecordInfoServiceImpl.selectOne(busiCustomerDrawrecordModel.getId());

        if(busiCustomerBankrollPojo == null
                || busiCustomerDrawrecordPojo == null
                || busiCustomerDrawrecordPojo.getBankrollId().intValue() !=busiCustomerBankrollPojo.getId())
            return null;

        BigDecimal drawAmount = busiCustomerDrawrecordPojo.getDrawAmount();
        BigDecimal serviceCharge = busiCustomerDrawrecordPojo.getServiceCharge();
        BigDecimal finalAmount = busiCustomerDrawrecordPojo.getFinalAmount();

        String applyTime = DateFormatUtils.format(busiCustomerDrawrecordPojo.getApplyTime(),"yyyy-MM-dd HH:mm:ss");

        busiCustomerDrawrecordModel.setFinalAmount(finalAmount.movePointLeft(2));
        busiCustomerDrawrecordModel.setDrawAmount(drawAmount.movePointLeft(2));
        busiCustomerDrawrecordModel.setServiceCharge(serviceCharge.movePointLeft(2));
        busiCustomerDrawrecordModel.setApplyTime(applyTime);
        busiCustomerDrawrecordModel.setDrawNumber(busiCustomerDrawrecordPojo.getDrawNumber());
        busiCustomerDrawrecordModel.setPayType(busiCustomerDrawrecordPojo.getPayType());
        busiCustomerDrawrecordModel.setAuditStatus(busiCustomerDrawrecordPojo.getAuditStatus());
        busiCustomerDrawrecordModel.setDrawType(busiCustomerDrawrecordPojo.getDrawType());

        busiCustomerDrawrecordModel.setReason(busiCustomerDrawrecordPojo.getReason());
        busiCustomerDrawrecordModel.setReceiveUser(busiCustomerDrawrecordPojo.getReceiveUser());
        busiCustomerDrawrecordModel.setBankNumber(busiCustomerDrawrecordPojo.getBankNumber());

        return busiCustomerDrawrecordModel;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage = new ErrorMessage();
        BusiCustomerDrawrecordModel busiCustomerDrawrecordModel = (BusiCustomerDrawrecordModel)model;

        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerDrawrecordModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }
        return null;
    }

}
