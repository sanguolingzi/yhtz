package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.OneAreaModel;
import com.yinhetianze.business.product.service.OneAreaInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.OneAreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageOneAreaListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OneAreaInfoService oneAreaInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        OneAreaPojo oneAreaPojo=new OneAreaPojo();
        if(CommonUtil.isNotEmpty(oneAreaModel.getProdName())){
            try {
                String prodName = URLDecoder.decode(oneAreaModel.getProdName(), "UTF-8");
                oneAreaPojo.setProdName(prodName);
            }catch (Exception e){
                LoggerUtil.error(GetBackstageOneAreaListExecutor.class, e);
            }
        }
        PageHelper.startPage(oneAreaModel.getCurrentPage(), oneAreaModel.getPageSize());
        PageInfo pageInfo=new PageInfo(oneAreaInfoServiceImpl.selectBackstageOneAreaList(oneAreaPojo));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return  pageData;
    }
}
