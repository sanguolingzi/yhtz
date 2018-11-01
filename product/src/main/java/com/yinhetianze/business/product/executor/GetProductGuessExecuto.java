package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.business.product.service.ProductGuessInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询猜你喜欢是否存在
 */
@Service
public class GetProductGuessExecuto  extends AbstractRestBusiExecutor{
    @Autowired
    private ProductGuessInfoService productGuessInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        return productGuessInfoServiceImpl.getProductGuess(productGuessModel);
    }
}
