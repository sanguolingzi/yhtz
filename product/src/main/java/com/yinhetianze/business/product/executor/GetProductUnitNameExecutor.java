package com.yinhetianze.business.product.executor;


import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.model.ProductUnitRelationPageModel;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class GetProductUnitNameExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ProductUnitRelationInfoService productUnitRelationInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductUnitRelationPageModel productUnitRelationPageModel=(ProductUnitRelationPageModel)model;
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            String unitName = URLDecoder.decode(productUnitRelationPageModel.getUnitName(),"UTF-8");
            map.put("unitName",unitName);
        }catch (Exception e){
            LoggerUtil.error(GetProductUnitNameExecutor.class,e);
        }
        map.put("categoryid",productUnitRelationPageModel.getCategoryid());
        int result=productUnitRelationInfoServiceImpl.getProductUnitName(map);
        return result;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductUnitRelationPageModel productUnitRelationPageModel=(ProductUnitRelationPageModel)model;
        ErrorMessage errorMessage =new ErrorMessage();
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getUnitName())){
            errorMessage.rejectNull("unitName", null,"计量单位名称");
        }
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getCategoryid())){
            errorMessage.rejectNull("categoryid", null,"分类ID");
        }
        return  errorMessage;
    }
}
