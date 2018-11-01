package com.yinhetianze.business.shop.executor;

import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;

@Component
public class UpdateShopQrcodeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ShopBusiService shopBusiServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopModel busiShopModel=(BusiShopModel)model;
        BusiShopPojo busiShopPojo=new BusiShopPojo();
        busiShopPojo.setId(busiShopModel.getId());
        try{
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            //String fileUploadPath = "d:\\testFile";
            String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
            String shopSpreadQrcode = sysPropertiesUtils.getStringValue("shopSpreadQrcode");
            String fileName = CommonUtil.getSerialnumber()+".png";

            String dir = fileUploadPath+shopOssRootPath+shopSpreadQrcode;
            String finalPath = dir+ File.separator+fileName;
            String wapUrl = sysPropertiesUtils.getStringValue("wapUrl");
            byte[] b = QrcodeUtils.createQrcode(wapUrl+"shopIndex?shopId="+busiShopModel.getId(),null);
            java.io.File fileDir = new  java.io.File(dir);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            java.io.File qrcodeFile = new  java.io.File(finalPath);
            FileOutputStream fos = new FileOutputStream(qrcodeFile);
            fos.write(b);

            String key = ossFileUpload.fileUpload(qrcodeFile,shopOssRootPath+shopSpreadQrcode);
            if(key == null){
                throw new Exception(" oss 店铺二维码文件上传失败!"+finalPath);
            }else{
                busiShopPojo.setShopQrcode(key);
            }
        }catch(Exception e){
            e.printStackTrace();
            LoggerUtil.error(UpdateShopQrcodeExecutor.class," 店铺二维码失败!  "+e.getMessage());
        }
        int result=shopBusiServiceImpl.updateByPrimaryKeySelective(busiShopPojo);
        if(result<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }
}
