package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl;
import com.yinhetianze.systemservice.system.mapper.busi.SysBannerBusiMapper;
import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.service.busi.SysBannerBusiService;
import com.yinhetianze.systemservice.system.service.info.SysBannerInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.SysBannerPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class SysBannerBusiServiceImpl implements SysBannerBusiService
{
    @Autowired
    private SysBannerBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysBannerInfoService sysBannerInfoServiceImpl;

    @Override
    public int insertSelective(SysBannerPojo sysBannerPojo){
        return mapper.insertSelective(sysBannerPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(SysBannerPojo sysBannerPojo){
        sysBannerPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(sysBannerPojo);
    }

    @Override
    public int addInfo(SysBannerModel sysBannerModel) throws BusinessException{
        try {
            SysBannerPojo sysBannerPojo = new SysBannerPojo();
            BeanUtils.copyProperties(sysBannerModel, sysBannerPojo);
            if(sysBannerModel.getLinkMarkup()!=0){
                int number=sysBannerModel.getLinkUrl().indexOf("?");
                if(number!=-1){
                    sysBannerPojo.setLinkParameter(sysBannerModel.getLinkUrl().substring(number+1));
                }
            }
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String bannerImagePath = sysPropertiesUtils.getStringValue("bannerImagePath");
            String ossPath = backOssRootPath+bannerImagePath;
            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "SysBanner add read local file "+storeFilePath+File.separatorChar+sysBannerModel.getPhotoUrl());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,sysBannerModel.getPhotoUrl());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            //删除本地文件
            file.delete();
            sysBannerPojo.setPhotoUrl(key);
            return insertSelective(sysBannerPojo);
        } catch (Exception e) {
            LoggerUtil.error(MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateInfo(SysBannerModel sysBannerModel) throws BusinessException {
        try{
            SysBannerPojo dbPojo = new SysBannerPojo();
            dbPojo.setDelFlag((short)0);
            dbPojo.setId(sysBannerModel.getId());
            dbPojo =  sysBannerInfoServiceImpl.selectOne(dbPojo);
            SysBannerPojo sysBannerPojo = new SysBannerPojo();
            BeanUtils.copyProperties(sysBannerModel,sysBannerPojo);
            //0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情
            if(sysBannerModel.getLinkMarkup()!=0){
                int number=sysBannerModel.getLinkUrl().indexOf("?");
                if(number!=-1){
                    sysBannerPojo.setLinkParameter(sysBannerModel.getLinkUrl().substring(number+1));
                }else{
                    sysBannerPojo.setLinkParameter("");
                }
            }else{
                sysBannerPojo.setLinkUrl("");
                sysBannerPojo.setLinkParameter("");
            }
            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(!dbPojo.getPhotoUrl().equals(sysBannerModel.getPhotoUrl())){
                //删除原图片
                //ossFileUpload.deleteFile(dbPojo.getPhotoUrl());

                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String bannerImagePath = sysPropertiesUtils.getStringValue("bannerImagePath");
                String ossPath = backOssRootPath+bannerImagePath;
                String storeFilePath = fileUploadPath + ossPath;
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "SysBanner update read local file "+storeFilePath+File.separatorChar+sysBannerModel.getPhotoUrl());
                File file = FileUtil.loadFile(storeFilePath,sysBannerModel.getPhotoUrl());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                sysBannerPojo.setPhotoUrl(key);
                return this.updateByPrimaryKeySelective(sysBannerPojo);
            }else{
                return this.updateByPrimaryKeySelective(sysBannerPojo);
            }

        }catch(Exception e){
            LoggerUtil.error(MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int deleteInfo(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            SysBannerPojo sysBannerPojo = new SysBannerPojo();
            sysBannerPojo.setId(Integer.parseInt(id));
            sysBannerPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(sysBannerPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}