package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductSkuModel;
import com.yinhetianze.business.product.model.ProductSpeciModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 添加商品单品信息规格
 *
 */
@Service
public class AddProductInfoExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        ProductInfoModel prodInfoModel = (ProductInfoModel) model;
        ProductPojo prodPojo = null; // 当前商品的基础信息

        if (CommonUtil.isEmpty(prodInfoModel.getProductId()))
        {
            throw new BusinessException("商品ID不能为空");
        }
        else
        {
            // 通过prodId获取当前商品信息
            prodPojo = new ProductPojo();
            prodPojo.setId(prodInfoModel.getProductId());
            prodPojo = productInfoServiceImpl.findProduct(prodPojo);
            if (CommonUtil.isEmpty(prodPojo))
            {
                throw new BusinessException("没有当前商品信息");
            }
        }

        Integer totalStorage = 0; // 当前商品总库存数
        // 批量保存的商品详情列表
        List<ProductDetailPojo> detailPojoList = new ArrayList<>();
        // 对每个sku商品信息进行设置
        Integer salt = new Random().nextInt(10); // 盐值记录,随机从个位数开始计数，该数值会加入到skuCode中
        if (CommonUtil.isNotEmpty(prodInfoModel.getSkuList()))
        {
            ProductDetailPojo detailPojo = null; // 循环时的单品值对象信息
            List<ProductSpeciModel> speciModelList = null; // 循环时的单品的规格列表
            for (ProductSkuModel sku : prodInfoModel.getSkuList())
            {
                salt ++; // 盐值更新
                detailPojo = new ProductDetailPojo();
                detailPojo.setCreateTime(new Date()); // 创建时间
                detailPojo.setProdId(prodInfoModel.getProductId()); // 商品ID

                // 市场价
                if (CommonUtil.isNotEmpty(sku.getMarketPrice()))
                {
                    detailPojo.setMarketPrice(sku.getMarketPrice());
                }
                // 成本价
                if (CommonUtil.isNotEmpty(sku.getCostPrice()))
                {
                    detailPojo.setCostPrice(sku.getCostPrice());
                }
                // 会员价
                if (CommonUtil.isNotEmpty(sku.getMemberPrice()))
                {
                    detailPojo.setMemberPrice(sku.getMemberPrice());
                }
                // 销售价
                if (CommonUtil.isNotEmpty(sku.getSellPrice()))
                {
                    detailPojo.setSalePrice(sku.getSellPrice());
                }
                //商品vip价
                if(CommonUtil.isNotEmpty(sku.getVipPrice())){
                    detailPojo.setVipPrice(sku.getVipPrice());
                }
                //商品U币价
                if(CommonUtil.isNotEmpty(sku.getuPrice())){
                    detailPojo.setuPrice(sku.getuPrice());
                }
                //数量
                if(CommonUtil.isNotEmpty(sku.getNumber())){
                    detailPojo.setNumber(sku.getNumber());
                }
                //商品U币价
                if(CommonUtil.isNotEmpty(sku.getFlagPrice())){
                    detailPojo.setFlagPrice(sku.getFlagPrice());
                }
                //推广价
                if(CommonUtil.isNotEmpty(sku.getPromotionPrice())){
                    detailPojo.setPromotionPrice(sku.getPromotionPrice());
                }
                detailPojo.setpStorage(CommonUtil.isNotEmpty(sku.getStorage()) ? Short.valueOf(sku.getStorage().toString()) : 0); // 库存值 为空时默认为0
                //totalStorage = totalStorage + detailPojo.getpStorage(); // 累计当前商品总库存数

                // 处理当前sku的规格项
                if (CommonUtil.isNotEmpty(sku.getSpeciList()))
                {
                    speciModelList = new ArrayList<>();
                    for (ProductSpeciModel speci : sku.getSpeciList()){
                        speci.setSpeciValue(speci.getSpeciValue().trim());
                        speciModelList.add(speci);
                    }
                }

                // 添加操作员信息
                if (CommonUtil.isNotEmpty(prodInfoModel.getCreateUser()))
                {
                    detailPojo.setCreateUser(prodInfoModel.getCreateUser());
                }

                // 生成sku编码
                detailPojo.setSkuCode(productUtil.generateSku(prodPojo, sku.getSpeciList(), salt));
                detailPojo.setProdSpeci(CommonUtil.objectToJsonString(speciModelList)); // 将商品规格项转成json保存
                detailPojoList.add(detailPojo); // 添加到商品详情列表中
            }

        }

        try
        {
            int result = productDetailBusiServiceImpl.addProductDetail(detailPojoList, prodPojo);
            if (result <= 0)
            {
                throw new BusinessException("添加商品详情信息未成功");
            }
            else
            {
                //更新商品总库存
                ProductDetailPojo productDetailPojo = new ProductDetailPojo();
                productDetailPojo.setProdId(prodInfoModel.getProductId());
                List<ProductDetailPojo> productDetailList= productDetailInfoServiceImpl.productDetailList(productDetailPojo);
                if(CommonUtil.isNotEmpty(productDetailList)){
                    for(ProductDetailPojo productDetail:productDetailList){
                        totalStorage=totalStorage+productDetail.getpStorage();
                    }
                }

                // 记录数量总体数量
                prodPojo.setpStorage(totalStorage);
                // 记录单品生成计算值
                prodPojo.setSalt(salt);
                int productStorage= productBusiServiceImpl.updateTotalStorage(prodPojo);
                if (productStorage != 1)
                {
                    throw new BusinessException("总库存更新失败");
                }
                //成功返回skuCode码
                LoggerUtil.info(AddProductInfoExecutor.class, "添加单品信息成功：{}条记录", new Object[]{result});
                return detailPojoList.get(0).getSkuCode();
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(AddProductInfoExecutor.class, e);
            throw new BusinessException("添加商品详情信息失败");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel prodInfoModel = (ProductInfoModel) model;

        if (CommonUtil.isEmpty(prodInfoModel.getProductId()))
        {
            errors.rejectError("productId", "商品ID不能为空");
        }

        if (CommonUtil.isEmpty(prodInfoModel.getSkuList()))
        {
            errors.rejectError("skuList", "售卖商品信息不能为空");
        }
        else
        {
            // 每个sku的sellPrice不能为空
            for (ProductSkuModel sku : prodInfoModel.getSkuList())
            {
                if (CommonUtil.isEmpty(sku.getSellPrice()))
                {
                    errors.rejectNull("sellPrice", null,"销售价");
                    return errors;
                }

                if (CommonUtil.isEmpty(sku.getVipPrice())) {
                    errors.rejectNull("vipPrice", null,"sku会员价");
                    return errors;
                }
            }
        }

        return errors;
    }

}
