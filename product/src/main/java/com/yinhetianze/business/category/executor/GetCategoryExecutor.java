package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
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
public class GetCategoryExecutor extends AbstractRestBusiExecutor<List<CategoryPojo>>
{
    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Override
    protected List<CategoryPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        CategoryModel cateModel = (CategoryModel) model;
        CategoryPojo pojoParam = new CategoryPojo();

        if (CommonUtil.isNotEmpty(cateModel.getId()))
        {
            pojoParam.setId(cateModel.getId());
        }
        if (CommonUtil.isNotEmpty(cateModel.getCateName()))
        {
            pojoParam.setCateName(cateModel.getCateName());
        }
        if (CommonUtil.isNotEmpty(cateModel.getParentId()))
        {
            pojoParam.setParentId(cateModel.getParentId());
        }
        if (CommonUtil.isNotEmpty(cateModel.getIsShow()))
        {
            pojoParam.setIsShow(cateModel.getIsShow());
        }

        List<CategoryPojo> pojoList = null;
        try
        {
//            pojoList = categoryInfoServiceImpl.getCategory(pojoParam);
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetCategoryExecutor.class, e);
            throw new BusinessException(e);
        }

        return pojoList;
    }
}
