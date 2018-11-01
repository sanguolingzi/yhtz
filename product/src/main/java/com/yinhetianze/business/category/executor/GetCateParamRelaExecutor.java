package com.yinhetianze.business.category.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.category.model.ParameterModel;
import com.yinhetianze.business.category.service.CateParamRelaInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Service
public class GetCateParamRelaExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CateParamRelaInfoService cateParamRelaInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ParameterModel parameterModel=(ParameterModel)model;
        if(CommonUtil.isNotEmpty(parameterModel.getParamName())){
            try {
                String paramName= URLDecoder.decode(parameterModel.getParamName(),"UTF-8");
                parameterModel.setParamName(paramName);
            }catch (Exception e){
                LoggerUtil.error(GetCateParamRelaExecutor.class,e);
            }
        }
        PageHelper.startPage(parameterModel.getCurrentPage(),parameterModel.getPageSize());
        PageInfo pageInfo=new PageInfo(cateParamRelaInfoServiceImpl.getCateParamRelaList(parameterModel));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
