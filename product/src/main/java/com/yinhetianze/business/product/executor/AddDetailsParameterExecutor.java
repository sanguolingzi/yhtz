package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductSpeciModel;
import com.yinhetianze.business.product.service.ProductExtraBusiService;
import com.yinhetianze.business.product.service.ProductExtraInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加商品基本信息
 */
@Service
public class AddDetailsParameterExecutor extends AbstractRestBusiExecutor<Integer>
{

    @Autowired
    private ProductExtraInfoService productExtraInfoServiceImpl;

    @Autowired
    private ProductExtraBusiService productExtraBusiServiceImpl;

    @Override
    protected Integer executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductModel prodModel = (ProductModel) model;
        ProductExtraPojo prodPojo = new ProductExtraPojo();
        //判断商品数据是否存在 如果存在则是修改，不存在则是添加
        prodPojo.setProdId(prodModel.getProductId());
        //根据商品Id 查出详情表的商品记录是否存在
        ProductExtraPojo selectProdPojo = productExtraInfoServiceImpl.findProductExtra(prodPojo);
        List<Map> speciModelList = null; // 循环时的单品的规格列表
        // 处理当前sku的参数
        if (CommonUtil.isNotEmpty(prodModel.getParamList())){
            speciModelList = new ArrayList<Map>();
            for(int i=0; i<prodModel.getParamList().size();i++){
                Map map =new HashMap();
                map.put("paramName",prodModel.getParamList().get(i).getParamName());
                map.put("sort",prodModel.getParamList().get(i).getSort());
                map.put("paramValue",prodModel.getParamList().get(i).getParamValue());
                speciModelList.add(map);
            }
        }
        // 将商品规格项转成json保存
        prodPojo.setProdParam(CommonUtil.objectToJsonString(speciModelList));
        int result=0;
        //存在的话则修改记录
        if(CommonUtil.isNotNull(selectProdPojo)){
            prodPojo.setId(selectProdPojo.getId());
            result = productExtraBusiServiceImpl.modifyProductExtra(prodPojo);
        }else{
            result= productExtraBusiServiceImpl.addProductExtra(prodPojo);
        }
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return result ;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        // 商品ID不能为空
        if (CommonUtil.isEmpty(prodModel.getProductId())) {
            errors.rejectNull("productId",null, "商品Id");
        }

        // 商品参数不能为空
        if (CommonUtil.isEmpty(prodModel.getParamList())) {
            errors.rejectNull("paramList", null, "商品参数");
        }
        return errors;
    }
}
