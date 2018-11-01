package com.yinhetianze.back.mall.executor;

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
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.MobileFloorDetailBusiService;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorDetailInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Component
public class UpdateBackstageMobileFloorDetailExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private MobileFloorDetailBusiService mobileFloorDetailBusiServiceImpl;

    @Autowired
    private MobileFloorDetailInfoService mobileFloorDetailInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MobileFloorDetailModel mobileFloorDetailModel=(MobileFloorDetailModel)model;
        MobileFloorDetailPojo mobileFloorDetailPojo=new MobileFloorDetailPojo();
        BeanUtils.copyProperties(mobileFloorDetailModel,mobileFloorDetailPojo);
        MobileFloorDetailPojo dbPojo=new MobileFloorDetailPojo();
        dbPojo.setId(mobileFloorDetailModel.getId());
        dbPojo.setDelFlag((short)0);
        dbPojo=mobileFloorDetailInfoServiceImpl.selectOne(dbPojo);
        try {
            if(mobileFloorDetailModel.getLinkMarkup()!=0){
                int number=mobileFloorDetailModel.getMobileFloorDetailLink().indexOf("?");
                if(number!=-1){
                    mobileFloorDetailPojo.setLinkParameter(mobileFloorDetailModel.getMobileFloorDetailLink().substring(number+1));
                }else{
                    mobileFloorDetailPojo.setLinkParameter("");
                }
            }else{
                mobileFloorDetailPojo.setMobileFloorDetailLink("");
                mobileFloorDetailPojo.setLinkParameter("");
            }
            if(CommonUtil.isNotEmpty(mobileFloorDetailPojo.getPhotoUrl()) && !dbPojo.getPhotoUrl().equals(mobileFloorDetailPojo.getPhotoUrl())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String mallImagePath = sysPropertiesUtils.getStringValue("floorImagePath");
                String ossPath = backOssRootPath+mallImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(UpdateBackstageMobileFloorDetailExecutor.class, "BackstageMobileFloorDetail update read local file "+storeFilePath+ File.separatorChar+mobileFloorDetailModel.getPhotoUrl());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,mobileFloorDetailModel.getPhotoUrl());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                mobileFloorDetailPojo.setPhotoUrl(key);
            }
        }catch (Exception e){
            LoggerUtil.error(UpdateBackstageMobileFloorDetailExecutor.class,e.getMessage());
        }
        int result=mobileFloorDetailBusiServiceImpl.updateByPrimaryKeySelective(mobileFloorDetailPojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MobileFloorDetailModel mobileFloorDetailModel=(MobileFloorDetailModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(mobileFloorDetailModel.getId())){
            errorMessage.rejectNull("id",null,"ID");
        }
        return errorMessage;
    }
}
