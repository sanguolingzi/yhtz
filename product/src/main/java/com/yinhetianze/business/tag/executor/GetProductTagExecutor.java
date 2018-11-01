package com.yinhetianze.business.tag.executor;

import com.yinhetianze.business.tag.model.ProductTagModel;
import com.yinhetianze.business.tag.service.ProductTagInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询商品标签执行器
 */
@Service
public class GetProductTagExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductTagInfoService productTagInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        // 前台入参
        ProductTagModel tagModel = (ProductTagModel) model;
        // 查询实体类
        ProductTagPojo pojo = new ProductTagPojo();

        if (CommonUtil.isNotEmpty(tagModel.getTagId()))
        {
            pojo.setId(tagModel.getTagId());
            pojo = productTagInfoServiceImpl.getProductTagById(pojo);
            if (CommonUtil.isEmpty(pojo))
            {
                throw new BusinessException("标签信息不存在");
            }
        }
        else
        {
            throw new BusinessException("标签ID不能为空");
        }

        return pojo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductTagModel tagModel = (ProductTagModel) model;

        // 标签ID不能为空
        if (CommonUtil.isEmpty(tagModel.getTagId()))
        {
            errors.rejectNull("tagId", null,"商品标签Id");
        }

        return errors;
    }
}
