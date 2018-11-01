package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.business.shop.service.busi.impl.ShopCompanyBusiServiceImpl;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 企业 修改基础信息(暂时只支持门头照)
 */

@Component
public class UpdateCompanyInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopCompanyModel busiShopCompanyModel  =(BusiShopCompanyModel)model;

        BusiShopCompanyPojo dbEntity =  shopCompanyInfoServiceImpl.selectByCustomerId(busiShopCompanyModel.getCustomerId());
        if(dbEntity == null
                || dbEntity.getId().intValue() != busiShopCompanyModel.getId().intValue()){
            LoggerUtil.error(UpdateCompanyInfoExecutor.class,"dbEntity.getId():"+dbEntity.getId()+"...busiShopCompanyModel.getId():"+busiShopCompanyModel.getId());
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        BusiShopCompanyPojo busiShopCompanyPojo  = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setId(busiShopCompanyModel.getId());
        try{
            if(CommonUtil.isNotEmpty(busiShopCompanyModel.getCompanyPhoto())){
                if(!busiShopCompanyModel.getCompanyPhoto().equals(dbEntity.getCompanyPhoto())){
                    //门头照
                    String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                    //String fileUploadPath = "D:\\testFile";
                    String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
                    String companyPhotoImagePath = sysPropertiesUtils.getStringValue("companyPhotoImagePath");
                    String ossPath = shopOssRootPath+companyPhotoImagePath;
                    String storeFilePath = fileUploadPath +ossPath;
                    LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany add read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getCompanyPhoto());
                    File file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getCompanyPhoto());
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null){
                        LoggerUtil.info(UpdateCompanyInfoExecutor.class, "Oss 文件上传 key is null "+storeFilePath+File.separatorChar+busiShopCompanyModel.getCompanyPhoto());
                        throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
                    }
                    busiShopCompanyPojo.setCompanyPhoto(key);


                    int result = shopCompanyBusiServiceImpl.updateByPrimaryKeySelective(busiShopCompanyPojo);
                    if(result <= 0)
                        throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
                }
            }

        }catch(Exception e){
            if(e instanceof BusinessException)
                throw  (BusinessException)e;

            LoggerUtil.info(UpdateCompanyInfoExecutor.class,"UpdateCompanyInfoExecutor failed:"+e.getMessage());
            throw new BusinessException("BC0037",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopCompanyModel busiShopCompanyModel  =(BusiShopCompanyModel)model;


        if(CommonUtil.isEmpty(busiShopCompanyModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getId())){
            errorMessage.rejectNull("id",null,"企业信息id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopCompanyModel.getCompanyPhoto())){
            errorMessage.rejectNull("companyPhoto",null,"企业门头照");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        busiShopCompanyModel.setCustomerId(tokenUser.getId());

        return errorMessage;
    }
}
