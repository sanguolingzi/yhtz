package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
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
public class GetCategoryUnitExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ProductUnitRelationInfoService productUnitRelationInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductUnitRelationModel productUnitRelationModel=(ProductUnitRelationModel)model;
        List<Map> list=productUnitRelationInfoServiceImpl.getCategoryUnit(productUnitRelationModel);
        return list;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductUnitRelationModel productUnitRelationModel = (ProductUnitRelationModel) model;
        if (CommonUtil.isEmpty(productUnitRelationModel.getCategoryid()))
        {
            errors.rejectNull("categoryId", null, "分类Id");
        }

        return errors;
    }
}
