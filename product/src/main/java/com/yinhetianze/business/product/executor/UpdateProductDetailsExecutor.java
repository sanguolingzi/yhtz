package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductDetailInfoModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.business.product.service.SysProdauditBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.product.SysProdauditPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UpdateProductDetailsExecutor  extends AbstractRestBusiExecutor{

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Autowired
    private SysProdauditBusiService sysProdauditBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProductDetailInfoModel productDetailInfoModel =(ProductDetailInfoModel)model;
        ProductDetailPojo productDetailPojo=new ProductDetailPojo();
        for (int i=0;i<productDetailInfoModel.getSkuList().size();i++){
            productDetailPojo.setSkuCode(productDetailInfoModel.getSkuList().get(i).getSkuCode());
            productDetailPojo.setSalePrice(productDetailInfoModel.getSkuList().get(i).getSalePrice());
            if(productDetailBusiServiceImpl.updateProductDetailAuditing(productDetailPojo)<=0)
                throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        ProductPojo productPojo=new ProductPojo();
        productPojo.setId(productDetailInfoModel.getProductId());
        productPojo.setpStatus(productDetailInfoModel.getpStatus());
        productPojo.setAuditState(productDetailInfoModel.getAuditState());
        productPojo.setSellPrice(productDetailInfoModel.getSellPrice());
        productPojo.setMarketPrice(productDetailInfoModel.getMarketPrice());
        /*if(CommonUtil.isNotEmpty(productDetailInfoModel.getSharePrice())){
            productPojo.setSharePrice(productDetailInfoModel.getSharePrice());
        }*/
        if(CommonUtil.isNotEmpty(productDetailInfoModel.getPromotionPrice())){
            productPojo.setPromotionPrice(productDetailInfoModel.getPromotionPrice());
        }
        if((productBusiServiceImpl.updateTotalStorage(productPojo))<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        SysProdauditPojo sysProdauditPojo=new SysProdauditPojo();
        sysProdauditPojo.setAuditStatus(productDetailInfoModel.getAuditState());
        sysProdauditPojo.setOnceAuditStatus((short)1);
        sysProdauditPojo.setProductId(productDetailInfoModel.getProductId());
        if((sysProdauditBusiServiceImpl.addsysProdaudit(sysProdauditPojo))<=0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return  "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errors = new ErrorMessage();
        ProductDetailInfoModel productDetailInfoModel = (ProductDetailInfoModel) model;

        if(CommonUtil.isEmpty(productDetailInfoModel.getSkuList())){
            errors.rejectNull("skuList",null, "商品规格信息");
        }
        // 商品ID不能为空
        if (CommonUtil.isEmpty(productDetailInfoModel.getProductId())) {
            errors.rejectNull("productId",null, "商品Id");
        }
        if(CommonUtil.isEmpty(productDetailInfoModel.getSellPrice())){
            errors.rejectNull("sellPrice", null, "商品展示价格");
        }
        return errors;
    }
}
