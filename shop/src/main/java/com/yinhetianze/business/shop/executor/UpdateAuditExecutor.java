package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditModel;
import com.yinhetianze.business.shopaudit.service.busi.ShopAuditBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改审核结果
 */

@Component
public class UpdateAuditExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private ShopAuditBusiService shopAuditBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = shopAuditBusiServiceImpl.updateInfo( (BusiSysShopAuditModel)model);
        if(result <= 0){
            throw  new BusinessException("BC0037");
        }
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysShopAuditModel busiSysShopAuditModel = (BusiSysShopAuditModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysShopAuditModel.getId())){
            errorMessage.rejectNull("id",null,"审核记录id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getAuditStatus())){
            errorMessage.rejectNull("auditStatus",null,"审核状态");
            return errorMessage;
        }

        if(busiSysShopAuditModel.getAuditStatus() == 1){
            if(CommonUtil.isEmpty(busiSysShopAuditModel.getReason())){
                errorMessage.rejectNull("reason",null,"审核原因");
                return errorMessage;
            }
        }

        if(CommonUtil.isEmpty(busiSysShopAuditModel.getAuditResultJson())){
            errorMessage.rejectNull("auditResultJson",null,"内容单项审核结果");
            return errorMessage;
        }
        return errorMessage;
    }
}
