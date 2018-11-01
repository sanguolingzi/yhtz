package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.model.SpecialParametersModel;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.business.parameter.service.SpecialParametersBusiService;
import com.yinhetianze.business.parameter.service.SpecialParametersInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import com.yinhetianze.pojo.product.SpecialParametersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class GetProductSpecialParameterExecutor extends AbstractRestBusiExecutor<Object>{
    @Autowired
    private SpecialParametersInfoService specialParametersInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        SpecialParametersModel paramModel = (SpecialParametersModel) model;

        if (CommonUtil.isNotEmpty(paramModel.getProductId())) {
            SpecialParametersPojo paramPojo = new SpecialParametersPojo();
            paramPojo.setProductId(paramModel.getProductId());
            List<SpecialParametersPojo> paramList= specialParametersInfoServiceImpl.findListProductParameter(paramPojo);
            if (CommonUtil.isEmpty(paramList)) {
                throw new BusinessException("商品参数不存在");
            }
            return paramList;
        } else {
            throw new BusinessException("商品参数ID为空");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        SpecialParametersModel specialParametersModel = (SpecialParametersModel) model;

        if (CommonUtil.isEmpty(specialParametersModel.getProductId())) {
            errors.rejectNull("productId", null, "商品Id");
        }

        return errors;
    }
}
