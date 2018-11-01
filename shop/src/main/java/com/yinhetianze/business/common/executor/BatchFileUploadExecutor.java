package com.yinhetianze.business.common.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
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
public class BatchFileUploadExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        SpringBootFileModel fileModel = (SpringBootFileModel)model;
        List<String> keyList = new ArrayList<String>();
        try{
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            for(MultipartFile multipartFile : fileModel.getFiles()){
                Object fileName = CommonUtil.getFieldValue(multipartFile,"filename");
                int index = fileName.toString().lastIndexOf(".");
                String storeFileExt = fileName.toString().substring(index);
                String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
                String stroeFilePath = fileUploadPath+backOssRootPath+getImagePath(fileModel.getFileBusiType());
                File file = null;
                //需要进行水印处理的
                if(java.util.Arrays.asList(new String[]{"2","6","7"}).contains(fileModel.getFileBusiType())){
                    String waterContent = sysPropertiesUtils.getStringValue("waterContent");
                    file = FileUtil.uploadFile(fileModel.getFile(),stroeFilePath,storeFileName,waterContent);
                }else{
                    file = FileUtil.uploadFile(fileModel.getFile(),stroeFilePath,storeFileName);
                }
                keyList.add(file.getName());
            }
            return keyList;
        }catch(Exception e){
            LoggerUtil.error(FileUploadExecutor.class,e.toString());
            throw new BusinessException();
        }
    }
    /**
     * 1、店铺品牌图   2、企业营业执照  3、店铺logo 4、店铺主图 5 门头照 6身份证照 7 分类图片 8店铺wap主图
     * @param fileBusiType
     * @return
     */
    private String getImagePath(String fileBusiType){
        if("1".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("shopBrandImagePath");
        }else if("2".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("shoplicImagePath");
        }else if("3".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("shopLogoImagePath");
        }else if("4".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("shopMainImagePath");
        }else if("5".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("companyPhotoImagePath");
        }else if("6".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("idCardImagePath");
        }else if("7".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("classifyImagePath");
        }else if("8".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("shopWapImagePath");
        }else{
            return sysPropertiesUtils.getStringValue("commonImagePath");
        }
    }

}
