package com.yinhetianze.business.product.executor;

import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductImgBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.product.utils.ProductUtil;
import com.yinhetianze.business.unit.service.UnitInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.HttpClientUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
import com.yinhetianze.pojo.category.CategoryPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.unit.UnitPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加商品基本信息
 */
@Service
public class AddProductExecutor extends AbstractRestBusiExecutor<Integer>{

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
    protected Integer executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        ProductModel prodModel = (ProductModel) model;
        ProductPojo prodPojo = new ProductPojo();

        // 设置商品非空值和默认值
        productUtil.setProdFixInfo(prodPojo, prodModel);

        // 商品编号/批次号
        //productUtil.setProdCode(prodPojo, prodModel);

        // 设置商品分类
        productUtil.setProdCate(prodPojo, prodModel);

        // 设置商品品牌
        if(prodModel.getProductDistinction()==0) {
            productUtil.setProdBrand(prodPojo, prodModel);
        }
        // 设置商品让利比例与赠送积分比例
        //productUtil.setProdProportion(prodPojo, prodModel);

        // 设置商品计量单位
        productUtil.setProdUnit(prodPojo, prodModel);

        // 设置商品显示价格与库存
        productUtil.setProdPriceStorage(prodPojo, prodModel);

        // 设置商品运费
        if(prodModel.getProductDistinction()==0){
            productUtil.setProdFreight(prodPojo, prodModel);
        }

        // 设置店铺信息 获取配置的自营店铺信息
        String shopId= sysPropertiesUtils.getStringValue("shopId");
        prodModel.setShopId(Integer.valueOf(shopId));
        productUtil.setShopInfo(prodPojo, prodModel);

        // 设置商品标签信息
        //productUtil.setProdTag(prodPojo, prodModel);

        // 设置发货地址
        //productUtil.setShipAddress(prodPojo, prodModel);

        // 新增信息
        productUtil.setOperCreateInfo(prodPojo, prodModel);

        //后台自营商品默认审核通过状态3
        if (CommonUtil.isNotNull(prodModel.getAuditState())) {
            prodPojo.setAuditState((short) 3);
        }
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
        //设置商品类型
            prodPojo.setProductDistinction(prodModel.getProductDistinction());
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
            }
        }
        //设置友旗币
        if(CommonUtil.isNotEmpty(prodModel.getFlagPrice())){
            prodPojo.setFlagPrice(prodModel.getFlagPrice());
        }
        try {
            // 执行添加商品信息
            int productId = addProductInfo(prodPojo, prodModel);
            //设置商品的默认销量值
            productUtil.setProductSales(productId);
            return productId;
        } catch (Exception e) {
            LoggerUtil.error(AddProductExecutor.class, e);
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
            //file.delete();
            prodPojo.setProductImg(key);
            // 执行添加商品操作
            int result = productBusiServiceImpl.addProduct(prodPojo);
            if (result <= 0) {
                LoggerUtil.info(AddProductExecutor.class, "添加商品未成功");
                throw new BusinessException("添加商品未成功");
            } else {
                if (CommonUtil.isNotEmpty(prodPojo.getId())) {
                    String qrcodeImagePath = sysPropertiesUtils.getStringValue("qrcodeImagePath");
                    String fileName = CommonUtil.getSerialnumber()+".png";
                    String dir = fileUploadPath+productOssRootPath+qrcodeImagePath;
                    String finalPath = dir+ File.separator+fileName;
                    String wapUrl = sysPropertiesUtils.getStringValue("wapUrl");
                    byte[] b = QrcodeUtils.createQrcode(wapUrl+"goodsDetails?id="+prodPojo.getId(),null);
                    java.io.File fileDir = new  java.io.File(dir);
                    if(!fileDir.exists()){
                        fileDir.mkdirs();
                    }
                    java.io.File qrcodeFile = new  java.io.File(finalPath);
                    FileOutputStream fos = new FileOutputStream(qrcodeFile);
                    fos.write(b);

                    String keys = ossFileUpload.fileUpload(qrcodeFile,productOssRootPath+qrcodeImagePath);
                    if(keys == null){
                        throw new Exception(" oss 商品二维码文件上传失败!"+finalPath);
                    }else{
                        ProductPojo productPojo=new ProductPojo();
                        productPojo.setQrcode(key);
                        productPojo.setId(prodPojo.getId());
                        int qrCode=productBusiServiceImpl.modifyProduct(productPojo);
                        if(qrCode!=1){
                            throw new Exception("添加二维码失败!");
                        }
                    }
                    return prodPojo.getId();
                } else {
                    LoggerUtil.info(AddProductExecutor.class, "未能获取添加的商品信息");
                    throw new BusinessException("未能获取添加的商品信息");
                }
            }

        } catch (Exception e) {
            LoggerUtil.error(AddProductExecutor.class,e.getMessage());
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

        // 商品品牌ID不能为空
        /*if (CommonUtil.isEmpty(prodModel.getProdBrandId())) {
            errors.rejectNull("brandId", null, "商品品牌ID");
        }*/

        // 商品编码不能为空
        /*if (CommonUtil.isEmpty(prodModel.getProdCode())) {
            errors.rejectNull("prodCode", null, "商品编码");
        }*/

        // 商品主图不能为空
        if (CommonUtil.isEmpty(prodModel.getProdImg())) {
            errors.rejectNull("productImg", null, "商品主图");
        }

        // 商品计量单位不能为空
        if (CommonUtil.isEmpty(prodModel.getProdUnitId())) {
            errors.rejectNull("prodUnitId", null, "商品计量单位");
        }

/*        // 商品让利比例不能为空
        if (CommonUtil.isEmpty(prodModel.getProfitsProportion())) {
            errors.rejectNull("profitsProportion", null, "商品让利比率");
        }*/

        // 商品销售价格不能为空
        if (CommonUtil.isEmpty(prodModel.getSellPrice())) {
            errors.rejectNull("sellPrice", null, "商品销售价");
        }

//        // 商品店铺ID不能为空
//        if (CommonUtil.isEmpty(prodModel.getShopId())) {
//            errors.rejectError("shopId", null, "商品店铺ID");
//        }

        // 商品标题不能为空
        if (CommonUtil.isEmpty(prodModel.getProdTitle())) {
            errors.rejectNull("title", null, "商品标题");
        }
        //商品类型
        if(CommonUtil.isEmpty(prodModel.getProductDistinction())){
            errors.rejectNull("title", null, "商品类型");
        }

        return errors;
    }
}
