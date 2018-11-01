package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductFresherModel;
import com.yinhetianze.business.product.service.ProductFresherImgInfoService;
import com.yinhetianze.business.product.service.ProductFresherInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductFresherImgPojo;
import com.yinhetianze.pojo.product.ProductFresherPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetProductFresherDetailsExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductFresherImgInfoService productFresherImgInfoServiceImpl;

    @Autowired
    private ProductFresherInfoService productFresherInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object>productFresherMap=new HashMap<String, Object>();
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ProductFresherPojo productFresherPojo=new ProductFresherPojo();
        productFresherPojo.setId(productFresherModel.getId());
        List<Map> productFresherList=productFresherInfoServiceImpl.selectProductFresherDetails(productFresherPojo);
        productFresherMap.put("productFresher",productFresherList);
        if(productFresherList.size()>0){
            ProductFresherImgPojo productFresherImgPojo=new ProductFresherImgPojo();
            productFresherImgPojo.setProdId(productFresherModel.getId());
            productFresherImgPojo.setDelFlag((short)0);
            List<ProductFresherImgPojo>productFresherImgList=productFresherImgInfoServiceImpl.selectProductFresherImgList(productFresherImgPojo);
            productFresherMap.put("productFresherImgList",productFresherImgList);
        }else{
            throw new BusinessException("该商品不存在");
        }
        return productFresherMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductFresherModel productFresherModel=(ProductFresherModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productFresherModel.getId())){
            errorMessage.rejectNull("id","null","U币专区商品ID");
        }
        return errorMessage;
    }
}
