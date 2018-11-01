package com.yinhetianze.business.tag.executor;

import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.tag.model.ProductTagModel;
import com.yinhetianze.business.tag.service.ProductTagBusiService;
import com.yinhetianze.business.tag.service.ProductTagInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.tag.ProductTagPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class BindProductTagExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductTagInfoService productTagInfoServiceImpl;

    @Autowired
    private ProductTagBusiService productTagBusiServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductTagModel tagModel = (ProductTagModel) model;

        // 商品标签id不能为空
        if (CommonUtil.isEmpty(tagModel.getTagId()))
        {
            throw new BusinessException("商品标签ID不能为空");
        }

        // 商品id不能为空
        if (CommonUtil.isEmpty(tagModel.getProdId()))
        {
            throw new BusinessException("商品ID不能为空");
        }

        // 商品标签校验
        ProductTagPojo pojo = new ProductTagPojo();
        pojo.setId(tagModel.getTagId());
        pojo = productTagInfoServiceImpl.getProductTagById(pojo);
        if (CommonUtil.isEmpty(pojo))
        {
            throw new BusinessException("商品标签不存在");
        }
        else if (pojo.getDelFlag() == 1) // 删除标记
        {
            throw new BusinessException("商品标签已经删除或不可用");
        }

        // 商品信息校验
        ProductPojo prodPojo = new ProductPojo();
        prodPojo.setId(tagModel.getProdId());
        prodPojo = productInfoServiceImpl.findProduct(prodPojo);
        if (CommonUtil.isEmpty(prodPojo))
        {
            throw new BusinessException("商品信息不存在");
        }
        if (CommonUtil.isNotEmpty(prodPojo.getDelFlag()) && 1 == prodPojo.getDelFlag()) // 删除标记
        {
            throw new BusinessException("商品已经删除或不可用");
        }

        try
        {
            // 绑定参数
            Map<String, Object> bindParam = new HashMap<>();
            bindParam.put("prodId", tagModel.getProdId());
            bindParam.put("tagId", tagModel.getTagId());
            // 商品绑定执行操作
            int result = productTagBusiServiceImpl.bindProductTag(bindParam);
            if (result > 0)
            {
                LoggerUtil.info(DeleteProductTagExecutor.class, "商品[{}]绑定标签[{}]成功", new Object[]{bindParam.get("prodId"), bindParam.get("tagId")});
            }
            else
            {
                throw new BusinessException("商品绑定标签未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(DeleteProductTagExecutor.class, e);
            throw new BusinessException("商品绑定标签失败");
        }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductTagModel tagModel = (ProductTagModel) model;

        // 标签ID不能为空
        if (CommonUtil.isEmpty(tagModel.getTagId()))
        {
            errors.rejectNull("tagId", null,"标签Id");
        }

        // 商品ID不能为空
        if (CommonUtil.isEmpty(tagModel.getProdId()))
        {
            errors.rejectNull("prodId", null,"商品Id");
        }

        return errors;
    }
}
