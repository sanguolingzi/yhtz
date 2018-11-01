package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.*;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 添加商品基本信息
 */
@Service
public class AddProductDetailsExecutor extends AbstractRestBusiExecutor<Object>
{

    @Autowired
    private ProductExtraInfoService productExtraInfoServiceImpl;

    @Autowired
    private ProductExtraBusiService productExtraBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductModel prodModel = (ProductModel) model;
        ProductExtraPojo prodPojo = new ProductExtraPojo();
        //判断商品数据是都存在 如果存在则是修改，不存在则是添加
        prodPojo.setProdId(prodModel.getProductId());
        //根据商品Id 查出详情表的商品记录是否存在
        ProductExtraPojo selectProdPojo = productExtraInfoServiceImpl.findProductExtra(prodPojo);
        prodPojo.setProdIntroduction(prodModel.getProdDetails());
        int result=0;
        //存在的话则修改记录
        if(CommonUtil.isNotNull(selectProdPojo)){
            prodPojo.setId(selectProdPojo.getId());
            result = productExtraBusiServiceImpl.modifyProductExtra(prodPojo);
        }else{
            result= productExtraBusiServiceImpl.addProductExtra(prodPojo);
        }
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success" ;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        // 商品ID不能为空
        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            errors.rejectNull("productId", null, "商品Id");
        }

        // 商品详情不能为空
        if (CommonUtil.isEmpty(prodModel.getProdDetails()))
        {
            errors.rejectNull("prodDetails", null, "商品详情");
        }
        return errors;
    }
}
