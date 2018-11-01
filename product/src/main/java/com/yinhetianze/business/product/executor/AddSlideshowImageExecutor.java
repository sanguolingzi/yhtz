package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductImgModel;
import com.yinhetianze.business.product.service.ProductImgBusiService;
import com.yinhetianze.business.product.service.ProductImgInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.pojo.product.ProductImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 添加轮播图片
 */
@Service
public class AddSlideshowImageExecutor extends AbstractRestBusiExecutor<String>
{

//    @Value("${fileUploadPath}")
//    private String fileUploadPath;
//
//    @Value("${ossRootPath}")
//    private String ossRootPath;
//
//    @Value("${goodsImagePath}")
//    private String goodsImagePath;

    @Autowired
    private ProductImgBusiService productImgBusiServiceImpl;

    @Autowired
    private ProductImgInfoService productImgInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        {
            try {
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String ossRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
                String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
                ProductImgModel productImgModel = (ProductImgModel)model;
                ProductImgPojo productImgPojoParam = new ProductImgPojo();
                productImgPojoParam.setProductId(productImgModel.getProductId());
                productImgPojoParam.setDelFlag((short)0);
                //查询当前商品是否已有轮播图
                List<ProductImgPojo> productImgPojoList=productImgInfoServiceImpl.selectProductImgList(productImgPojoParam);
                //前台已有图片数组一
                String [] arr1=productImgModel.getProductName();
                if(CommonUtil.isNotNull(productImgPojoList) && productImgPojoList.size()>0 ){
                        List<ProductImgPojo>  deleteImg =productImgPojoList.stream().filter(
                                productImg ->{
                                    return Arrays.stream(arr1).noneMatch(
                                            p ->{
                                                return (productImg.getPhotoUrl().split("/")[productImg.getPhotoUrl().split("/").length-1].equals(p));
                                            }
                                    );
                                }
                        ).collect(Collectors.toList());
                        //后台剩余的数组进行删除操作
                        if(CommonUtil.isNotEmpty(deleteImg)){
                            deleteImg.stream().forEach(productImgPojo->{
                                productImgPojo.setDelFlag((short)1);
                                productImgBusiServiceImpl.updateProductImgList(productImgPojo);
                            });
                        }
                }
                //前台传递数组判断
                       List<String> frontImgList = Arrays.stream(arr1).filter(p2->{
                            return productImgPojoList.stream().noneMatch(productImg->{
                               return (p2.equals(productImg.getPhotoUrl().split("/")[productImg.getPhotoUrl().split("/").length-1]));
                           });
                              }
                        ).collect(Collectors.toList());

                String ossPath = ossRootPath+goodsImagePath;
                String storeFilePath = fileUploadPath + goodsImagePath;
                //获取地址名称数组
                if(frontImgList.size()>0){
                    for (int i=0; i<frontImgList.size();i++){
                        ProductImgPojo  productImgPojo =new ProductImgPojo();
                        productImgPojo.setProductId(productImgModel.getProductId());
                        //读取本地文件
                        File file = FileUtil.loadFile(storeFilePath,frontImgList.get(i));
                        //上传oss
                        String key = ossFileUpload.fileUpload(file, ossPath);
                        if(key == null)
                            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                        //删除本地文件
                        //file.delete();
                        productImgPojo.setPhotoUrl(key);
                        productImgPojo.setSort((short) i);
                        int result=productImgBusiServiceImpl.insertSelective(productImgPojo);
                        if(result <= 0)
                            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    }
                }
                return "success";
            } catch (Exception e) {
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }
    }


        @Override
        protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ProductImgModel productImgModel = (ProductImgModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

//        if(productImgModel.getPhotoUrl() == null){
//            errorMessage.rejectError("photoUrl",null,"图片地址");
//        }

        if(productImgModel.getProductId() == null){
            errorMessage.rejectNull("productId",null,"商品id");
            return errorMessage;
        }
//        if(productImgModel.getSort() == null){
//            errorMessage.rejectNull("sort",null,"排序号");
//            return errorMessage;
//        }

        return errorMessage;
    }
}
