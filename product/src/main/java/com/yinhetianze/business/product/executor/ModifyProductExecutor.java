package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 添加商品基本信息
 */
@Service
public class ModifyProductExecutor extends AbstractRestBusiExecutor<String>
{
    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Autowired
    private ProductUtil productUtil;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
        String ossRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
        String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
        ProductModel prodModel = (ProductModel) model;
        ProductPojo prodPojo = null;

        // 商品ID校验
        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            throw new BusinessException("商品ID不能为空");
        }
        else
        {
            prodPojo = new ProductPojo();
            prodPojo.setId(prodModel.getProductId());
            //prodPojo.setpStatus((short)1); // 下架商品才能进行修改
            prodPojo = productInfoServiceImpl.findProduct(prodPojo);
            if (CommonUtil.isEmpty(prodPojo))
            {
                throw new BusinessException("可进行修改的商品信息不存在");
            }
            //判断长传图片数据库图片是都一致  一致则不更改图片
            if(!prodModel.getProdImg().equals(prodPojo.getProductImg())){
                try{
                    //上传商品主图至oss
                    String ossPath = ossRootPath + goodsImagePath;
                    String storeFilePath = fileUploadPath + goodsImagePath;
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath, prodModel.getProdImg());
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if (key == null)
                        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    //删除本地文件
                    file.delete();
                    prodModel.setProdImg(key);
                }catch (Exception e) {
                    throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                }

            }
        }

        // 设置商品固定非空信息
        productUtil.setProdFixInfo(prodPojo, prodModel);

        // 设置商品分类信息
        productUtil.setProdCate(prodPojo, prodModel);

        // 店铺信息
//        productUtil.setShopInfo(prodPojo, prodModel);

        // 设置商品品牌
        if(CommonUtil.isNotEmpty(prodModel.getProdBrandId())) {
            productUtil.setProdBrand(prodPojo, prodModel);
        }

        // 设置商品单位
        productUtil.setProdUnit(prodPojo, prodModel);

        // 运费信息
        productUtil.setProdFreight(prodPojo, prodModel);

        // 标签信息
        //productUtil.setProdTag(prodPojo, prodModel);

        // 显示价格与库存
        productUtil.setProdPriceStorage(prodPojo, prodModel);

        // 发货地信息
        //productUtil.setShipAddress(prodPojo, prodModel);

        // 设置操作更新信息
        productUtil.setOperUpdateInfo(prodPojo, prodModel);

        //设置让利比例
        //productUtil.setProdProportion(prodPojo, prodModel);

        //设置商品的分享价
        if(CommonUtil.isNotEmpty(prodModel.getSharePrice())){
            prodPojo.setSharePrice(prodModel.getSharePrice());
        }
        //设置推广价
        if(CommonUtil.isNotEmpty(prodModel.getPromotionPrice())){
            prodPojo.setPromotionPrice(prodModel.getPromotionPrice());
        }
        //设置商品会员价
        if(CommonUtil.isNotEmpty(prodModel.getVipPrice())){
            prodPojo.setVipPrice(prodModel.getVipPrice());
        }
        //设置详情描述
        if(CommonUtil.isNotEmpty(prodModel.getRemark())){
            prodPojo.setRemark(prodModel.getRemark());
        }
        //设置代发店铺
        if(CommonUtil.isNotEmpty(prodModel.getDropShipping())){
            prodPojo.setDropShipping(prodModel.getDropShipping());
            //设置代发店铺Id
            if(CommonUtil.isNotEmpty(prodModel.getDropShippingId()) && prodModel.getDropShipping()==1){
                prodPojo.setDropShippingId(prodModel.getDropShippingId());
            }else{
                prodPojo.setDropShippingId(0);
            }
        }
        //设置友旗币
        if(CommonUtil.isNotEmpty(prodModel.getFlagPrice())){
            prodPojo.setFlagPrice(prodModel.getFlagPrice());
        }
        try
        {
            int result = productBusiServiceImpl.modifyProduct(prodPojo);
            if (result <= 0)
            {
                throw new BusinessException("修改商品信息未成功");
            }
            else
            {
                LoggerUtil.info(ModifyProductExecutor.class, "修改商品信息成功。商品ID：{}", new Object[]{prodModel.getProductId()});
            }
        }
        catch (Exception e)
        {
            LoggerUtil.error(ModifyProductExecutor.class, e);
            throw new BusinessException("修改商品信息失败");
        }
        return null;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params)
    {
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        if (CommonUtil.isEmpty(prodModel.getProductId()))
        {
            errors.rejectNull("productId", null,"商品Id");
        }

        // 商品名称不能为空
        if (CommonUtil.isEmpty(prodModel.getProdName()))
        {
            errors.rejectNull("productName", null, "商品名称");
        }

        // 商品分类ID不能为空
        if (CommonUtil.isEmpty(prodModel.getProdCateId()))
        {
            errors.rejectNull("categoryId", null, "分类Id");
        }

        // 商品品牌ID不能为空

        /*if (CommonUtil.isEmpty(prodModel.getProdBrandId()))
        {
            errors.rejectNull("brandId", null, "品牌");
        }*/

        // 商品编码不能为空
        /*if (CommonUtil.isEmpty(prodModel.getProdCode()))
        {
            errors.rejectNull("prodCode", null, "");
        }*/

        // 商品主图不能为空
        if (CommonUtil.isEmpty(prodModel.getProdImg()))
        {
            errors.rejectNull("productImg", null, "商品主图");
        }

        // 商品计量单位不能为空
        if (CommonUtil.isEmpty(prodModel.getProdUnitId()))
        {
            errors.rejectNull("prodUnitId", null, "商品计量单位");
        }

//        // 商品让利比例不能为空
//        if (CommonUtil.isEmpty(prodModel.getProfitsProportion()))
//        {
//            errors.rejectNull("profitsProportion", null, "商品让利比例");
//        }

        // 商品销售价格不能为空
        if (CommonUtil.isEmpty(prodModel.getSellPrice()))
        {
            errors.rejectNull("sellPrice", null, "商品销售价");
        }

        // 商品店铺ID不能为空
        /*if (CommonUtil.isEmpty(prodModel.getShopId()))
        {
            errors.rejectNull("shopId", null, "");
        }*/

        // 商品标题不能为空
        if (CommonUtil.isEmpty(prodModel.getProdTitle()))
        {
            errors.rejectNull("title", null, "商品标题");
        }

        return super.validate(request, model, params);
    }
}
