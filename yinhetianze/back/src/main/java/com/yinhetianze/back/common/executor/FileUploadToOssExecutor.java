package com.yinhetianze.back.common.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httprequest.SpringBootFileModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class FileUploadToOssExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        SpringBootFileModel fileModel = (SpringBootFileModel)model;
        File file = null;
        try{
            Object fileName = CommonUtil.getFieldValue(fileModel.getFile(),"filename");
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            int index = fileName.toString().lastIndexOf(".");
            String storeFileExt = fileName.toString().substring(index);
            String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
            String busiPath = getImagePath(fileModel.getFileBusiType());
            String sroeFilePath = fileUploadPath+backOssRootPath+busiPath;
            file = FileUtil.uploadFile(fileModel.getFile(),sroeFilePath,storeFileName);

            String ossPath = backOssRootPath+busiPath;

            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(FileUploadToOssExecutor.class, "FileUploadToOssExecutor add read local file "+storeFilePath+File.separatorChar+file.getName());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            return key;
        }catch(Exception e){
            LoggerUtil.error(FileUploadToOssExecutor.class,e.toString());
            throw new BusinessException();
        }finally{
            if(file!=null){
                file.delete();
            }
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
