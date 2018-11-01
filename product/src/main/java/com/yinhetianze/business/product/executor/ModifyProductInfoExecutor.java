package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductInfoModel;
import com.yinhetianze.business.product.model.ProductSkuModel;
import com.yinhetianze.business.product.model.ProductSpeciModel;
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

import static com.sun.tools.doclint.Entity.prod;

/**
 * 添加商品基本信息
 */
@Service
public class ModifyProductInfoExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private ProductDetailBusiService productDetailBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        // 前台传递的商品单品列表信息
        ProductInfoModel prodInfoModel = (ProductInfoModel) model;

        if (CommonUtil.isNotEmpty(prodInfoModel.getProductId()))
        {
            // 查询当前商品基本信息
            ProductPojo prodPojo = new ProductPojo();
            prodPojo.setId(prodInfoModel.getProductId());
            prodPojo = productInfoServiceImpl.findProduct(prodPojo);
            if (CommonUtil.isEmpty(prodPojo))
            {
                throw new BusinessException("商品信息不存在");
            }
            prodPojo.setUpdateTime(new Date()); // 设置更新时间

            // 将原来的规格全部设置成已经删除
            ProductDetailPojo delPojo = new ProductDetailPojo();
            delPojo.setProdId(prodPojo.getId());
            delPojo.setDelFlag((short)1); // 设置为删除1
            delPojo.setpStatus((short)0); // 设置为不可用0

            // 批量保存的商品详情列表
            List<ProductDetailPojo> detailPojoList = new ArrayList<>();
            // 对每个sku商品信息进行设置
            if (CommonUtil.isNotEmpty(prodInfoModel.getSkuList()))
            {
                ProductDetailPojo detailPojo = null; // 循环时的单品值对象信息
                List<ProductSpeciModel> speciModelList = null; // 循环时的单品的规格列表
                Integer totalStorage = 0; // 当前商品总库存数
                Integer salt = new Random().nextInt(10); // 盐值记录,随机从个位数开始计数，该数值会加入到skuCode中
                for (ProductSkuModel sku : prodInfoModel.getSkuList())
                {
                    salt ++; // 盐值更新
                    detailPojo = new ProductDetailPojo();
                    detailPojo.setCreateTime(new Date()); // 创建时间
                    detailPojo.setUpdateTime(new Date()); // 设置更新时间
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

                    detailPojo.setpStorage(CommonUtil.isNotEmpty(sku.getStorage()) ? Short.valueOf(sku.getStorage().toString()) : 0); // 库存值 为空时默认为0
                    totalStorage = totalStorage + detailPojo.getpStorage(); // 累计当前商品总库存数

                    // 处理当前sku的规格项
                    if (CommonUtil.isNotEmpty(sku.getSpeciList()))
                    {
                        speciModelList = new ArrayList<>();
                        for (ProductSpeciModel speci : sku.getSpeciList())
                        {
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
                    detailPojo.setSkuCode(productUtil.generateSku(prodPojo, sku.getSpeciList(), salt));
                    detailPojo.setProdSpeci(CommonUtil.objectToJsonString(speciModelList)); // 将商品规格项转成json保存
                    detailPojoList.add(detailPojo); // 添加到商品详情列表中
                }

                // 记录数量总体数量
                prodPojo.setpStorage(totalStorage);
                // 记录单品生成计算值
                prodPojo.setSalt(salt);
            }

            try
            {
                int result = productDetailBusiServiceImpl.modifyProductDetail(detailPojoList, prodPojo, delPojo);
                if (result < 0)
                {
                    throw new BusinessException("更新商品信息未成功");
                }
                else
                {
                    LoggerUtil.info(ModifyProductInfoExecutor.class, "更新商品[{}]详情信息成功");
                }
            }
            catch (Exception e)
            {
                LoggerUtil.error(ModifyProductInfoExecutor.class, e);
                throw new BusinessException("更新商品规格信息失败");
            }
        }
        else
        {
            throw new BusinessException("商品ID不能为空");
        }

        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductInfoModel infoModel = (ProductInfoModel) model;

        // 商品ID不能为空
        if (CommonUtil.isEmpty(infoModel.getProductId()))
        {
            errors.rejectNull("productId", null,"商品Id");
        }

        // 商品sku列表不能为空
        if (CommonUtil.isEmpty(infoModel.getSkuList()))
        {
            errors.rejectNull("skuList", null,"商品sku");
        }
        else
        {
            // 每个sku的价格不能为空
            for (ProductSkuModel sku : infoModel.getSkuList())
            {
                if (CommonUtil.isEmpty(sku.getSellPrice()))
                {
                    errors.rejectNull("sellPrice", null,"商销售价");
                    return errors;
                }
            }
        }
        return errors;
    }
}
