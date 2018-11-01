package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterBusiService;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.category.CategoryPojo;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BindProductCateParamRelaExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Autowired
    private ProductParameterBusiService productParameterBusiServiceImpl;

    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductParameterModel paramModel = (ProductParameterModel) model;

        // 分类ID非空校验
        if (CommonUtil.isEmpty(paramModel.getCateId()))
        {
            throw new BusinessException("商品分类ID不能为空");
        }
        if (CommonUtil.isEmpty(paramModel.getParamIds()))
        {
            throw new BusinessException("商品参数ID不能为空");
        }

        // 校验商品分类是否存在，不存在抛出异常
        CategoryPojo catePojo = new CategoryPojo();
        catePojo.setId(paramModel.getCateId());
        catePojo = categoryInfoServiceImpl.findCategory(catePojo);
        if (CommonUtil.isEmpty(catePojo))
        {
            throw new BusinessException("商品分类不存在");
        }
        else if (catePojo.getCateLevel() != 3)
        {
            throw new BusinessException("参数只能绑定到第三级分类下");
        }

        // 设置批量添加关系的参数
        List<Map<String, Object>> relaList = new ArrayList<>();
        Map<String, Object> rela = null;
        StringBuffer sb = new StringBuffer();
        for (Integer id : paramModel.getParamIds())
        {
            rela = new HashMap<>();
            rela.put("cateId", paramModel.getCateId());
            rela.put("paramId", id);
            sb.append(id).append(CommonConstant.CHAR_COMMA);
            relaList.add(rela);
        }

        try
        {
            int result = productParameterBusiServiceImpl.bindProductCateParamRela(relaList, paramModel.getCateId());
            if (result > 0)
            {
                LoggerUtil.info(BindProductCateParamRelaExecutor.class, "绑定商品分类与参数关系成功：分类[{}]，参数[{}]", new Object[]{paramModel.getCateId(), sb.toString().substring(0, sb.length() - 1)});
            }
            else
            {
                throw new BusinessException("绑定商品参数信息未成功");
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(BindProductCateParamRelaExecutor.class, e);
            throw new BusinessException("绑定商品参数失败");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductParameterModel paramModel = (ProductParameterModel) model;

        // 参数列表
        if (CommonUtil.isEmpty(paramModel.getParamIds()))
        {
            errors.rejectNull("paramIds", null,"参数列表Id");
        }

        // 分类ID
        if (CommonUtil.isEmpty(paramModel.getCateId()))
        {
            errors.rejectNull("cateId", null,"参数Id");
        }

        return super.validate(request, model, params);
    }
}
