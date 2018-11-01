package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.model.SpecialParametersModel;
import com.yinhetianze.business.parameter.service.ProductParameterBusiService;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.business.parameter.service.SpecialParametersBusiService;
import com.yinhetianze.business.parameter.service.SpecialParametersInfoService;
import com.yinhetianze.business.product.executor.AddShopProductExecutor;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.product.SpecialParametersPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ModifyProductSpecialParameterExecutor extends AbstractRestBusiExecutor{
    @Autowired
    private SpecialParametersInfoService specialParametersInfoServiceImpl;

    @Autowired
    private SpecialParametersBusiService specialParametersBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        SpecialParametersModel paramModel = (SpecialParametersModel) model;
        //判断该商品是否是登录店铺的商品
        ProductPojo productPojo=productUtil.setProductToke(model.getToken(),paramModel.getProductId());
        // 校验商品参数是否存在，不存在抛出异常
        SpecialParametersPojo paramPojo = new SpecialParametersPojo();
        paramPojo.setId(paramModel.getId());
        paramPojo = specialParametersInfoServiceImpl.findProductParameter(paramPojo);
        if (CommonUtil.isEmpty(paramPojo)) {
            throw new BusinessException("商品参数不存在");
        }
        //判断该商品的该参数名称是否重复
        SpecialParametersPojo param= new SpecialParametersPojo();
        param.setProductId(paramPojo.getProductId());
        param.setParaName(paramModel.getParaName());
        SpecialParametersPojo repeatPojo = specialParametersInfoServiceImpl.findProductParameter(param);
        if(CommonUtil.isNotEmpty(repeatPojo)){
            throw new BusinessException("该商品已存在该参数名称");
        }
        paramPojo.setParaName(paramModel.getParaName()); // 设置参数名称
        paramPojo.setParaDescribe(paramModel.getParaDescribe()); // 设置参数描述
        try {
            int result = specialParametersBusiServiceImpl.modifyProductParameter(paramPojo);
            if (result > 0) {
                LoggerUtil.info(ModifyProductSpecialParameterExecutor.class, "更新商品参数信息成功：[{}]", new Object[]{paramPojo.getId()});
            } else {
                throw new BusinessException("更新商品参数信息未成功");
            }
        } catch (Exception e) {
            LoggerUtil.error(ModifyProductSpecialParameterExecutor.class, e);
            throw new BusinessException("更新商品参数失败");
        }

        return "modifySuccess";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        SpecialParametersModel specialParametersModel = (SpecialParametersModel) model;

        if (CommonUtil.isEmpty(specialParametersModel.getId())) {
            errors.rejectNull("paramId", null, "参数Id");
        }
        return super.validate(request, model, params);
    }
}
