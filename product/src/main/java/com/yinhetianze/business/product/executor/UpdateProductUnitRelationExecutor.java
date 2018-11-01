package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductUnitRelationModel;
import com.yinhetianze.business.product.model.ProductUnitRelationPageModel;
import com.yinhetianze.business.product.service.ProductUnitBusiService;
import com.yinhetianze.business.product.service.ProductUnitRelationBusiService;
import com.yinhetianze.business.product.service.ProductUnitRelationInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductUnitRelationPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改商品计量单位
 */
@Service
public class UpdateProductUnitRelationExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductUnitRelationBusiService productUnitRelationBusiServiceImpl;

    @Autowired
    private ProductUnitBusiService productUnitBusiServiceImpl;

    @Autowired
    private ProductUnitRelationInfoService productUnitRelationInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductUnitRelationPageModel productUnitRelationPageModel=(ProductUnitRelationPageModel)model;
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("unitName",productUnitRelationPageModel.getUnitName());
        map.put("isShow",productUnitRelationPageModel.getIsShow());
        map.put("sort",productUnitRelationPageModel.getSort());
        map.put("categoryid",productUnitRelationPageModel.getCategoryid());
        map.put("id",productUnitRelationPageModel.getRelationid());
        int result= productUnitBusiServiceImpl.updateProductUnit(map);
        if(result<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductUnitRelationPageModel productUnitRelationPageModel=(ProductUnitRelationPageModel)model;
        ErrorMessage errorMessage =new ErrorMessage();
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getUnitName())){
            errorMessage.rejectNull("unitName",null,"计量单位名称");
        }
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getRelationid())){
            errorMessage.rejectNull("relationid",null,"计量单位ID");
        }
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getIsShow())){
            errorMessage.rejectNull("isShow",null,"是否显示");
        }
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getSort())){
            errorMessage.rejectNull("sort",null,"排序");
        }
        if(CommonUtil.isEmpty(productUnitRelationPageModel.getCategoryid())){
            errorMessage.rejectNull("categoryid",null,"分类ID");
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("unitName",productUnitRelationPageModel.getUnitName());
        map.put("categoryid",productUnitRelationPageModel.getCategoryid());
        Integer result=productUnitRelationInfoServiceImpl.getProductUnitName(map);
        if(CommonUtil.isNotEmpty(result) && result != productUnitRelationPageModel.getRelationid()){
            errorMessage.rejectError("relationid","BC0055","该分类下商品计量单位存在","该分类下商品计量单位存在");
        }
        return errorMessage;
    }
}
