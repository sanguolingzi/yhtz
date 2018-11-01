package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiUpdatePhoneModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
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
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 消费者/会员 修改头像
 */

@Component
public class UpdateCustomerPhotoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(busiCustomerModel.getId());
        //busiCustomerPojo.setPhone(busiUpdatePhoneModel.getPhone());

        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(busiCustomerPojo)){
            throw new BusinessException("BC0054",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        String key = null;
        try{
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String customerOssRootPath = sysPropertiesUtils.getStringValue("customerOssRootPath");
            String userHeadImagePath = sysPropertiesUtils.getStringValue("userHeadImagePath");

            String ossPath = customerOssRootPath+userHeadImagePath;

            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(UpdateCustomerPhotoExecutor.class, "UpdateCustomerPhoto add read local file "+storeFilePath+File.separatorChar+busiCustomerModel.getPhotoUrl());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,busiCustomerModel.getPhotoUrl());
            //上传oss
            key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                throw new BusinessException("OSS文件上传失败!");

        }catch(Exception e){
            LoggerUtil.error(UpdateCustomerPhotoExecutor.class, e.toString());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        BusiCustomerPojo updateCustomerPojo = new BusiCustomerPojo();
        updateCustomerPojo.setId(busiCustomerPojo.getId());
        updateCustomerPojo.setPhotoUrl(key);

        int result = customerBusiServiceImpl.updateByPrimaryKeySelective(updateCustomerPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);

        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        /**
         * 校验 token
         */
        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerModel.getToken());

        if(CommonUtil.isEmpty(busiCustomerModel.getId())){
            errorMessage.rejectNull("id",null,"用户id");
            return errorMessage;
        }


        if(tokenUser.getId().intValue() != busiCustomerModel.getId().intValue()){
            LoggerUtil.error(UpdateCustomerPhotoExecutor.class,"tokenId:"+tokenUser.getId()+".....param CustomerId:"+busiCustomerModel.getId());
            errorMessage.rejectNull("info",null,"提交参数异常");
            return errorMessage;
        }


        if(CommonUtil.isEmpty(busiCustomerModel.getPhotoUrl())){
            errorMessage.rejectNull("photoUrl",null,"photoUrl");
            return errorMessage;
        }

        return errorMessage;
    }
}
