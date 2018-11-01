package com.yinhetianze.business.parameter.executor;

import com.yinhetianze.business.parameter.model.ProductParameterModel;
import com.yinhetianze.business.parameter.model.SpecialParametersModel;
import com.yinhetianze.business.parameter.service.ProductParameterBusiService;
import com.yinhetianze.business.parameter.service.ProductParameterInfoService;
import com.yinhetianze.business.parameter.service.SpecialParametersBusiService;
import com.yinhetianze.business.parameter.service.SpecialParametersInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductParameterPojo;
import com.yinhetianze.pojo.product.SpecialParametersPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteProductSpecialParameterExecutor extends AbstractRestBusiExecutor{
    @Autowired
    private SpecialParametersInfoService specialParametersInfoServiceImpl;

    @Autowired
    private SpecialParametersBusiService specialParametersBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        SpecialParametersModel paramModel = (SpecialParametersModel) model;

        if (CommonUtil.isNotEmpty(paramModel.getId())) {
            SpecialParametersPojo paramPojo = new SpecialParametersPojo();
            paramPojo.setId(paramModel.getId());
            paramPojo = specialParametersInfoServiceImpl.findProductParameter(paramPojo);
            if (CommonUtil.isNotEmpty(paramPojo)) {
                try {
                    int result = specialParametersBusiServiceImpl.deleteProductParameter(paramPojo);
                    if (result > 0) {
                        LoggerUtil.info(DeleteProductSpecialParameterExecutor.class, "删除商品参数成功：{}", new Object[]{paramPojo.getId()});
                    } else {
                        throw new BusinessException("删除商品参数未成功");
                    }
                } catch (Exception e) {
                    LoggerUtil.error(DeleteProductSpecialParameterExecutor.class, e);
                    throw new BusinessException("删除商品参数失败");
                }
            } else {
                throw new BusinessException("商品参数不存在");
            }
        } else {
            throw new BusinessException("商品参数ID不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        SpecialParametersModel paramModel = (SpecialParametersModel) model;

        // 参数ID不能为空
        if (CommonUtil.isEmpty(paramModel.getId())) {
            errors.rejectNull("paramId", null, "参数Id");
        }

        return errors;
    }
}
