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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddProductTagExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductTagInfoService productTagInfoServiceImpl;

    @Autowired
    private ProductTagBusiService productTagBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductTagModel tagModel = (ProductTagModel) model;

        if (CommonUtil.isNotEmpty(tagModel.getTagName()))
        {
            // 根据名称查询是否存在tag
            List<ProductTagPojo> tagList = productTagInfoServiceImpl.getProductTagByName(tagModel.getTagName());
            if (CommonUtil.isNotEmpty(tagList))
            {
                throw new BusinessException("标签名称已经存在");
            }

            // 即将保存的标签实体
            ProductTagPojo tagPojo = new ProductTagPojo();
            tagPojo.setTagName(tagModel.getTagName().trim());
            // 标签内容文本，富文本信息
            if (CommonUtil.isNotEmpty(tagModel.getTagContent()))
            {
                tagPojo.setTagContent(tagModel.getTagContent().trim());
            }
            // 标签标志图标
            if (CommonUtil.isNotEmpty(tagModel.getTagImg()))
            {
                tagPojo.setTagImg(tagModel.getTagImg().trim());
            }
            // 是否显示标志
            if (CommonUtil.isEmpty(tagModel.getIsShow()))
            {
                tagPojo.setIsShow((short) 1);
            }
            else
            {
                tagPojo.setIsShow(tagModel.getIsShow());
            }

            try
            {
                // 执行插入操作
                int result = productTagBusiServiceImpl.addProductTag(tagPojo);
                if (result <= 0)
                {
                    throw new BusinessException("添加标签未成功");
                }
                else
                {
                    LoggerUtil.info(AddProductTagExecutor.class, "添加商品标签成功！标签名称：{}", new Object[]{tagModel.getTagName()});
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(AddProductTagExecutor.class, e);
                throw new BusinessException("添加商品标签失败");
            }
        }
        else
        {
            throw new BusinessException("标签名称不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductTagModel tagModel = (ProductTagModel) model;

        // 名称不能为空
        if (CommonUtil.isEmpty(tagModel.getTagName()))
        {
            errors.rejectNull("tagName", null,"名称");
        }

        return errors;
    }
}
