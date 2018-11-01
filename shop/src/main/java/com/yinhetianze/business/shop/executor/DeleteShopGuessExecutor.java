package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.ShopGuessModel;
import com.yinhetianze.business.shop.service.busi.ShopGuessBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.ShopGuessPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteShopGuessExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopGuessBusiService shopGuessBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopGuessPojo shopGuessPojo=new ShopGuessPojo();
        BeanUtils.copyProperties(model,shopGuessPojo);
        shopGuessPojo.setDelFlag((short)1);
        int result=shopGuessBusiServiceImpl.updateByPrimaryKeySelective(shopGuessPojo);
        if(result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopGuessModel shopGuessModel=(ShopGuessModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();

        if(CommonUtil.isEmpty(shopGuessModel.getId())){
            errorMessage.rejectNull("id",null,"id");
        }
        return errorMessage;
    }
}
