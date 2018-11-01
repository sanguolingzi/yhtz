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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class PaymentSettlementExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private SettlementInfoService settlementInfoServiceImpl;

    @Autowired
    private SettlementBusiService settlementBusiServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SettlementModel settlementModel=(SettlementModel)model;
        SettlementPojo settlementPojo=new SettlementPojo();
        settlementPojo.setSettlementId(settlementModel.getSettlementId());
        SettlementPojo settlementPojoCheck=settlementInfoServiceImpl.findById(settlementPojo);
        if(CommonUtil.isEmpty(settlementPojoCheck)){
            throw new BusinessException("结算记录ID有误");
        }
        if(settlementPojoCheck.getStatus()!=2){
            throw new BusinessException("审核未通过不能打款");
        }
        settlementPojo.setStatus((byte)4);
        settlementPojo.setPaymentTime(new Date());
        settlementPojo.setOrdersIds(settlementPojoCheck.getOrdersIds());
        int i=settlementBusiServiceImpl.paymentSettlement(settlementPojo);
        if(i<1){
            throw new BusinessException("打款失败");
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
        return error;
    }
}
