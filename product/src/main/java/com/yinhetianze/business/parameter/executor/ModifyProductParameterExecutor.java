package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterBusiService;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModifyProductParameterExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Autowired
    private ProductParameterBusiService productParameterBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductParameterModel paramModel = (ProductParameterModel) model;

        if (CommonUtil.isEmpty(paramModel.getParamId()))
        {
            throw new BusinessException("商品参数ID不能为空");
        }
        if (CommonUtil.isEmpty(paramModel.getParamName()))
        {
            throw new BusinessException("商品参数名称不能为空");
        }

        // 校验商品参数是否存在，不存在抛出异常
        ProductParameterPojo paramPojo = new ProductParameterPojo();
        paramPojo.setId(paramModel.getParamId());
        paramPojo = productParameterInfoServiceImpl.findProductParameter(paramPojo);
        if (CommonUtil.isEmpty(paramPojo))
        {
            throw new BusinessException("商品参数不存在");
        }

        paramPojo.setParamName(paramModel.getParamName()); // 设置参数名称
        if (CommonUtil.isNotEmpty(paramModel.getFirstWord()))
        {
            paramPojo.setFirstWord(paramModel.getFirstWord());
        }
        else
        {
            paramPojo.setFirstWord(CommonUtil.toHanyuPinyinFirst(paramModel.getParamName().substring(0,1)));
        }
        // 是否占据整行
        if (CommonUtil.isNotEmpty(paramModel.getIsWholeLine()))
        {
            paramPojo.setIsWholeLine(paramModel.getIsWholeLine());
        }
        else
        {
            paramPojo.setIsWholeLine((short) 0);
        }
        // 排序编号
        if (CommonUtil.isNotEmpty(paramModel.getSort()))
        {
            paramPojo.setSort(paramModel.getSort());
        }

        try
        {
            int result = productParameterBusiServiceImpl.modifyProductParameter(paramPojo);
            if (result > 0)
            {
                LoggerUtil.info(ModifyProductParameterExecutor.class, "更新商品参数信息成功：[{}]", new Object[]{paramPojo.getId()});
            }
            else
            {
                throw new BusinessException("更新商品参数信息未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ModifyProductParameterExecutor.class, e);
            throw new BusinessException("更新商品参数失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductParameterModel paramModel = (ProductParameterModel) model;

        if (CommonUtil.isEmpty(paramModel.getParamId()))
        {
            errors.rejectNull("paramId", null,"参数Id");
        }

        if (CommonUtil.isEmpty(paramModel.getParamName()))
        {
            errors.rejectNull("paramName", null,"参数名称");
        }

        return super.validate(request, model, params);
    }
}
