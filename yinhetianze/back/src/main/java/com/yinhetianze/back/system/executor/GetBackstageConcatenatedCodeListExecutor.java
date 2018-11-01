package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ConcatenatedCodePojo;
import com.yinhetianze.systemservice.system.model.ConcatenatedCodeModel;
import com.yinhetianze.systemservice.system.service.info.ConcatenatedCodeInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageConcatenatedCodeListExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ConcatenatedCodeInfoService concatenatedCodeInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ConcatenatedCodeModel concatenatedCodeModel =(ConcatenatedCodeModel)model;
        PageHelper.startPage(concatenatedCodeModel.getCurrentPage(), concatenatedCodeModel.getPageSize());
        PageInfo pageInfo=new PageInfo(concatenatedCodeInfoServiceImpl.selectConcatenatedCodeList());
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
