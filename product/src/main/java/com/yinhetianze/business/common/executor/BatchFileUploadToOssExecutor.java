package com.yinhetianze.business.common.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httprequest.SpringBootFileModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchFileUploadToOssExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object  executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SpringBootFileModel fileModel = (SpringBootFileModel)model;
        List<String> keyList = new ArrayList<String>();
        File file = null;
        String key;
        try{
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            for(MultipartFile multipartFile : fileModel.getFiles()){
                Object fileName = CommonUtil.getFieldValue(multipartFile,"filename");
                int index = fileName.toString().lastIndexOf(".");
                String storeFileExt = fileName.toString().substring(index);
                String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
                String busiPath = getImagePath(fileModel.getFileBusiType());
                String sroeFilePath = fileUploadPath+getImagePath(fileModel.getFileBusiType())+busiPath;
                file = FileUtil.uploadFile(multipartFile,sroeFilePath,   storeFileName);
                String productOssRootPath = sysPropertiesUtils.getStringValue("productOssRootPath");
                String ossPath = productOssRootPath+busiPath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(BatchFileUploadToOssExecutor.class, "FileUploadToOssExecutor add read local file "+storeFilePath+File.separatorChar+file.getName());
                //上传oss
                key=ossFileUpload.fileUpload(file, ossPath);
                keyList.add(key);
            }
            return keyList;
        }
        catch(Exception e){
            LoggerUtil.error(BatchFileUploadToOssExecutor.class,e.toString());
            throw new BusinessException();
        }finally{
            if(file!=null){
                file.delete();
            }
        }
    }

    /**
     * 1、商品    2、品牌  3 分类   4 商品详情
     * @param fileBusiType
     * @return
     */
    private String getImagePath(String fileBusiType){
        if("1".equals(fileBusiType)){
            String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
            return goodsImagePath;
        }else if("2".equals(fileBusiType)){
            String brandImagePath = sysPropertiesUtils.getStringValue("brandImagePath");
            return brandImagePath;
        }else if("3".equals(fileBusiType)){
            String categoryImagePath = sysPropertiesUtils.getStringValue("categoryImagePath");
            return categoryImagePath;
        }else if("4".equals(fileBusiType)){
            String categoryImagePath = sysPropertiesUtils.getStringValue("goodsDetailImagePath");
            return categoryImagePath;
        }else{
            String commonImagePath = sysPropertiesUtils.getStringValue("commonImagePath");
            return commonImagePath;
        }
    }
}
