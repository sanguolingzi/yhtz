package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.ShopCompanyPageModel;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UpdateShopCompanyinfoExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopBusiService shopBusiServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result=shopBusiServiceImpl.updateShopCompanyInfo((ShopCompanyPageModel)model);
        if(result<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopCompanyPageModel shopCompanyPageModel=(ShopCompanyPageModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(shopCompanyPageModel.getShopId())){
            errorMessage.rejectNull("shopId","null","店铺ID为空");
        }
        if(CommonUtil.isEmpty(shopCompanyPageModel.getCompanyinfoId())){
            errorMessage.rejectNull("companyinfoId","null","企业ID为空");
        }
        return errorMessage;
    }
}
