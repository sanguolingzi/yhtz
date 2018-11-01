package com.yinhetianze.business.category.executor;

import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class GetOneCategoryListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        List<Map> list =categoryInfoServiceImpl.getOneCateList();
        return list;
    }
}
