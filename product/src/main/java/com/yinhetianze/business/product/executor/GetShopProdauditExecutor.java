package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.SysProdauditModel;
import com.yinhetianze.business.product.service.SysProdauditInfoService;
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
public class GetShopProdauditExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private SysProdauditInfoService sysProdauditInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SysProdauditModel sysProdauditModel=(SysProdauditModel)model;
        List<Map> list =sysProdauditInfoServiceImpl.getShopProdaudit(sysProdauditModel);
        return list;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        SysProdauditModel sysProdauditModel=(SysProdauditModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(sysProdauditModel.getProductId())){
            errorMessage.rejectNull("productId", null,"商品Id");
        }
        return  errorMessage;
    }
}
