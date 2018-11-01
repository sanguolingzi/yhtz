package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class GetSearchGoodsExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private ProductInfoService productInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        //1.价格升序 2.价格降序 3.销量升序 4.销量降序
        ProductModel productModel=(ProductModel)model;
        PageHelper.startPage(productModel.getCurrentPage(),productModel.getPageSize());
        List<Map> goodsList=productInfoServiceImpl.getSearchGoods(productModel);
        PageInfo pageInfo = new PageInfo(goodsList);
        return pageInfo;
    }
}
