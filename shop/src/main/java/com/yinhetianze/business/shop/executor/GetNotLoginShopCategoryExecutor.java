package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.info.ShopCategoryInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店铺内分类查询
 */

@Component
public class GetNotLoginShopCategoryExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopCategoryInfoService shopCategoryInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel)model;
        ShopCategoryPojo shopCategoryPojo = new ShopCategoryPojo();
        shopCategoryPojo.setShopId(shopCategoryModel.getShopId());
        List<ShopCategoryPojo> listShopCategoryPojo=shopCategoryInfoServiceImpl.getShopCategory(shopCategoryPojo);
        return listShopCategoryPojo;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();

        ShopCategoryModel shopCategoryModel = (ShopCategoryModel)model;

        if(CommonUtil.isEmpty(shopCategoryModel.getShopId())){
            errorMessage.rejectNull("shopId",null,"店铺Id");
            return errorMessage;
        }

        return errorMessage;
    }
}
