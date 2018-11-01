package com.yinhetianze.business.common.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httprequest.SpringBootFileModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchFileUploadExecutor extends AbstractRestBusiExecutor<Object> {

   /* @Value("${fileUploadPath}")
    private String fileUploadPath;

    @Value("${commonImagePath}")
    private String commonImagePath;

    @Value("${goodsImagePath}")
    private String goodsImagePath;*/
   @Autowired
   private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
        SpringBootFileModel fileModel = (SpringBootFileModel)model;
        List<String> keyList = new ArrayList<String>();
        try{
            for(MultipartFile multipartFile : fileModel.getFiles()){
                Object fileName = CommonUtil.getFieldValue(multipartFile,"filename");
                int index = fileName.toString().lastIndexOf(".");
                String storeFileExt = fileName.toString().substring(index);
                String storeFileName = CommonUtil.getSerialnumber()+storeFileExt;
                String sroeFilePath = fileUploadPath+getImagePath(fileModel.getFileBusiType());
                File file = FileUtil.uploadFile(multipartFile,sroeFilePath,   storeFileName);
                keyList.add(file.getName());
            }
            return keyList;
        }catch(Exception e){
            e.printStackTrace();
            return keyList;
        }
    }

    private String getImagePath(String fileBusiType){
        String commonImagePath = sysPropertiesUtils.getStringValue("productOssRootPath");
        String goodsImagePath = sysPropertiesUtils.getStringValue("goodsImagePath");
        if("1".equals(fileBusiType)){
            return goodsImagePath;
        }else if("2".equals(fileBusiType)){
            return commonImagePath;
        }else{
            return commonImagePath;
        }
    }
}
