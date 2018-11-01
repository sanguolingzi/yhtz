package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductInfoModel;
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
 * 添加商品基本信息
 */
@Service
public class ModifyOneProductInfoExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        // 前台传递的商品单品列表信息
        ProductInfoModel prodInfoModel = (ProductInfoModel) model;

        if (CommonUtil.isNotEmpty(prodInfoModel.getProductId())){
            // 查询当前商品基本信息
            ProductPojo prodPojo = new ProductPojo();
            prodPojo.setId(prodInfoModel.getProductId());
            prodPojo = productInfoServiceImpl.findProduct(prodPojo);
            if (CommonUtil.isEmpty(prodPojo)){
                throw new BusinessException("商品信息不存在");
            }
            //如果商品上下架状态为空可以进行修改  或者商品上下架状态不为空 则必须是下架状态才能进行修改
            if(CommonUtil.isNotEmpty(prodPojo.getpStatus()) && prodPojo.getpStatus() !=1){
                LoggerUtil.info(AddShopProductExecutor.class, "下架商品才能进行修改");
                throw new BusinessException("下架商品才能进行修改");
            }
            prodPojo.setUpdateTime(new Date()); // 设置更新时间
            ProductDetailPojo productDetailPojoParam=new ProductDetailPojo();
            productDetailPojoParam.setSkuCode(prodInfoModel.getSkuCode());
            productDetailPojoParam.setProdId(prodInfoModel.getProductId());
            //根据skuCode查询修改规格存不存在
            ProductDetailPojo  productDetailPojo = productDetailInfoServiceImpl.selectProductDetailPojo(productDetailPojoParam);
            if (CommonUtil.isEmpty(productDetailPojo)){
                throw new BusinessException("修改信息不存在");
            }
            ProductDetailPojo detailPojo = null; // 循环时的单品值对象信息
            List<ProductSpeciModel> speciModelList = null; // 循环时的单品的规格列表
            Integer totalStorage = 0; // 当前商品总库存数
            Integer salt = new Random().nextInt(10); // 盐值记录,随机从个位
            salt ++; // 盐值更新
            detailPojo = new ProductDetailPojo();
            detailPojo.setId(productDetailPojo.getId());
            detailPojo.setUpdateTime(new Date()); // 设置更新时间
            detailPojo.setProdId(prodInfoModel.getProductId()); // 商品ID

            // 市场价
            if (CommonUtil.isNotEmpty(prodInfoModel.getSku().getMarketPrice()))
            {
                detailPojo.setMarketPrice(prodInfoModel.getSku().getMarketPrice());
            }
            // 成本价
            if (CommonUtil.isNotEmpty(prodInfoModel.getSku().getCostPrice()))
            {
                detailPojo.setCostPrice(prodInfoModel.getSku().getCostPrice());
            }
            // 会员价
            if (CommonUtil.isNotEmpty(prodInfoModel.getSku().getMemberPrice()))
            {
                detailPojo.setMemberPrice(prodInfoModel.getSku().getMemberPrice());
            }
            // 销售价
            if (CommonUtil.isNotEmpty(prodInfoModel.getSku().getSellPrice()))
            {
                detailPojo.setSalePrice(prodInfoModel.getSku().getSellPrice());
            }

            //vip价
            if(CommonUtil.isNotEmpty(prodInfoModel.getSku().getVipPrice())){
                detailPojo.setVipPrice(prodInfoModel.getSku().getVipPrice());
            }

            //u币价
            if(CommonUtil.isNotEmpty(prodInfoModel.getSku().getuPrice())){
                detailPojo.setuPrice(prodInfoModel.getSku().getuPrice());
            }
            //数量
            if(CommonUtil.isNotEmpty(prodInfoModel.getSku().getNumber())){
                detailPojo.setNumber(prodInfoModel.getSku().getNumber());
            }
            //商品U币价
            if(CommonUtil.isNotEmpty(prodInfoModel.getSku().getFlagPrice())){
                detailPojo.setFlagPrice(prodInfoModel.getSku().getFlagPrice());
            }
            // 会员价
            if (CommonUtil.isNotEmpty(prodInfoModel.getSku().getPromotionPrice()))
            {
                detailPojo.setPromotionPrice(prodInfoModel.getSku().getPromotionPrice());
            }

            detailPojo.setpStorage(CommonUtil.isNotEmpty(prodInfoModel.getSku().getStorage()) ? Short.valueOf(prodInfoModel.getSku().getStorage().toString()) : 0); // 库存值 为空时默认为0
            //totalStorage = totalStorage + detailPojo.getpStorage(); // 累计当前商品总库存数

            // 处理当前sku的规格项
            if (CommonUtil.isNotEmpty(prodInfoModel.getSku().getSpeciList()))
            {
                speciModelList = new ArrayList<>();
                for (ProductSpeciModel speci : prodInfoModel.getSku().getSpeciList()){
                    speci.setSpeciValue(speci.getSpeciValue().trim());
                    speciModelList.add(speci);
                }
            }

            // 添加操作员信息
            if (CommonUtil.isNotEmpty(prodInfoModel.getCreateUser()))
            {
                detailPojo.setCreateUser(prodInfoModel.getCreateUser());
                detailPojo.setUpdateUser(prodInfoModel.getCreateUser());
            }

            // 生成sku编码
           // detailPojo.setSkuCode(productUtil.generateSku(prodPojo, prodInfoModel.getSku().getSpeciList(), salt));
            detailPojo.setProdSpeci(CommonUtil.objectToJsonString(speciModelList)); // 将商品规格项转成json保存
            //根据skuCode修改该商品的信息
            //productDetailBusiServiceImpl.updateSkuPojo(detailPojo);
            try
            {
                int result = productDetailBusiServiceImpl.updateSkuPojo(detailPojo);
                if (result < 0)
                {
                    throw new BusinessException("更新商品信息未成功");
                }
                else
                {
                    //更新商品总库存
                    ProductDetailPojo product = new ProductDetailPojo();
                    product.setProdId(prodInfoModel.getProductId());
                    List<ProductDetailPojo> productDetailList= productDetailInfoServiceImpl.productDetailList(product);
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
                    LoggerUtil.info(ModifyOneProductInfoExecutor.class, "更新商品[{}]详情信息成功");
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(ModifyOneProductInfoExecutor.class, e);
                throw new BusinessException("更新商品规格信息失败");
            }
        }
        else {
            throw new BusinessException("商品ID不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel infoModel = (ProductInfoModel) model;

        // 商品ID不能为空
        if (CommonUtil.isEmpty(infoModel.getProductId()))
        {
            errors.rejectNull("productId", null,"商品Id");
        }

        // 商品sku列表不能为空
        if (CommonUtil.isEmpty(infoModel.getSku())) {
            errors.rejectNull("sku", null,"商品sku");
        }
        else {
            // 每个sku的价格不能为空
                if (CommonUtil.isEmpty(infoModel.getSku().getSellPrice())) {
                    errors.rejectNull("sellPrice", null,"sku价格");
                    return errors;
                }

                if (CommonUtil.isEmpty(infoModel.getSku().getVipPrice())) {
                    errors.rejectNull("vipPrice", null,"sku会员价");
                    return errors;
                }
        }
        //商品skuCode
        if(CommonUtil.isEmpty(infoModel.getSkuCode())){
            errors.rejectNull("skuCode", null,"商品skuCode");
        }
        return errors;
    }
}
