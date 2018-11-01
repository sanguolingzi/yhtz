package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.model.ProductUnitRelationPageModel;
import com.yinhetianze.business.product.service.ProductUnitBusiService;
import com.yinhetianze.business.product.service.ProductUnitRelationBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductUnitPojo;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 添加商品计量单位
 */
@Service
public class AddProductUnitRelationExecutor  extends AbstractRestBusiExecutor{

    @Autowired
    private ProductUnitRelationBusiService productUnitRelationBusiServiceImpl;

    @Autowired
    private ProductUnitBusiService productUnitBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductUnitRelationPageModel productUnitRelationPageModel=(ProductUnitRelationPageModel)model;
        ProductUnitRelationPojo productUnitRelationPojo=new ProductUnitRelationPojo();
        ProductUnitPojo productUnitPojo=new ProductUnitPojo();
        productUnitPojo.setUnitName(productUnitRelationPageModel.getUnitName());
        productUnitPojo.setIsShow(productUnitRelationPageModel.getIsShow());
        productUnitPojo.setSort(productUnitRelationPageModel.getSort());
        int results= productUnitBusiServiceImpl.insertProductUnitid(productUnitPojo);
        if(results<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        productUnitRelationPojo.setUnitid(productUnitPojo.getId());
        productUnitRelationPojo.setCategoryid(productUnitRelationPageModel.getCategoryid());
        int result=productUnitRelationBusiServiceImpl.insertSelective(productUnitRelationPojo);
        if(result<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductUnitRelationPageModel productUnitRelationPageModel=(ProductUnitRelationPageModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getCategoryid())){
            errorMessage.rejectNull("categoryid",null,"关联的商品分类ID");
            return errorMessage;
        }
        return errorMessage;
    }
}
