package com.yinhetianze.back.common.executor;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class FileUploadExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        SpringBootFileModel fileModel = (SpringBootFileModel)model;
        try{
            Object fileName = CommonUtil.getFieldValue(fileModel.getFile(),"filename");
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            int index = fileName.toString().lastIndexOf(".");
            String storeFileExt = fileName.toString().substring(index);
            String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
            String busiPath = getImagePath(fileModel.getFileBusiType());
            String stroeFilePath = fileUploadPath+backOssRootPath+busiPath;
            File file = FileUtil.uploadFile(fileModel.getFile(),stroeFilePath,storeFileName);
            LoggerUtil.info(FileUploadExecutor.class,"filePath:"+stroeFilePath+",,,fileName:"+storeFileName);
            return file.getName();
        }catch(Exception e){
            LoggerUtil.error(FileUploadExecutor.class,e.toString());
            throw new BusinessException();
        }
    }

    /**
     * 1、商城活动   2、楼层  3、banner 4、频道   5、帮助中心 6 会员权益 7 会员权益banner
     * @param fileBusiType
     * @return
     */
    private String getImagePath(String fileBusiType){
        if("1".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("mallImagePath");
        }else if("2".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("floorImagePath");
        }else if("3".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("bannerImagePath");
        }else if("4".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("channelImagePath");
        }else if("5".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("helpImagePath");
        }else if("6".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("memberImg");
        }else if("7".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("memberBanner");
        }else if("8".equals(fileBusiType)){
            return sysPropertiesUtils.getStringValue("advertisementImgPath");
        }else{
            return sysPropertiesUtils.getStringValue("commonImagePath");
        }
    }
}
