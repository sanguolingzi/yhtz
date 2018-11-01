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
            for(MultipartFile multipartFile : fileModel.getFiles()){
                Object fileName = CommonUtil.getFieldValue(multipartFile,"filename");
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String orderOssRootPath = sysPropertiesUtils.getStringValue("orderOssRootPath");
                int index = fileName.toString().lastIndexOf(".");
                String storeFileExt = fileName.toString().substring(index);
                String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
                String sroeFilePath = fileUploadPath+orderOssRootPath+getImagePath(fileModel.getFileBusiType());
                File file = FileUtil.uploadFile(multipartFile,sroeFilePath,   storeFileName);
                keyList.add(file.getName());
            }
            return keyList;
        }catch(Exception e){
            LoggerUtil.error(FileUploadExecutor.class,e.toString());
            throw new BusinessException();
        }
    }
    /**
     * 1、评论图片   2、售后图片
     * @param fileBusiType
     * @return
     */
    private String getImagePath(String fileBusiType){
        if("1".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("evaluateImagePath");
        } if("2".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("exchangeImagePath");
        } else{
            return sysPropertiesUtils.getStringValue("commonImagePath");
        }
    }
}
