package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.business.product.service.ProductGuessInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除猜你喜欢
 */
@Service
public class DeleteProductGuessExecutor  extends AbstractRestBusiExecutor{

    @Autowired
    private ProductGuessInfoService productGuessInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        return productGuessInfoServiceImpl.deleteBatch(productGuessModel);
    }
}
