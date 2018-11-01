package com.yinhetianze.business.brand.executor;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.brand.service.BrandBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取分类列表的executor
 */
@Service
public class DeleteBrandExecutor extends AbstractRestBusiExecutor<Object>
{

    @Autowired
    private BrandBusiService brandBusiServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        BrandModel brandModel = (BrandModel) model;
        BrandPojo brandPojo = new BrandPojo();

        // 品牌ID
        if (CommonUtil.isNotEmpty(brandModel.getBrandId()))
        {
            brandPojo.setId(brandModel.getBrandId());
        }
        else
        {
            throw new BusinessException("品牌ID不能为空");
        }
        ProductPojo productPojo=new ProductPojo();
        productPojo.setProdBrandId(brandModel.getBrandId());
        if(productInfoServiceImpl.getProductBrandId(productPojo)>0){
            throw new BusinessException("删除失败,该品牌下有商品");
        }else{
            brandPojo.setDelFlag((short)1);
            int result = brandBusiServiceImpl.deleteBrand(brandPojo);
            if (result <= 0)
            {
                throw new BusinessException("删除品牌未成功");
            }
        }

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        BrandModel brandModel = (BrandModel) model;

        // 品牌ID不能为空
        if (CommonUtil.isEmpty(brandModel.getBrandId()))
        {
            errors.rejectError("brandId", "9999", new Object[]{});
        }
        return errors;
    }
}
