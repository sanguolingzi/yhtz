package com.yinhetianze.business.companyaudit.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysCompanyAuditPojo;
import com.yinhetianze.business.companyaudit.model.BusiSysCompanyAuditModel;
import com.yinhetianze.business.companyaudit.service.info.BusiSysCompanyAuditInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询最新审核记录
 */
@Component
public class GetCompanyAuditInfoExecutorTest extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private BusiSysCompanyAuditInfoService busiSysCompanyAuditInfoServiceImpl;

    //@Autowired
    //private BusiSysOptorInfoService busiSysOptorInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSysCompanyAuditModel busiSysCompanyAuditModel = (BusiSysCompanyAuditModel)model;

        BusiSysCompanyAuditPojo busiSysCompanyAuditPojo = new BusiSysCompanyAuditPojo();
        BeanUtils.copyProperties(busiSysCompanyAuditModel,busiSysCompanyAuditPojo);

        busiSysCompanyAuditPojo = busiSysCompanyAuditInfoServiceImpl.selectNewestPojo(busiSysCompanyAuditPojo);
        if(busiSysCompanyAuditPojo != null){
            //BeanUtils.copyProperties(busiSysCompanyAuditPojo,busiSysCompanyAuditModel);
            //BusiSysOptorPojo busiSysOptorPojo = new BusiSysOptorPojo();
            //busiSysOptorPojo.setId(busiSysCompanyAuditPojo.getId());
            //busiSysOptorPojo = busiSysOptorInfoServiceImpl.select(busiSysOptorPojo);
            //busiSysCompanyAuditModel.setCreateUserName(busiSysOptorPojo.getAccount());
        }
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
        return errorMessage;
    }
}
