package com.yinhetianze.business.shop.executor;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.busi.ShopCategoryBusiService;
import com.yinhetianze.business.shop.service.info.ShopCategoryInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺 删除基础信息
 */

@Component
public class DeleteShopCategoryExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopCategoryBusiService shopCategoryBusiServiceImpl;

    @Autowired
    private ShopCategoryInfoService shopCategoryInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel)model;

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null){
            LoggerUtil.error(DeleteShopCategoryExecutor.class,"busiShopPojo is null");
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        ShopCategoryPojo shopCategoryPojo = new ShopCategoryPojo();
        shopCategoryPojo.setId(shopCategoryModel.getId());
        shopCategoryPojo.setShopId(busiShopPojo.getId());

        shopCategoryPojo = shopCategoryInfoServiceImpl.getOneShopCategory(shopCategoryPojo);
        if(shopCategoryPojo != null){
            Map<String,Object> paraMap = new HashMap<String,Object>();
            paraMap.put("shopId",busiShopPojo.getId());
            paraMap.put("shopCateId",shopCategoryModel.getId());
            paraMap.put("delFlag",(short)0);
            List list = productInfoServiceImpl.getProductList(paraMap);
            JSONObject jsonObject = new JSONObject();
            if(list!=null&&!list.isEmpty()){
                jsonObject.put("result","failed");
                jsonObject.put("message","该分类下有关联商品!,请先更改商品店铺分类。");
                return jsonObject.toJSONString();
            }

            shopCategoryBusiServiceImpl.deleteInfo(shopCategoryPojo);
            jsonObject.put("result","success");
            jsonObject.put("message","");
            return jsonObject.toJSONString();
        }
        throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ShopCategoryModel shopCategoryModel = (ShopCategoryModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();

        if(shopCategoryModel.getId() == null){
            errorMessage.rejectNull("id",null,"id");
        }
        return errorMessage;
    }
}
