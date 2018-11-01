package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.systemservice.system.service.info.ClassifyInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class FindAllClassifyExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private ClassifyInfoService classifyInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ClassifyModel classifyModel=(ClassifyModel)model;
        PageHelper.startPage(classifyModel.getCurrentPage(),classifyModel.getPageSize());
        PageInfo pageInfo = new PageInfo(classifyInfoServiceImpl.findAllClassify());
        Map<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
        return map;
    }
}
