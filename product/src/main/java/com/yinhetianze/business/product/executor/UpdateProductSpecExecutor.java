package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.model.ProductSkuModel;
import com.yinhetianze.business.product.model.ProductSpeciModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Pc添加店铺内商品规格
 */
@Service
public class UpdateProductSpecExecutor extends AbstractRestBusiExecutor<String>{
    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductInfoModel prodInfoModel = (ProductInfoModel) model;
        ProductPojo prodPojo = new ProductPojo(); // 当前商品的基础信息
        prodPojo.setId(prodInfoModel.getProductId());
        //判断该商品是否是登录店铺的商品
        ProductPojo productPojo=productUtil.setProductToke(model.getToken(),prodInfoModel.getProductId());
        prodPojo.setUpdateTime(new Date());
        ProductDetailPojo productDetailPojoParam=new ProductDetailPojo();
        productDetailPojoParam.setSkuCode(prodInfoModel.getSkuCode());
        productDetailPojoParam.setProdId(prodInfoModel.getProductId());
        //根据skuCode查询修改规格存不存在
        ProductDetailPojo  productDetailCode = productDetailInfoServiceImpl.selectProductDetailPojo(productDetailPojoParam);
        if (CommonUtil.isEmpty(productDetailCode)){
            throw new BusinessException("修改规格不存在");
        }
        Integer totalStorage = 0; // 当前商品总库存数
        // 批量保存的商品详情列表
        List<ProductDetailPojo> detailPojoList = new ArrayList<>();
        // 对每个sku商品信息进行设置
        Integer salt = new Random().nextInt(10); // 盐值记录,随机从个位数开始计数，该数值会加入到skuCode中
        ProductDetailPojo detailPojo = null; // 循环时的单品值对象信息
        List<ProductSpeciModel> speciModelList = null; // 循环时的单品的规格列表
        if (CommonUtil.isNotEmpty(prodInfoModel.getSkuList())) {
            for (ProductSkuModel sku : prodInfoModel.getSkuList()) {
                salt++; // 盐值更新
                detailPojo = new ProductDetailPojo();
                detailPojo.setId(productDetailCode.getId());
                detailPojo.setUpdateTime(new Date()); // 设置更新时间
                detailPojo.setProdId(prodInfoModel.getProductId()); // 商品ID
                detailPojo.setCostPrice(sku.getCostPrice());//供货价
                detailPojo.setMemberPrice(sku.getMemberPrice());//零售价
                detailPojo.setpStorage(CommonUtil.isNotEmpty(sku.getStorage()) ? Short.valueOf(sku.getStorage().toString()) : 0); // 库存值 为空时默认为0
                // 处理当前sku的规格项
                if (CommonUtil.isNotEmpty(sku.getSpeciList())) {
                    speciModelList = new ArrayList<>();
                    for (ProductSpeciModel speci : sku.getSpeciList()) {
                        speciModelList.add(speci);
                    }
                }
                // 添加操作员信息
                if (CommonUtil.isNotEmpty(prodInfoModel.getCreateUser())) {
                    detailPojo.setCreateUser(prodInfoModel.getCreateUser());
                }
                // 生成sku编码
               // detailPojo.setSkuCode(productUtil.generateShopSku(productPojo, sku.getSpeciList(), salt));
                detailPojo.setProdSpeci(CommonUtil.objectToJsonString(speciModelList)); // 将商品规格项转成json保存
               // detailPojoList.add(detailPojo); // 添加到商品详情列表中
            }

        }
        try {
            int result = productDetailBusiServiceImpl.updateSkuPojo(detailPojo);
            if (result <= 0) {
                throw new BusinessException("修改商品详情信息未成功");
            } else {
                //更新商品总库存
                ProductDetailPojo productDetailPojo = new ProductDetailPojo();
                productDetailPojo.setProdId(prodInfoModel.getProductId());
                List<ProductDetailPojo> productDetailList = productDetailInfoServiceImpl.productDetailList(productDetailPojo);
                if (CommonUtil.isNotEmpty(productDetailList)) {
                    for (ProductDetailPojo productDetail : productDetailList) {
                        totalStorage = totalStorage + productDetail.getpStorage();
                    }
                }

                // 记录数量总体数量
                prodPojo.setpStorage(totalStorage);
                // 记录单品生成计算值
                prodPojo.setSalt(salt);
                int productStorage = productBusiServiceImpl.updateTotalStorage(prodPojo);
                if (productStorage != 1) {
                    throw new BusinessException("总库存更新失败");
                }
                //成功返回skuCode码
                LoggerUtil.info(UpdateProductSpecExecutor.class, "修改单品信息成功：{}条记录", new Object[]{result});
                return "success";
            }
        } catch (Exception e) {
            LoggerUtil.error(UpdateProductSpecExecutor.class, e);
            throw new BusinessException("修改商品详情信息失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel prodInfoModel = (ProductInfoModel) model;

        if (CommonUtil.isEmpty(prodInfoModel.getProductId())) {
            errors.rejectError("productId", "商品ID不能为空");
        }

        if (CommonUtil.isEmpty(prodInfoModel.getSkuList())) {
            errors.rejectError("skuList", "商品规格信息不能为空");
        } else {
            // 每个sku的sellPrice不能为空
            for (ProductSkuModel sku : prodInfoModel.getSkuList()) {
                if (CommonUtil.isEmpty(sku.getCostPrice())) {
                    errors.rejectNull("costPrice", null, "供货价");
                    return errors;
                }
                if(CommonUtil.isEmpty(sku.getMemberPrice())){
                    errors.rejectNull("memberPrice", null, "零售价");
                    return errors;
                }
                if (CommonUtil.isEmpty(sku.getStorage())) {
                    errors.rejectNull("costPrice", null, "库存不能为空");
                    return errors;
                }
            }
        }
        //商品skuCode
        if(CommonUtil.isEmpty(prodInfoModel.getSkuCode())){
            errors.rejectNull("skuCode", null,"商品skuCode");
        }
        return errors;
    }

}
