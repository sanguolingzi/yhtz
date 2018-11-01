package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.service.*;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.pojo.product.ProductExtraPojo;
import com.yinhetianze.pojo.product.ProductImgPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.product.ProductSalesPojo;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class GetFrontProductDetailsExecutor extends AbstractRestBusiExecutor{
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
    private AreaModelUtils areaModelUtils;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private ProductSalesInfoService productSalesInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
   private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Override
    protected Map<String, Object> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductInfoModel prodModel = (ProductInfoModel) model;
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("productInfo", null); // 商品基本信息，默认为空
        responseMap.put("introduction", null); // 商品详情介绍，默认为空
        responseMap.put("productParam", null); // 商品参数，默认为空
        responseMap.put("detailList", null); // 商品详情信息，默认为空
        responseMap.put("speciInfo", null); // 商品规格信息，默认为空
        responseMap.put("productImgList", null); // 商品缩略图组

        try
        {
            // 商品ID不能为空
            if (CommonUtil.isNotEmpty(prodModel.getProductId()))
            {
                // 通过商品ID获取商品信息
                ProductPojo prodPojo;
                prodPojo= new ProductPojo();
                prodPojo.setId(prodModel.getProductId());
                prodPojo.setDelFlag((short)0);
                prodPojo.setAuditState((short)3);
                prodPojo.setpStatus((short)0);
                prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                if (CommonUtil.isEmpty(prodPojo))
                {
                    prodPojo=new ProductPojo();
                    prodPojo.setId(prodModel.getProductId());
                    prodPojo.setDelFlag((short)0);
                    prodPojo.setAuditState((short)3);
                    prodPojo.setpStatus((short)2);
                    prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                    if(CommonUtil.isEmpty(prodPojo)){
                        // 获取不到商品信息则抛出异常，不进行下一步操作
                        throw new BusinessException("查询不到商品信息");
                    }
                }
                if(prodPojo.getProductDistinction()==1){
                    double consumerCoupon=(new Double(prodPojo.getSellPrice().doubleValue()*(Double.parseDouble(sysPropertiesUtils.getStringValue("integralPercent")))/100)).intValue();
                    DecimalFormat    df   = new DecimalFormat("######0.00");
                    responseMap.put("consumerCoupon",df.format(consumerCoupon));
                }
                responseMap.put("productInfo", prodPojo); // 商品基本信息
                //获取销量信息
                ProductSalesPojo productSalesPojo=new ProductSalesPojo();
                productSalesPojo.setProductId(prodModel.getProductId());
                productSalesPojo=productSalesInfoServiceImpl.selectOne(productSalesPojo);
                if(CommonUtil.isEmpty(productSalesPojo)){
                    throw new BusinessException("获取销量信息失败");
                }
                responseMap.put("productSalesInfo",productSalesPojo);//商品销量信息

                //获取店铺信息

                if(prodPojo.getProductDistinction()==0){
                    //dropShipping
                    if(prodPojo.getDropShipping()==0){
                        BusiShopPojo busiShopPojo=new BusiShopPojo();
                        busiShopPojo.setId(prodPojo.getShopId());
                        busiShopPojo=shopInfoServiceImpl.selectOne(busiShopPojo);
                        if(CommonUtil.isEmpty(busiShopPojo)){
                            LoggerUtil.error(GetFrontProductDetailsExecutor.class,"获取店铺信息失败");
                        }else{
                            //获取企业信息
                            BusiShopCompanyPojo busiShopCompanyPojo=new BusiShopCompanyPojo();
                            busiShopCompanyPojo.setId(busiShopPojo.getCompanyinfoId());
                            busiShopCompanyPojo=shopCompanyInfoServiceImpl.selectOne(busiShopCompanyPojo);
                            if(CommonUtil.isEmpty(busiShopCompanyPojo)){
                                LoggerUtil.error(GetFrontProductDetailsExecutor.class,"获取企业信息失败");
                            }else {
                                String shopAddress = areaModelUtils.getProvince(busiShopCompanyPojo.getRegionLocation(), false).getName();
                                if (CommonUtil.isEmpty(shopAddress)) {
                                    LoggerUtil.error(GetFrontProductDetailsExecutor.class, "获取企业地址失败");
                                }
                                responseMap.put("shopAddress", shopAddress);
                            }
                        }
                    }else{
                        if(CommonUtil.isNotEmpty(prodPojo.getDropShippingId())){
                            ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
                            shopProxyPojo.setId(prodPojo.getDropShippingId());
                            shopProxyPojo.setDelFlag((short)0);
                            shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
                            if(CommonUtil.isEmpty(shopProxyPojo)){
                                LoggerUtil.error(GetFrontProductDetailsExecutor.class,"获取店铺信息失败");
                            }else{
                                //获取店铺地址
                                if(shopProxyPojo.getShopProvince()!=null){
                                    String  shopAddress=areaModelUtils.getProvince(shopProxyPojo.getShopProvince(),false).getName();
                                    if(CommonUtil.isEmpty(shopAddress)){
                                        LoggerUtil.error(GetFrontProductDetailsExecutor.class,"获取店铺地址失败");
                                    }
                                    responseMap.put("shopAddress", shopAddress);
                                }
                            }
                        }
                    }

                }
               //  areaModelUtils.getProvince("",false)
                // 通过商品ID获取商品拓展信息
                ProductExtraPojo prodExtraPojo = new ProductExtraPojo();
                prodExtraPojo.setProdId(prodModel.getProductId());
                prodExtraPojo = productExtraInfoServiceImpl.findProductExtra(prodExtraPojo);
                if (CommonUtil.isNotEmpty(prodExtraPojo))
                {
                    String introduction = prodExtraPojo.getProdIntroduction();
                    responseMap.put("introduction", introduction); // 商品详情介绍

                    String prodParam = prodExtraPojo.getProdParam();
                    // 将商品参数节点传递到前台
                    responseMap.put("productParam", prodParam); // 商品参数
                }

                // 通过商品ID获取商品单品列表
                Map<String, Object> detailParam = new HashMap<>();
                detailParam.put("prodId", prodModel.getProductId());
                if (CommonUtil.isNotEmpty(prodModel.getSkuCode()))
                {
                    detailParam.put("skuCode", prodModel.getSkuCode());
                }
                detailParam.put("delFlag", 0); // 没有删除的标记0
                detailParam.put("status", 0); // 有效状态1
                // 商品详情信息单品列表
                List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
                if (CommonUtil.isNotEmpty(detailList))
                {
                    // 将商品单品详情列表添加到返回结果中
                    responseMap.put("detailList", detailList);

                    // 记录有哪几种规格类型，格式如：[颜色={红，黄，蓝}，尺寸={大，中，小}......]
                    Map<String, Set> speciInfo = new HashMap<>();
                    for (Map<String, Object> map : detailList)
                    {
                        // 商品规格是否为空json数据是否为空
                        if (CommonUtil.isNotEmpty(map.get("prodSpeci")))
                        {
                            // 将商品规格json数据转成对象
                            List<Map> speciList = CommonUtil.readFromString(map.get("prodSpeci").toString(), List.class);
                            if (CommonUtil.isNotEmpty(speciList))
                            {
                                // 遍历规格数据
                                for (Map m : speciList)
                                {
                                    // 最终speciInfo中不包含规格名称时添加，并设置对应的规格值集合
                                    if (!speciInfo.containsKey(m.get("speciName")))
                                    {
                                        speciInfo.put(m.get("speciName").toString(), new HashSet<>());
                                    }
                                    // 向规格对应的规格值集合中添加规格值
                                    speciInfo.get(m.get("speciName")).add(m.get("speciValue"));
                                }
                            }
                        }
                    }
                    // 将商品规格名称种类列表添加到返回结果中
                    responseMap.put("speciInfo", speciInfo);
                }
                else if (CommonUtil.isNotEmpty(prodModel.getSkuCode()))
                {
                    LoggerUtil.info(GetFrontProductDetailsExecutor.class, "SKU {} 没有对应的单品信息", new Object[]{prodModel.getSkuCode()});
                }

                // 获取商品缩略图组 暂未开发图片上传
                ProductImgPojo ProductImgPojo = new ProductImgPojo();
                ProductImgPojo.setProductId(prodModel.getProductId());
                ProductImgPojo.setDelFlag((short) 0);
                List<ProductImgPojo> productImgPojoList = productImgInfoServiceImpl.selectProductImgList(ProductImgPojo);
                if(CommonUtil.isEmpty(productImgPojoList)){
                    LoggerUtil.info(GetFrontProductDetailsExecutor.class,"没有获取滚动图组");
                }else{
                    responseMap.put("productImgList", productImgPojoList); // 商品缩略图组
                }
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(GetFrontProductDetailsExecutor.class, e);
            throw new BusinessException("获取商品详细信息失败");
        }

        return responseMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel infoModel = (ProductInfoModel) model;
//        ProductModel prodModel = (ProductModel) model;

        if (CommonUtil.isEmpty(infoModel.getProductId()))
//        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            errors.rejectNull("productId", null, "商品Id");
        }

        return errors;
    }
}
