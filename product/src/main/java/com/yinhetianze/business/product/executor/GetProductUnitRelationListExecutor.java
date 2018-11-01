package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductUnitModel;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
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

/**
 * 根据计量单位模糊查询商品计量
 */
@Service
public class GetProductUnitRelationListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductUnitRelationInfoService productUnitRelationInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductUnitModel productUnitModel=(ProductUnitModel)model;
        if(CommonUtil.isNotEmpty(productUnitModel.getUnitName())){
            try {
                String unitName= URLDecoder.decode(productUnitModel.getUnitName(),"UTF-8");
                productUnitModel.setUnitName(unitName);
            }catch (Exception e){
                LoggerUtil.error(GetProductUnitRelationListExecutor.class,e);
            }
        }
        PageHelper.startPage(productUnitModel.getCurrentPage(),productUnitModel.getPageSize());
        PageInfo pageInfo=new PageInfo(productUnitRelationInfoServiceImpl.getProductUnitRelationList(productUnitModel));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
