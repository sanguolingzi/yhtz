package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.mapper.busi.MobileFloorBusiMapper;
import com.yinhetianze.systemservice.mall.model.MobileFloorModel;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.systemservice.mall.service.busi.MobileFloorBusiService;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MobileFloorBusiServiceImpl implements MobileFloorBusiService
{
    @Autowired
    private MobileFloorBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private MobileFloorInfoService mobileFloorInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    public int insertSelective(MobileFloorPojo mobileFloorPojo) {
        return mapper.insertSelective(mobileFloorPojo);
    }

    @Override
    public int addInfo(MobileFloorModel mobileFloorModel) throws BusinessException {
        try {
            MobileFloorPojo mobileFloorPojo=new MobileFloorPojo();
            BeanUtils.copyProperties(mobileFloorModel,mobileFloorPojo);
            if(mobileFloorModel.getLinkMarkup()!=0){
                int number=mobileFloorModel.getMobileFloorLink().indexOf("?");
                if(number!=-1){
                    mobileFloorPojo.setLinkParameter(mobileFloorModel.getMobileFloorLink().substring(number+1));
                }
            }
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String mallImagePath = sysPropertiesUtils.getStringValue("floorImagePath");
            String ossPath = backOssRootPath+mallImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(MobileFloorBusiServiceImpl.class, "mobileFloor add read local file "+storeFilePath+ File.separatorChar+mobileFloorPojo.getPhotoUrl());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,mobileFloorPojo.getPhotoUrl());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            mobileFloorPojo.setPhotoUrl(key);
            return insertSelective(mobileFloorPojo);
        }catch (Exception e){
            LoggerUtil.error(MobileFloorBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateByPrimaryKeySelective(MobileFloorPojo mobileFloorPojo) {
        return mapper.updateByPrimaryKeySelective(mobileFloorPojo);
    }

    @Override
    public int updateInfo(MobileFloorModel mobileFloorModel) throws BusinessException {
        try {
            MobileFloorPojo mobileFloorPojo=new MobileFloorPojo();
            MobileFloorPojo dbPojo=new MobileFloorPojo();
            dbPojo.setId(mobileFloorModel.getId());
            dbPojo.setDelFlag((short)0);
            dbPojo=mobileFloorInfoServiceImpl.selectOne(dbPojo);
            BeanUtils.copyProperties(mobileFloorModel,mobileFloorPojo);
            if(mobileFloorPojo.getLinkMarkup()==0) {
                mobileFloorPojo.setMobileFloorLink("");
            }
            if(mobileFloorModel.getLinkMarkup()!=0){
                int number=mobileFloorModel.getMobileFloorLink().indexOf("?");
                if(number!=-1){
                    mobileFloorPojo.setLinkParameter(mobileFloorModel.getMobileFloorLink().substring(number+1));
                }else {
                    mobileFloorPojo.setLinkParameter("");
                }
            }else{
                mobileFloorPojo.setMobileFloorLink("");
                mobileFloorPojo.setLinkParameter("");
            }
            if(CommonUtil.isNotEmpty(mobileFloorPojo.getPhotoUrl())&&
                    !dbPojo.getPhotoUrl().equals(mobileFloorPojo.getPhotoUrl())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String mallImagePath = sysPropertiesUtils.getStringValue("floorImagePath");

                String ossPath = backOssRootPath+mallImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(MobileFloorBusiServiceImpl.class, "MobileFloor add read local file "+storeFilePath+ File.separatorChar+mobileFloorPojo.getPhotoUrl());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,mobileFloorPojo.getPhotoUrl());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                mobileFloorPojo.setPhotoUrl(key);
                return updateByPrimaryKeySelective(mobileFloorPojo);
            }else{
                return updateByPrimaryKeySelective(mobileFloorPojo);
            }

        }catch (Exception e){
            LoggerUtil.error(MobileFloorBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }
}