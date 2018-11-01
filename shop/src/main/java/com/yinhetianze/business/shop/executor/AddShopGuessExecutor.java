package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.ShopGuessModel;
import com.yinhetianze.business.shop.service.busi.ShopGuessBusiService;
import com.yinhetianze.business.shop.service.info.ShopGuessInfoService;
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
public class AddShopGuessExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ShopGuessBusiService shopGuessBusiServiceImpl;

    @Autowired
    private ShopGuessInfoService shopGuessInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopGuessPojo shopGuessPojo=new ShopGuessPojo();
        BeanUtils.copyProperties(model,shopGuessPojo);
        int result=shopGuessBusiServiceImpl.insertSelective(shopGuessPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopGuessModel shopGuessModel=(ShopGuessModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();
        if(CommonUtil.isEmpty(shopGuessModel.getShopId())){
            errorMessage.rejectNull("shopId",null,"店铺ID");
        }
        if(CommonUtil.isEmpty(shopGuessModel.getSort())){
            errorMessage.rejectNull("sort",null,"排序");
        }
        ShopGuessPojo shopGuessPojo=new ShopGuessPojo();
        shopGuessPojo.setShopId(shopGuessModel.getShopId());
        shopGuessPojo.setDelFlag((short)0);
        shopGuessPojo=shopGuessInfoServiceImpl.selectOne(shopGuessPojo);
        if(CommonUtil.isNotEmpty(shopGuessPojo)){
            errorMessage.rejectError("id","BC0055","该店铺已经添加过","该店铺已经添加过");
        }
        return errorMessage;
    }
}
