package com.yinhetianze.business.shop.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.busi.ShopCategoryBusiService;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 增加店铺内分类
 */
@Service
public class AddShopCategoryExecutor extends AbstractRestBusiExecutor<String>{
    @Autowired
    private ShopCategoryBusiService shopCategoryBusiServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ShopCategoryInfoService shopCategoryInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel) model;
        ShopCategoryPojo  shopCategoryPojo = new ShopCategoryPojo();
        BeanUtils.copyProperties(shopCategoryModel,shopCategoryPojo);
        int number=shopCategoryBusiServiceImpl.addShopCategory(shopCategoryPojo);
        JSONObject jsonObject = new JSONObject();
        if(1!=number){
            jsonObject.put("code","failed");
            return jsonObject.toJSONString();
        }
        jsonObject.put("code","success");
        jsonObject.put("data",shopCategoryPojo.getId());
        return jsonObject.toJSONString();
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage = new ErrorMessage();
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel) model;
        /*
        if (CommonUtil.isEmpty(shopCategoryModel.getShopId())) {
            errorMessage.rejectNull("shopId", null, "店铺Id");
            return errorMessage;
        }
        */
        if (CommonUtil.isEmpty(shopCategoryModel.getCateName())) {
            errorMessage.rejectNull("cateName", null, "店铺内分类名称");
            return errorMessage;
        }
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());

        ///查询店铺是否存在
        BusiShopPojo busiShopPojo= shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(CommonUtil.isEmpty(busiShopPojo)){
            errorMessage.rejectError("shopId", "BC0061", "店铺Id不存在");
            return errorMessage;
        }
        shopCategoryModel.setShopId(busiShopPojo.getId());

        //判断该分类是否存在
        ShopCategoryPojo  shopCategoryPojo = new ShopCategoryPojo();
        BeanUtils.copyProperties(shopCategoryModel,shopCategoryPojo);
        shopCategoryPojo.setDelFlag((short)0);
        ShopCategoryPojo isShopCategory=shopCategoryInfoServiceImpl.getOneShopCategory(shopCategoryPojo);
        if(CommonUtil.isNotEmpty(isShopCategory)){
            errorMessage.rejectError("cateName", "BC0059", "该分类已存在");
            return errorMessage;
        }
        return errorMessage;
    }
}
