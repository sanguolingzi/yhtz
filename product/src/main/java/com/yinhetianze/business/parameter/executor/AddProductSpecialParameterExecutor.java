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
import java.util.Date;

@Service
public class AddProductSpecialParameterExecutor extends AbstractRestBusiExecutor{
    @Autowired
    private SpecialParametersInfoService specialParametersInfoServiceImpl;

    @Autowired
    private SpecialParametersBusiService specialParametersBusiServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private ProductUtil productUtil;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        SpecialParametersModel specialParametersModel = (SpecialParametersModel) model;
        SpecialParametersPojo pojo = null;

        //判断该商品是否是登录店铺的商品
        productUtil.setProductToke(model.getToken(),specialParametersModel.getProductId());

        if (CommonUtil.isNotEmpty(specialParametersModel.getParaName())) {
            // 参数名称是否存在
            pojo = new SpecialParametersPojo();
            pojo.setParaName(specialParametersModel.getParaName()); // 设置参数名称
            pojo.setProductId(specialParametersModel.getProductId());
            Object obj = specialParametersInfoServiceImpl.getProductParameterList(pojo);
            if (CommonUtil.isNotEmpty(obj)) {
                throw new BusinessException("参数名称已经存在");
            }
            //判断商品是否存在
            ProductPojo productPojo=new ProductPojo();
            productPojo.setId(specialParametersModel.getProductId());
            ProductPojo product=productInfoServiceImpl.findProduct(productPojo);
            if (CommonUtil.isEmpty(product)) {
                throw new BusinessException("商品不存在");
            }
            try {
                // 执行添加操作
                pojo.setCreateTime(new Date()); // 创建时间
                pojo.setParaDescribe(specialParametersModel.getParaDescribe());
                int result = specialParametersBusiServiceImpl.addProductParameter(pojo);
                if (result == 1) {
                    LoggerUtil.info(AddProductSpecialParameterExecutor.class, "添加商品参数成功：{}", new Object[]{pojo.getParaName()});
                    return "success";
                } else {
                    throw new BusinessException("添加商品参数未成功");
                }
            } catch (Exception e) {
                LoggerUtil.error(AddProductSpecialParameterExecutor.class, e);
                throw new BusinessException("添加商品参数失败");
            }
        } else {
            throw new BusinessException("商品参数名称不能为空");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        SpecialParametersModel specialParametersModel = (SpecialParametersModel) model;

        // 参数名称不能为空
        if (CommonUtil.isEmpty(specialParametersModel.getParaName())) {
            errors.rejectNull("paraName", null, "参数名称");
        }
        if (CommonUtil.isEmpty(specialParametersModel.getParaDescribe())) {
            errors.rejectNull("paraDescribe", null, "参数描述");
        }
        if(CommonUtil.isEmpty(specialParametersModel.getProductId())){
            errors.rejectNull("productId", null, "商品Id");
        }
        return errors;
    }
}
