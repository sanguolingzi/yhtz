package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.mall.mapper.busi.SysFloorBusiMapper;
import com.yinhetianze.systemservice.mall.model.SysFloorModel;
import com.yinhetianze.systemservice.mall.service.busi.SysFloorBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.SysFloorPojo;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysFloorBusiServiceImpl implements SysFloorBusiService
{
    @Autowired
    private SysFloorBusiMapper mapper;

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    public int insertSelective(SysFloorPojo sysFloorPojo){
        return mapper.insertSelective(sysFloorPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(SysFloorPojo sysFloorPojo){
        return mapper.updateByPrimaryKeySelective(sysFloorPojo);
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException{
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            SysFloorPojo sysFloorPojo = new SysFloorPojo();
            sysFloorPojo.setId(Integer.parseInt(id));
            sysFloorPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(sysFloorPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }

    @Override
    public int updateInfo(SysFloorModel sysFloorModel) throws BusinessException {
        try{
            SysFloorPojo dbPojo=new SysFloorPojo();
            dbPojo.setId(sysFloorModel.getId());
            dbPojo=sysFloorInfoServiceImpl.selectOne(dbPojo);
            SysFloorPojo sysFloorPojo=new SysFloorPojo();
            BeanUtils.copyProperties(sysFloorModel,sysFloorPojo);
            if(sysFloorModel.getLinkMarkup()!=0){
                int number=sysFloorModel.getFloorLink().indexOf("?");
                if(number!=-1){
                    sysFloorPojo.setLinkParameter(sysFloorModel.getFloorLink().substring(number+1));
                }else{
                    sysFloorPojo.setLinkParameter("");
                }
            }else{
                sysFloorPojo.setFloorLink("");
                sysFloorPojo.setLinkParameter("");
            }
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String mallImagePath = sysPropertiesUtils.getStringValue("floorImagePath");
            String ossPath = backOssRootPath+mallImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            if(CommonUtil.isNotEmpty(sysFloorPojo.getPhotoUrl())&&
                    !dbPojo.getPhotoUrl().equals(sysFloorPojo.getPhotoUrl())){
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive add read local file "+storeFilePath+ File.separatorChar+sysFloorModel.getPhotoUrl());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,sysFloorModel.getPhotoUrl());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                sysFloorPojo.setPhotoUrl(key);
            }
            if(CommonUtil.isEmpty(dbPojo.getIcon())){
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive add read local file "+storeFilePath+ File.separatorChar+sysFloorModel.getIcon());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,sysFloorModel.getIcon());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                sysFloorPojo.setIcon(key);
            }else{
                if(CommonUtil.isNotEmpty(sysFloorPojo.getIcon())&&
                        !dbPojo.getIcon().equals(sysFloorPojo.getIcon())){
                    LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive add read local file "+storeFilePath+ File.separatorChar+sysFloorModel.getIcon());
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath,sysFloorModel.getIcon());
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        return 0;
                    sysFloorPojo.setIcon(key);
                }
            }

            return this.updateByPrimaryKeySelective(sysFloorPojo);
        }catch (Exception e){
            LoggerUtil.error(SysFloorBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int addInfo(SysFloorModel sysFloorModel) throws BusinessException {
        try{
            SysFloorPojo sysFloorPojo=new SysFloorPojo();
            BeanUtils.copyProperties(sysFloorModel,sysFloorPojo);
            if(sysFloorModel.getLinkMarkup()!=0){
                int number=sysFloorModel.getFloorLink().indexOf("?");
                if(number!=-1){
                    sysFloorPojo.setLinkParameter(sysFloorModel.getFloorLink().substring(number+1));
                }
            }
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String mallImagePath = sysPropertiesUtils.getStringValue("floorImagePath");
            String ossPath = backOssRootPath+mallImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive add read local file "+storeFilePath+ File.separatorChar+sysFloorModel.getPhotoUrl());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,sysFloorModel.getPhotoUrl());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            sysFloorPojo.setPhotoUrl(key);
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive add read local file "+storeFilePath+ File.separatorChar+sysFloorModel.getIcon());
            File files =FileUtil.loadFile(storeFilePath,sysFloorModel.getIcon());
            String keys=ossFileUpload.fileUpload(files, ossPath);
            if(keys==null)
                return 0;
            sysFloorPojo.setIcon(keys);
            return insertSelective(sysFloorPojo);
        }catch (Exception e){
            LoggerUtil.error(SysFloorBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }
}