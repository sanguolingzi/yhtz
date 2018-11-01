package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class GetProductCategoryIdParameterExecutor extends AbstractRestBusiExecutor<Object>{
    @Autowired
    private ProductParameterInfoService productParameterInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductParameterModel paramModel = (ProductParameterModel) model;

        // 执行查询
        List<Map> paramList = productParameterInfoServiceImpl.getProductCategoryIdParameter(paramModel);
        if (CommonUtil.isNotEmpty(paramList)) {
            return paramList;
        }

        return "failed";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductParameterModel paramModel = (ProductParameterModel) model;

        if (CommonUtil.isEmpty(paramModel.getCateId())) {
            errors.rejectNull("cateId", null, "分类Id");
        }

        return errors;
    }
}
