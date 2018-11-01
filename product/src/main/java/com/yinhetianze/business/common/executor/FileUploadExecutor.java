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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class FileUploadExecutor extends AbstractRestBusiExecutor<String> {

    /*@Value("${fileUploadPath}")
    private String fileUploadPath;

    @Value("${commonImagePath}")
    private String commonImagePath;

    @Value("${goodsImagePath}")
    private String goodsImagePath;

    @Value("${brandImagePath}")
    private String brandImagePath;

    @Value("${categoryImagePath}")
    private String categoryImagePath;*/
    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
        SpringBootFileModel fileModel = (SpringBootFileModel)model;
        try{
            Object fileName = CommonUtil.getFieldValue(fileModel.getFile(),"filename");
            int index = fileName.toString().lastIndexOf(".");
            String storeFileExt = fileName.toString().substring(index);
            String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
            String busiPath = getImagePath(fileModel.getFileBusiType());
            String sroeFilePath = fileUploadPath+busiPath;
            File file = FileUtil.uploadFile(fileModel.getFile(),sroeFilePath,storeFileName);
            return file.getName();
        }catch(Exception e){
            LoggerUtil.error(FileUploadExecutor.class,e);
            throw new BusinessException();

        }
    }

    /**
     * 1、商品    2、品牌
     * @param fileBusiType
     * @return
     */
    private String getImagePath(String fileBusiType){
        String commonImagePath = sysPropertiesUtils.getStringValue("commonImagePath");
        String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
        String brandImagePath = sysPropertiesUtils.getStringValue("brandImagePath");
        String categoryImagePath = sysPropertiesUtils.getStringValue("categoryImagePath");
        if("1".equals(fileBusiType)){
            return goodsImagePath;
        }else if("2".equals(fileBusiType)){
            return brandImagePath;
        }else if("3".equals(fileBusiType)){
            return categoryImagePath;
        }else{
            return commonImagePath;
        }
    }
}
