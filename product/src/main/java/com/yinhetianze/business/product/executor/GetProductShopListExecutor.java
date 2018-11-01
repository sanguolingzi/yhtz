package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询商品基本信息
 */
@Service
public class GetProductShopListExecutor extends AbstractRestBusiExecutor<PageData<ProductPojo>>{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected PageData<ProductPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductModel prodModel = (ProductModel) model;
        Map<String,Object> prodParam = new HashMap<>();

        if (CommonUtil.isNotEmpty(prodModel.getProdName())) {
            prodParam.put("prodName", prodModel.getProdName());
        }

        if (CommonUtil.isNotEmpty(prodModel.getProductId())) {
            prodParam.put("prodId", prodModel.getProductId());
        }

        prodParam.put("delFlag", 0);

        if (CommonUtil.isNotEmpty(prodModel.getIsReplacement())) {
            prodParam.put("isReplacement", prodModel.getIsReplacement());
        }

        if (CommonUtil.isNotEmpty(prodModel.getShopId())) {
            prodParam.put("shopId", prodModel.getShopId());
        }

        if (CommonUtil.isNotEmpty(prodModel.getAuditState())) {
            prodParam.put("auditState", prodModel.getAuditState());
        }

        if (CommonUtil.isNotEmpty(prodModel.getpStatus())) {
            prodParam.put("prodStatus", prodModel.getpStatus());
        }

        if (CommonUtil.isNotEmpty(prodModel.getIsFreightFree())) {
            prodParam.put("isFreightFree", prodModel.getIsFreightFree());
        }

        if (CommonUtil.isNotEmpty(prodModel.getProdCateId())) {
            prodParam.put("prodCateId", prodModel.getProdCateId());
        }

        //店铺内只能查询店铺商品
        prodParam.put("productType", 1);

        //结算类型
        if (CommonUtil.isNotEmpty(prodModel.getSettlement())) {
            prodParam.put("settlement", prodModel.getSettlement());
        }
        //商品类型
        if (CommonUtil.isNotEmpty(prodModel.getProductDistinction())) {
            prodParam.put("productDistinction", prodModel.getProductDistinction());
        }
        List<ProductPojo> prodList = null;
        try {
            PageHelper.startPage(prodModel.getCurrentPage(), prodModel.getPageSize());

            prodList = productInfoServiceImpl.getProductList(prodParam);

            PageInfo<ProductPojo> pageInfo = new PageInfo<>(prodList);

            return new PageData<ProductPojo>(pageInfo.getList(), pageInfo.getTotal());
        } catch (Exception e) {
            LoggerUtil.error(GetProductShopListExecutor.class, e);
            throw new BusinessException("根据条件查询产品信息失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null){
            errors.rejectError("shopId", null, "非商家店铺");
        }
        prodModel.setShopId(busiShopPojo.getId());
        return errors;
    }
}
