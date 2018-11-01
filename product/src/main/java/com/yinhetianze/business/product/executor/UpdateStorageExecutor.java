package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductSkuModel;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateStorageExecutor  extends AbstractRestBusiExecutor<String> {
   @Autowired
   private ProductDetailBusiService productDetailBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductSkuModel productSkuModel=(ProductSkuModel)model;
        Map<String,Object> map=new HashMap<>();
        map.put("prodId",productSkuModel.getProdId());
        map.put("number",productSkuModel.getStorage());
        map.put("skuCode",productSkuModel.getSku());
        int i=productDetailBusiServiceImpl.updatePDStorage(map);
        if(i!=2){
            throw new BusinessException("更新库存异常");
        }
        return "success";
    }

}
