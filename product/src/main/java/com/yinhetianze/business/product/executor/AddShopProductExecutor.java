package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.service.SysProdauditInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
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
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 添加商品基本信息
 */
@Service
public class AddShopProductExecutor extends AbstractRestBusiExecutor<Integer>{

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

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Integer executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductModel prodModel = (ProductModel) model;
        ProductPojo prodPojo = new ProductPojo();
        ProductPojo prodPojoId = new ProductPojo();
        //判断该商品是否是登录店铺的商品
        prodPojoId=productUtil.setProductToke(model.getToken(),prodModel.getProductId());
        if(CommonUtil.isNotEmpty(prodModel.getProductId())){
            prodPojo.setId(prodModel.getProductId());
        }
        // 设置商品非空值和默认值
        productUtil.setProdFixInfo(prodPojo, prodModel);

        // 设置商品分类
        productUtil.setProdCate(prodPojo, prodModel);

        // 设置商品显示价格与库存
        //productUtil.setProdPriceStorage(prodPojo, prodModel);

        // 设置商品运费
        productUtil.setProdFreight(prodPojo, prodModel);

        // Pc设置店铺信息
        productUtil.setPcShopInfo(prodPojo, prodModel);

        // 新增信息
        productUtil.setOperCreateInfo(prodPojo, prodModel);

        //设置店铺内分类
        productUtil.setShopCateId(prodPojo, prodModel);

        // 设置店铺内品牌
        productUtil.setShopBrandId(prodPojo, prodModel);
        //设置抽成比例或者现金抽成
        //productUtil.setSettlementProportion(prodPojo, prodModel);

        //电子发票
        prodPojo.setElectronicInvoice(prodModel.getElectronicInvoice());

        //商品类型
        prodPojo.setProductDistinction(prodModel.getProductDistinction());

        //商品种类
        prodPojo.setProductType(prodModel.getProductType());

