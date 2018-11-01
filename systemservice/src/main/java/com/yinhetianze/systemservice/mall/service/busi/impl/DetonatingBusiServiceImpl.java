package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.DetonatingPojo;
import com.yinhetianze.systemservice.mall.mapper.busi.DetonatingBusiMapper;
import com.yinhetianze.systemservice.mall.model.DetonatingModel;
import com.yinhetianze.systemservice.mall.service.busi.DetonatingBusiService;
import com.yinhetianze.systemservice.mall.service.info.DetonatingInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DetonatingBusiServiceImpl implements DetonatingBusiService
{
    @Autowired
    private DetonatingBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private DetonatingInfoService detonatingInfoServiceImpl;

    @Override
    public int addDetonating(DetonatingPojo detonatingPojo) {
        return mapper.insertSelective(detonatingPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(DetonatingPojo detonatingPojo) {
        return mapper.updateByPrimaryKeySelective(detonatingPojo);
    }

    @Override
    public int addInfo(DetonatingModel detonatingModel) throws BusinessException {
        try {
            DetonatingPojo detonatingPojo=new DetonatingPojo();
            BeanUtils.copyProperties(detonatingModel, detonatingPojo);

            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String floorImagePath = sysPropertiesUtils.getStringValue("floorImagePath");

            String ossPath = backOssRootPath+floorImagePath;

            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(DetonatingBusiServiceImpl.class, "Detonating add read local file "+storeFilePath+ File.separatorChar+detonatingModel.getPhotoUrl());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,detonatingModel.getPhotoUrl());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            //删除本地文件
            //file.delete();
            detonatingPojo.setPhotoUrl(key);
            return mapper.insertSelective(detonatingPojo);
        } catch (Exception e) {
            LoggerUtil.error(DetonatingBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateInfo(DetonatingModel detonatingModel) throws BusinessException {
        try{
            DetonatingPojo dbPojo = new DetonatingPojo();
            dbPojo.setDelFlag((short)0);
            dbPojo.setId(detonatingModel.getId());
            dbPojo =  detonatingInfoServiceImpl.selectOne(dbPojo);

            DetonatingPojo detonatingPojo = new DetonatingPojo();
            BeanUtils.copyProperties(detonatingModel,detonatingPojo);

            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(!dbPojo.getPhotoUrl().equals(detonatingModel.getPhotoUrl())){
                //删除原图片
                //ossFileUpload.deleteFile(dbPojo.getPhotoUrl());

                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String floorImagePath = sysPropertiesUtils.getStringValue("floorImagePath");

                String ossPath = backOssRootPath+floorImagePath;
                String storeFilePath = fileUploadPath + ossPath;
                LoggerUtil.info(DetonatingBusiServiceImpl.class, "Detonating update read local file "+storeFilePath+File.separatorChar+detonatingModel.getPhotoUrl());
                File file = FileUtil.loadFile(storeFilePath,detonatingModel.getPhotoUrl());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                detonatingPojo.setPhotoUrl(key);
                return this.updateByPrimaryKeySelective(detonatingPojo);
            }else{
                return this.updateByPrimaryKeySelective(detonatingPojo);
            }

        }catch(Exception e){
            LoggerUtil.error(DetonatingBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }
}