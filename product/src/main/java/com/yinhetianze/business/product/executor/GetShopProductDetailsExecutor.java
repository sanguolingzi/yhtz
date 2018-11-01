package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.parameter.service.SpecialParametersInfoService;
import com.yinhetianze.business.product.model.ProductImgModel;
import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductExtraInfoService;
import com.yinhetianze.business.product.service.ProductImgInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.category.CategoryPojo;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import com.yinhetianze.pojo.product.ProductImgPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.product.SpecialParametersPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;

/*import com.yinhetianze.business.product.service.ProductExtraInfoService;*/

/**
 * 通过商品ID获取当前商品的完整商品信息
 * 包括:sku列表，图文详情，参数信息，缩略图组
 */
@Service
public class GetShopProductDetailsExecutor extends AbstractRestBusiExecutor<Map<String,Object>>{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductExtraInfoService productExtraInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Autowired
    private ProductImgInfoService productImgInfoServiceImpl;

    @Autowired
    private SpecialParametersInfoService specialParametersInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Map<String,Object> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductInfoModel prodModel = (ProductInfoModel) model;
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("productInfo", null); // 商品基本信息，默认为空
        responseMap.put("introduction", null); // 商品详情介绍，默认为空
        responseMap.put("productParam", null); // 商品参数，默认为空
        responseMap.put("detailList", null); // 商品详情信息，默认为空
        responseMap.put("speciInfo", null); // 商品规格信息，默认为空
        responseMap.put("productImgList", null); // 商品轮播图 默认为空
        responseMap.put("categoryArrar", null);//商品分类三级Id 默认为空
        responseMap.put("specialParametersList", null); //商品特殊属性参数
        try {
            // 商品ID不能为空
            if (CommonUtil.isNotEmpty(prodModel.getProductId())) {
                // 通过商品ID获取商品信息
                ProductPojo prodPojo = new ProductPojo();
                prodPojo.setId(prodModel.getProductId());
                prodPojo.setDelFlag((short) 0);
                Map prodMap = productInfoServiceImpl.findShopProduct(prodPojo);
                if (CommonUtil.isEmpty(prodMap)) {
                    // 获取不到商品信息则抛出异常，不进行下一步操作
                    throw new BusinessException("当前商品信息不存在");
                }
                responseMap.put("productInfo", prodMap); // 商品基本信息
                /*if("1".equals(prodMap.get("productDistinction"))){
                    if(CommonUtil.isNotEmpty(prodPojo.getSellPrice())){
                        double consumerCoupon=(new Double(prodPojo.getSellPrice().doubleValue()*(Double.parseDouble(sysPropertiesUtils.getStringValue("integralPercent")))/100)).intValue();
                        DecimalFormat df   = new DecimalFormat("######0.00");
                        responseMap.put("consumerCoupon",df.format(consumerCoupon));
                    }
                }*/

                // 通过商品ID获取商品拓展信息
                ProductExtraPojo prodExtraPojo = new ProductExtraPojo();
                prodExtraPojo.setProdId(prodModel.getProductId());
                prodExtraPojo = productExtraInfoServiceImpl.findProductExtra(prodExtraPojo);
                if (CommonUtil.isNotEmpty(prodExtraPojo)) {
                    String introduction = prodExtraPojo.getProdIntroduction();
                    responseMap.put("introduction", introduction); // 商品详情介绍

                    String prodParam = prodExtraPojo.getProdParam();

                    // 将商品参数节点传递到前台
                    responseMap.put("productParam", prodParam); // 商品参数
                }
                //获取商品分类联动ID
                CategoryPojo categoryPojo = new CategoryPojo();
                categoryPojo.setId((Integer) prodMap.get("prodCateId"));
                CategoryPojo categoryPojoParent = categoryInfoServiceImpl.findCategory(categoryPojo);
                categoryPojo.setId(categoryPojoParent.getParentId());
                CategoryPojo categoryPojoLevelParent = categoryInfoServiceImpl.findCategory(categoryPojo);
                int[] categoryArrar = {categoryPojoLevelParent.getParentId(), categoryPojoParent.getParentId(), (Integer) prodMap.get("prodCateId")};
                responseMap.put("categoryArrar", categoryArrar);

                // 通过商品ID获取商品单品列表
                Map<String,Object> detailParam = new HashMap<>();
                detailParam.put("prodId", prodModel.getProductId());
                if (CommonUtil.isNotEmpty(prodModel.getSkuCode())) {
                    detailParam.put("skuCode", prodModel.getSkuCode());
                }
                detailParam.put("delFlag", 0); // 没有删除的标记0
                // 店铺商品规格查询
                List<Map<String,Object>> detailList = productDetailInfoServiceImpl.getShopProductDetail(detailParam);
                if (CommonUtil.isNotEmpty(detailList)) {
                    // 将商品单品详情列表添加到返回结果中
                    responseMap.put("detailList", detailList);
                } else if (CommonUtil.isNotEmpty(prodModel.getSkuCode())) {
                    LoggerUtil.info(GetShopProductDetailsExecutor.class, "SKU {} 没有对应的单品信息", new Object[]{prodModel.getSkuCode()});
                }

                // 商品轮播图
                ProductImgPojo ProductImgPojo = new ProductImgPojo();
                ProductImgPojo.setDelFlag((short) 0);
                ProductImgPojo.setProductId(prodModel.getProductId());
                List<ProductImgPojo> productImgPojoList = productImgInfoServiceImpl.selectProductImgList(ProductImgPojo);
                responseMap.put("productImgList", productImgPojoList); // 商品轮播图
                //商品特殊属性
                SpecialParametersPojo paramPojo = new SpecialParametersPojo();
                paramPojo.setProductId(prodModel.getProductId());
                List<SpecialParametersPojo> paramList = specialParametersInfoServiceImpl.findListProductParameter(paramPojo);
                responseMap.put("specialParametersList", paramList);
            }
        } catch (Exception e) {
            LoggerUtil.error(GetShopProductDetailsExecutor.class, e);
            throw new BusinessException("获取商品详细信息失败");
        }

        return responseMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel infoModel = (ProductInfoModel) model;
        //商品Id
        if (CommonUtil.isEmpty(infoModel.getProductId())) {
            errors.rejectNull("productId", null, "商品Id");
        }

        return errors;
    }
}