        //商品邮费
        productUtil.setIsFreightFree(prodPojo, prodModel);
        //上架状态设置
        productUtil.setPstatus(prodPojo, prodModel);
        //详情描述
        if(CommonUtil.isNotEmpty(prodModel.getRemark())){
            prodPojo.setRemark(prodModel.getRemark());
        }
        //设置商品零售价 供货价 库存
        productUtil.setShopProductPriceStorage(prodPojo, prodModel);
        try {
            //执行修改信息
            if(CommonUtil.isNotEmpty(prodModel.getProductId())){
                return updateProductInfo(prodPojo, prodModel,prodPojoId);
                // 执行添加商品信息
            }else{
                //设置商品的默认销量值
                int productId= addProductInfo(prodPojo, prodModel);
                productUtil.setProductSales(productId);
                return productId;
            }
        } catch (Exception e) {
            LoggerUtil.error(AddShopProductExecutor.class, e);
            throw new BusinessException("添加商品信息失败");
        }

    }

    /**
     * 添加商品入库操作
     *
     * @param prodPojo
     * @param prodModel
     * @return
     * @throws BusinessException
     */
    private Integer addProductInfo(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException{
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String productOssRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
            String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            //上传商品主图至oss
            String ossPath = productOssRootPath + goodsImagePath;
            String storeFilePath = fileUploadPath + goodsImagePath;
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath, prodPojo.getProductImg());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if (key == null)
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            //删除本地文件
            prodPojo.setProductImg(key);
            // 执行添加商品操作
            int  result= productBusiServiceImpl.addProduct(prodPojo);
            if (result <= 0) {
                LoggerUtil.info(AddShopProductExecutor.class, "添加商品未成功");
                throw new BusinessException("添加商品未成功");
            } else {
                // 重新获取添加成功的商品信息
                ProductPojo newProd = new ProductPojo();
                newProd.setId(prodPojo.getId());
                prodPojo = productInfoServiceImpl.findProduct(newProd);
                if (CommonUtil.isNotEmpty(prodPojo)) {
                    return prodPojo.getId();
                } else {
                    LoggerUtil.info(AddShopProductExecutor.class, "未能获取添加的商品信息");
                    throw new BusinessException("未能获取添加的商品信息");
                }
            }

        } catch (Exception e) {
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }


    /**
     * 修改商品入库操作
     *
     * @param prodPojo
     * @param prodModel
     * @return
     * @throws BusinessException
     */
    private Integer updateProductInfo(ProductPojo prodPojo, ProductModel prodModel,ProductPojo prodPojoId) throws BusinessException{
        try {
            int result=0;
            if(prodPojoId.getProductImg().equals(prodModel.getProdImg())){
                result= productBusiServiceImpl.modifyProduct(prodPojo);
            }else{
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String productOssRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
                String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
                //上传商品主图至oss
                String ossPath = productOssRootPath + goodsImagePath;
                String storeFilePath = fileUploadPath + goodsImagePath;
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath, prodPojo.getProductImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if (key == null)
                    throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                //删除本地文件
                prodPojo.setProductImg(key);
                // 执行添加商品操作
                result= productBusiServiceImpl.modifyProduct(prodPojo);
            }
            if(result !=1){
                LoggerUtil.info(AddShopProductExecutor.class, "添加商品未成功");
                throw new BusinessException("添加商品未成功");
            }
            return prodPojo.getId();
        } catch (Exception e) {
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errors = new ErrorMessage();
        ProductModel prodModel = (ProductModel) model;

        // 商品名称不能为空
        if (CommonUtil.isEmpty(prodModel.getProdName())) {
            errors.rejectNull("productName", null, "商品名称");
        }

        // 商品分类ID不能为空
        if (CommonUtil.isEmpty(prodModel.getProdCateId())) {
            errors.rejectNull("categoryId", null, "商品分类Id");
        }

        // 商品主图不能为空
        if (CommonUtil.isEmpty(prodModel.getProdImg())) {
            errors.rejectNull("productImg", null, "商品主图");
        }

        // 商品标题不能为空
        if (CommonUtil.isEmpty(prodModel.getProdTitle())) {
            errors.rejectNull("title", null, "商品标题");
        }

        //店内分类Id不能为空
        if(CommonUtil.isEmpty(prodModel.getShopCateId())){
            errors.rejectError("shopcateId",null,"店铺内分类Id");
        }
        //店铺内品牌Id不能为空
        if(CommonUtil.isEmpty(prodModel.getShopBrandId())){
            errors.rejectError("shopBrandId",null,"店铺内品牌分类Id");
        }
        //商品类型不能为空
        if (CommonUtil.isEmpty(prodModel.getProductDistinction())) {
            errors.rejectNull("productDistinction", null, "商品类型");
        }
        if(prodModel.getProductDistinction()!=0 && prodModel.getProductDistinction()!=1){
            errors.rejectError("productDistinction", null, "商品类型错误");
        }
        //商品种类不能为空
        if (CommonUtil.isEmpty(prodModel.getProductType())) {
            errors.rejectNull("productType", null, "商品种类");
        }
        if(prodModel.getProductType()!=0 && prodModel.getProductType()!=1){
            errors.rejectError("productType", null, "商品种类错误");
        }
        //商品上下架判断
        if(CommonUtil.isNotEmpty(prodModel.getDownTime())&&CommonUtil.isEmpty(prodModel.getUpTime())){
            errors.rejectError("downTime", null, "请先设置商品的上架时间");
        }
        //商品零售价
        if(CommonUtil.isEmpty(prodModel.getRetailPrice())){
            errors.rejectNull("retailPrice", null, "商品零售价");
        }
        //商品供货价
        if(CommonUtil.isEmpty(prodModel.getSupplyPrice())){
            errors.rejectNull("supplyPrice", null, "商品供货价");
        }
        return errors;
    }

}
