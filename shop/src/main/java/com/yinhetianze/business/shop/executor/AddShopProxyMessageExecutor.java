package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.busi.ShopProxyBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class AddShopProxyMessageExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopProxyBusiService shopProxyBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result=shopProxyBusiServiceImpl.addMessage((ShopProxyModel)model);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
}
