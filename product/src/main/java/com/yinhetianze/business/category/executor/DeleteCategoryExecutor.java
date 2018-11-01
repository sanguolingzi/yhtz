package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.model.CategoryModel;
import com.yinhetianze.business.category.service.CategoryBusiService;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.category.CategoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 获取分类列表的executor
 */
@Service
public class DeleteCategoryExecutor extends AbstractRestBusiExecutor<List<CategoryPojo>>
{

    @Autowired
    private CategoryBusiService categoryBusiServiceImpl;

    @Override
    protected List<CategoryPojo> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        CategoryModel cateModel = (CategoryModel) model;
        CategoryPojo pojoParam = new CategoryPojo();

        // 分类ID
        if (CommonUtil.isNotEmpty(cateModel.getId()))
        {
            pojoParam.setId(cateModel.getId());
        }
        else
        {
            throw new BusinessException("分类ID不能为空");
        }

        // 执行删除的操作员ID，当逻辑删除时有效，否则无用
        /*if (CommonUtil.isNotEmpty(cateModel.getUpdateUser()))
        {
            pojoParam.setUpdateUser(cateModel.getUpdateUser());
        }*/

        try
        {
            // 执行删除
            int result = categoryBusiServiceImpl.deleteCategory(pojoParam);
            if (result <= 0)
            {
                throw new BusinessException("删除分类没有成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(DeleteCategoryExecutor.class, e);
            throw new BusinessException(e);
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        CategoryModel cateModel = (CategoryModel) model;

        // 分类ID不能为空
        if (CommonUtil.isEmpty(cateModel.getId()))
        {
            errors.rejectNull("id", null, "分类Id");
        }
        return errors;
    }
}
