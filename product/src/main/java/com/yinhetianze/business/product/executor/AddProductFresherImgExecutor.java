package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.ProductFresherImgModel;
import com.yinhetianze.business.product.service.ProductFresherImgBusiService;
import com.yinhetianze.business.product.service.ProductFresherImgInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.pojo.product.ProductFresherImgPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddProductFresherImgExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ProductFresherImgInfoService productFresherImgInfoServiceImpl;

    @Autowired
    private ProductFresherImgBusiService productFresherImgBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String categoryImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            ProductFresherImgModel productFresherImgModel=(ProductFresherImgModel)model;
            ProductFresherImgPojo productFresherImgPojo=new ProductFresherImgPojo();
            productFresherImgPojo.setProdId(productFresherImgModel.getProdId());
            productFresherImgPojo.setDelFlag((short)0);
            //查询当前商品是否已有轮播图
            List<ProductFresherImgPojo> productFresherImgPojoList=productFresherImgInfoServiceImpl.selectProductFresherImgList(productFresherImgPojo);
            //前台已有图片数组一
            String [] arr1=productFresherImgModel.getProductImgs();
            if(CommonUtil.isNotNull(productFresherImgPojoList) && productFresherImgPojoList.size()>0 ){
                List<ProductFresherImgPojo>  deleteImg =productFresherImgPojoList.stream().filter(
                        temp ->{
                            return Arrays.stream(arr1).noneMatch(
                                    p ->{
                                        return (temp.getProdImg().split("/")[temp.getProdImg().split("/").length-1].equals(p));
                                    }
                            );
                        }
                ).collect(Collectors.toList());
                //后台剩余的数组进行删除操作
                if(CommonUtil.isNotEmpty(deleteImg)){
                    deleteImg.stream().forEach(fresherImgPojo->{
                        fresherImgPojo.setDelFlag((short)1);
                        productFresherImgBusiServiceImpl.updateProductFresherImgPojoList(fresherImgPojo);
                    });
                }
            }
            //前台传递数组判断
            List<String> frontImgList = Arrays.stream(arr1).filter(p2->{
                        return productFresherImgPojoList.stream().noneMatch(fresherImg->{
                            return (p2.equals(fresherImg.getProdImg().split("/")[fresherImg.getProdImg().split("/").length-1]));
                        });
                    }
            ).collect(Collectors.toList());

            String ossPath = categoryImagePath;
            String storeFilePath = fileUploadPath + categoryImagePath;
            //获取地址名称数组
            if(frontImgList.size()>0){
                for (int i=0; i<frontImgList.size();i++){
                    ProductFresherImgPojo dbPojo=new ProductFresherImgPojo();
                    dbPojo.setProdId(productFresherImgModel.getProdId());
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath,frontImgList.get(i));
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    //删除本地文件
                    file.delete();
                    dbPojo.setProdImg(key);
                    dbPojo.setSort((short)i);
                    int result=productFresherImgBusiServiceImpl.insertSelective(dbPojo);
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
