package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopBankrollModel;
import com.yinhetianze.business.shop.service.busi.ShopBankrollBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增店铺账户信息
 */

@Component
public class AddShopBankrollInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopBankrollBusiService shopBankrollBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopBankrollPojo busiShopBankrollPojo = new BusiShopBankrollPojo();
        BeanUtils.copyProperties(model,busiShopBankrollPojo);
        int result = shopBankrollBusiServiceImpl.add(busiShopBankrollPojo);
        if(result<=0)
            throw new BusinessException("BC0051", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopBankrollModel busiShopBankrollModel = (BusiShopBankrollModel)model;

        if(busiShopBankrollModel.getShopId() == null){
            errorMessage.rejectNull("shopId",null,"店铺Id");
            return errorMessage;
        }
        return errorMessage;
    }
}
