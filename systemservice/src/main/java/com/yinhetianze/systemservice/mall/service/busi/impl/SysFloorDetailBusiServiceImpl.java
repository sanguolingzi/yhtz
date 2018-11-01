package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.systemservice.mall.mapper.busi.SysFloorDetailBusiMapper;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.SysFloorDetailBusiService;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.SysFloorDetailPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysFloorDetailBusiServiceImpl implements SysFloorDetailBusiService
{
    @Autowired
    private SysFloorDetailBusiMapper mapper;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysFloorDetailInfoService sysFloorDetailInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public int updateByPrimaryKeySelective(SysFloorDetailModel sysFloorDetailModel) {
        SysFloorDetailPojo sysFloorDetailPojo = new SysFloorDetailPojo();
        BeanUtils.copyProperties(sysFloorDetailModel,sysFloorDetailPojo);
        int result =updateByPrimaryKeySelective(sysFloorDetailPojo);
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(SysFloorDetailPojo sysFloorDetailPojo) {
        sysFloorDetailPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(sysFloorDetailPojo);
    }


    @Override
    public int addInfo(SysFloorDetailModel sysFloorDetailModel) throws BusinessException {
        try {
            SysFloorDetailPojo sysFloorDetailPojo = new SysFloorDetailPojo();
            BeanUtils.copyProperties(sysFloorDetailModel, sysFloorDetailPojo);

            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String floorImagePath = sysPropertiesUtils.getStringValue("floorImagePath");

            String ossPath = backOssRootPath+floorImagePath;

            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "sysFloorDetail add read local file "+storeFilePath+File.separatorChar+sysFloorDetailModel.getPhotoUrl());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,sysFloorDetailModel.getPhotoUrl());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            //删除本地文件
            //file.delete();
            sysFloorDetailPojo.setPhotoUrl(key);
            return mapper.insertSelective(sysFloorDetailPojo);
        } catch (Exception e) {
            LoggerUtil.error(MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateInfo(SysFloorDetailModel sysFloorDetailModel) throws BusinessException {
        try{
            SysFloorDetailPojo dbPojo = new SysFloorDetailPojo();
            dbPojo.setDelFlag((short)0);
            dbPojo.setId(sysFloorDetailModel.getId());
            dbPojo =  sysFloorDetailInfoServiceImpl.selectOne(dbPojo);

            SysFloorDetailPojo sysFloorDetailPojo = new SysFloorDetailPojo();
            BeanUtils.copyProperties(sysFloorDetailModel,sysFloorDetailPojo);

            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(!dbPojo.getPhotoUrl().equals(sysFloorDetailModel.getPhotoUrl())){
                //删除原图片
                //ossFileUpload.deleteFile(dbPojo.getPhotoUrl());

                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String floorImagePath = sysPropertiesUtils.getStringValue("floorImagePath");

                String ossPath = backOssRootPath+floorImagePath;
                String storeFilePath = fileUploadPath + ossPath;
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "sysFloorDetail update read local file "+storeFilePath+File.separatorChar+sysFloorDetailModel.getPhotoUrl());
                File file = FileUtil.loadFile(storeFilePath,sysFloorDetailModel.getPhotoUrl());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                sysFloorDetailPojo.setPhotoUrl(key);
                return this.updateByPrimaryKeySelective(sysFloorDetailPojo);
            }else{
                return this.updateByPrimaryKeySelective(sysFloorDetailPojo);
            }

        }catch(Exception e){
            LoggerUtil.error(MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            SysFloorDetailPojo sysFloorDetailPojo = new SysFloorDetailPojo();
            sysFloorDetailPojo.setId(Integer.parseInt(id));
            sysFloorDetailPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(sysFloorDetailPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }



}