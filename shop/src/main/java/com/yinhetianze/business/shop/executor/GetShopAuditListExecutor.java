package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditPageModel;
import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询企业/店铺审核信息记录
 */

@Component
public class GetShopAuditListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiSysShopAuditPageModel busiSysShopAuditPageModel = (BusiSysShopAuditPageModel)model;
        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setCustomerId(busiSysShopAuditPageModel.getCustomerId());
        busiSysShopAuditPojo.setAuditType(busiSysShopAuditPageModel.getAuditType());

        if(busiSysShopAuditPageModel.getIsAll() == null){
            PageHelper.startPage(busiSysShopAuditPageModel.getCurrentPage(),busiSysShopAuditPageModel.getPageSize());
        }

        List<BusiSysShopAuditPojo> list =  shopAuditInfoServiceImpl.select(busiSysShopAuditPojo);

        if(CommonUtil.isEmpty(list))
           return null;

        PageInfo pageInfo = new PageInfo(list);
        PageData<BusiSysShopAuditPojo> pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiSysShopAuditPageModel busiSysShopAuditPageModel = (BusiSysShopAuditPageModel)model;

        if(CommonUtil.isEmpty(busiSysShopAuditPageModel.getAuditType())){
            errorMessage.rejectNull("auditType",null,"auditType");
        }

        if(CommonUtil.isEmpty(busiSysShopAuditPageModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiSysShopAuditPageModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }
}
