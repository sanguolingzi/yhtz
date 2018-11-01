package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductGuessModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductGuessInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 查询猜你喜欢
 */
@Service
public class GetProductGuessListExecuto extends AbstractRestBusiExecutor {

    @Autowired
    private ProductGuessInfoService    productGuessInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductGuessModel productGuessModel=(ProductGuessModel)model;
        if(CommonUtil.isNotEmpty(productGuessModel.getProdName())){
            try{
                String prodName = URLDecoder.decode(productGuessModel.getProdName(),"UTF-8");
                productGuessModel.setProdName(prodName);
            }catch (Exception e){
                LoggerUtil.error(GetProductGuessListExecuto.class, e);
            }
        }
        PageHelper.startPage(productGuessModel.getCurrentPage(),productGuessModel.getPageSize());
        PageInfo pageInfo=new PageInfo(productGuessInfoServiceImpl.getProductGuessList(productGuessModel));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
