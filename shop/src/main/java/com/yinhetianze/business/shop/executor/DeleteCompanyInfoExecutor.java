package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 企业 删除基础信息
 */

@Component
public class DeleteCompanyInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;
        BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setId(busiShopCompanyModel.getId());
        busiShopCompanyPojo.setDelFlag((short)1);
        int result = shopCompanyBusiServiceImpl.updateByPrimaryKeySelective(busiShopCompanyPojo);
        if(result == 1)
            return "success";
        throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiShopCompanyModel busiShopCompanyModel = (BusiShopCompanyModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();
        if(busiShopCompanyModel.getId() == null){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }
        return errorMessage;
    }
}
