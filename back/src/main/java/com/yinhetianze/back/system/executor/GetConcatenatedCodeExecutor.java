package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.ConcatenatedCodePojo;
import com.yinhetianze.systemservice.system.model.ConcatenatedCodeModel;
import com.yinhetianze.systemservice.system.service.info.ConcatenatedCodeInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class GetConcatenatedCodeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ConcatenatedCodeInfoService concatenatedCodeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ConcatenatedCodeModel concatenatedCodeModel =(ConcatenatedCodeModel)model;
        ConcatenatedCodePojo concatenatedCodePojo =new ConcatenatedCodePojo();
        BeanUtils.copyProperties(concatenatedCodeModel, concatenatedCodePojo);
        List<Map> list=concatenatedCodeInfoServiceImpl.selectConcatenatedCode(concatenatedCodePojo);
        return list;
    }
}
