package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 店铺 删除基础信息
 */

@Component
public class DeleteShopInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopBusiService shopBusiServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopModel busiShopModel = (BusiShopModel)model;
        BusiShopPojo busiShopPojo = new BusiShopPojo();
        busiShopPojo.setId(busiShopModel.getId());
        busiShopPojo.setDelFlag((short)1);
        int result = shopBusiServiceImpl.updateByPrimaryKeySelective(busiShopPojo);
        if(result == 1)
            return "success";
        throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiShopModel busiShopModel = (BusiShopModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();

        if(busiShopModel.getId() == null){
            errorMessage.rejectNull("id",null,"id");
        }
        return errorMessage;
    }
}
