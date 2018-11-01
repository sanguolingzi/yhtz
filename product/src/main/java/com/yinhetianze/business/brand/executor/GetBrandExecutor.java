package com.yinhetianze.business.brand.executor;

import com.yinhetianze.business.brand.model.BrandModel;
import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 获取分类列表的executor
 */
@Service
public class GetBrandExecutor extends AbstractRestBusiExecutor<BrandPojo>
{
    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Override
    protected BrandPojo executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        BrandModel brandModel = (BrandModel) model;
        BrandPojo pojoParam = new BrandPojo();

        // 根据品牌ID或者品牌名称进行匹配查询，两者条件不能同时为空，否则抛出异常
        if (CommonUtil.isNotEmpty(brandModel.getBrandId()))
        {
            pojoParam.setId(brandModel.getBrandId());
        }
        else if (CommonUtil.isNotEmpty(brandModel.getBrandName()))
        {
            pojoParam.setBrandName(brandModel.getBrandName());
        }
        else
        {
            throw new BusinessException("请输入有效的品牌信息进行查询");
        }

        try
        {
            pojoParam = brandInfoServiceImpl.findBrand(pojoParam);
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetBrandExecutor.class, e);
            throw new BusinessException(e);
        }

        return pojoParam;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        BrandModel brandModel = (BrandModel) model;

        // 品牌ID和品牌名称不能同时为空
        if (CommonUtil.isEmpty(brandModel.getBrandId()) && CommonUtil.isEmpty(brandModel.getBrandName()))
        {
            errors.rejectError("brandId", "9999", new Object[]{});
        }

        return super.validate(request, model, params);
    }
}
