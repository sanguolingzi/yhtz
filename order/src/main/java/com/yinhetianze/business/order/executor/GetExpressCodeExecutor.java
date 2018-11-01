package com.yinhetianze.business.order.executor;

import com.yinhetianze.business.expressCode.service.ExpressCodeInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class GetExpressCodeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ExpressCodeInfoService expressCodeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        return expressCodeInfoServiceImpl.findAll();
    }

}
