package com.yinhetianze.business.tag.executor;

import com.yinhetianze.business.tag.model.ProductTagModel;
import com.yinhetianze.business.tag.service.ProductTagBusiService;
import com.yinhetianze.business.tag.service.ProductTagInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ModifyProductTagExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductTagInfoService productTagInfoServiceImpl;

    @Autowired
    private ProductTagBusiService productTagBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductTagModel tagModel = (ProductTagModel) model;

        if (CommonUtil.isEmpty(tagModel.getTagId()))
        {
            throw new BusinessException("商品标签ID不能为空");
        }
        else
        {
            // 商品标签实体类
            ProductTagPojo pojo = new ProductTagPojo();
            pojo.setId(tagModel.getTagId());
            pojo = productTagInfoServiceImpl.getProductTagById(pojo);
            if (CommonUtil.isEmpty(pojo))
            {
                throw new BusinessException("商品标签不存在");
            }

            // 对前台传递的不为空的参数进行设置
            if (CommonUtil.isNotEmpty(tagModel.getTagName()))
            {
                pojo.setTagName(tagModel.getTagName());
            }
            if (CommonUtil.isNotEmpty(tagModel.getTagImg()))
            {
                pojo.setTagImg(tagModel.getTagImg());
            }
            if (CommonUtil.isNotEmpty(tagModel.getTagContent()))
            {
                pojo.setTagContent(tagModel.getTagContent());
            }
            if (CommonUtil.isNotEmpty(tagModel.getIsShow()))
            {
                pojo.setIsShow(tagModel.getIsShow());
            }

            try
            {
                // 执行商品标签修改操作
                int result = productTagBusiServiceImpl.modifyProductTag(pojo);
                if (result > 0)
                {
                    LoggerUtil.info(ModifyProductTagExecutor.class, "更新商品标[{}]签成功", new Object[]{tagModel.getTagId()});
                }
                else
                {
                    throw new BusinessException("更新商品标签未成功");
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(ModifyProductTagExecutor.class, e);
                throw new BusinessException("更新商品标签失败");
            }
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductTagModel tagModel = (ProductTagModel) model;

        // 商品标签ID不能为空
        if (CommonUtil.isEmpty(tagModel.getTagId()))
        {
            errors.rejectNull("tagId", null,"商品标签Id");
        }

        return errors;
    }
}
