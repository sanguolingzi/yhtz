package com.yinhetianze.business.companyaudit.executor;

import com.yinhetianze.business.companyaudit.model.BusiSysCompanyAuditModel;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.business.companyaudit.service.busi.BusiSysCompanyAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增企业审核记录
 */
@Component
public class UpdateCompanyAuditExecutorTest extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private BusiSysCompanyAuditService busiSysCompanyAuditServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysCompanyAuditModel busiSysCompanyAuditModel = (BusiSysCompanyAuditModel)model;
        busiSysCompanyAuditServiceImpl.addInfo(busiSysCompanyAuditModel);
        return busiSysCompanyAuditModel;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiSysCompanyAuditModel busiSysCompanyAuditModel = (BusiSysCompanyAuditModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiSysCompanyAuditModel.getCompanyId())){
            errorMessage.rejectNull("companyId",null,"企业id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSysCompanyAuditModel.getAuditStatus())){
            errorMessage.rejectNull("auditStatus",null,"审核状态");
            return errorMessage;
        }

        if(busiSysCompanyAuditModel.getAuditStatus() == 1){
            if(CommonUtil.isEmpty(busiSysCompanyAuditModel.getReason())){
                errorMessage.rejectNull("reason",null,"审核原因");
                return errorMessage;
            }
        }

        if(CommonUtil.isEmpty(busiSysCompanyAuditModel.getOnceAuditStatus())){
            errorMessage.rejectNull("onceAuditStatus",null,"前一次状态");
            return errorMessage;
        }
        return errorMessage;
    }
}
