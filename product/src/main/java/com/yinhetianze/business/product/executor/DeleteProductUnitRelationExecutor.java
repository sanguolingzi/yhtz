package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.DeleteModel;
import com.yinhetianze.business.product.service.ProductUnitRelationBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除商品计量
 */
@Service
public class DeleteProductUnitRelationExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private ProductUnitRelationBusiService productUnitRelationBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel=(DeleteModel)model;
        productUnitRelationBusiServiceImpl.deleteBatch(deleteModel.getIds());
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        DeleteModel deleteModel=(DeleteModel)model;
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }
        return errorMessage;
    }
}
