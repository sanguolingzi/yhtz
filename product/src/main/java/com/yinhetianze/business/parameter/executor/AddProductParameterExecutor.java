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
import java.util.Date;

@Service
public class AddProductParameterExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Autowired
    private ProductParameterBusiService productParameterBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductParameterModel paramModel = (ProductParameterModel) model;
        ProductParameterPojo pojo = null;

        if (CommonUtil.isNotEmpty(paramModel.getParamName()))
        {
            // 参数名称是否存在
            pojo = new ProductParameterPojo();
            pojo.setCreateTime(new Date()); // 创建时间
            pojo.setParamName(paramModel.getParamName()); // 设置参数名称
            Object obj = productParameterInfoServiceImpl.getProductParameterList(pojo);
            if (CommonUtil.isNotEmpty(obj))
            {
                throw new BusinessException("参数名称已经存在");
            }

            // 参数名称首字符
            if (CommonUtil.isNotEmpty(paramModel.getFirstWord()))
            {
                pojo.setFirstWord(paramModel.getFirstWord());
            }
            else
            {
                pojo.setFirstWord(CommonUtil.toHanyuPinyinFirst(paramModel.getParamName().substring(0,1)));
            }
            // 是否占据整行
            if (CommonUtil.isNotEmpty(paramModel.getIsWholeLine()))
            {
                pojo.setIsWholeLine(paramModel.getIsWholeLine());
            }
            else
            {
                pojo.setIsWholeLine((short) 0);
            }
            // 排序编号
            if (CommonUtil.isNotEmpty(paramModel.getSort()))
            {
                pojo.setSort(paramModel.getSort());
            }

            try
            {
                // 执行添加操作
                int result = productParameterBusiServiceImpl.addProductParameter(pojo);
                if (result > 0)
                {
                    LoggerUtil.info(AddProductParameterExecutor.class, "添加商品参数成功：{}", new Object[]{pojo.getParamName()});
                }
                else
                {
                    throw new BusinessException("添加商品参数未成功");
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(AddProductParameterExecutor.class, e);
                throw new BusinessException("添加商品参数失败");
            }
        }
        else
        {
            throw new BusinessException("商品参数名称不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductParameterModel paramModel = (ProductParameterModel) model;

        // 参数名称不能为空
        if (CommonUtil.isEmpty(paramModel.getParamName()))
        {
            errors.rejectNull("paramName", null,"参数名称");
        }

        return errors;
    }
}
