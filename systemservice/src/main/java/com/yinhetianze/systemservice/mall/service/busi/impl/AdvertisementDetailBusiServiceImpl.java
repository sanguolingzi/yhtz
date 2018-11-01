package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.AdvertisementDetailPojo;
import com.yinhetianze.systemservice.mall.mapper.busi.AdvertisementDetailBusiMapper;
import com.yinhetianze.systemservice.mall.model.AdvertisementDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.AdvertisementDetailBusiService;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementDetailInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class AdvertisementDetailBusiServiceImpl implements AdvertisementDetailBusiService
{
    @Autowired
    private AdvertisementDetailBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private AdvertisementDetailInfoService advertisementDetailInfoServiceImpl;

    @Override
    public int addAdvertisementDetail(AdvertisementDetailPojo advertisementDetailPojo) {
        return mapper.insertSelective(advertisementDetailPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(AdvertisementDetailPojo advertisementDetailPojo) {
        return mapper.updateByPrimaryKeySelective(advertisementDetailPojo);
    }

    @Override
    public int addInfo(AdvertisementDetailModel advertisementDetailModel) throws BusinessException {
        try{
            AdvertisementDetailPojo advertisementDetailPojo=new AdvertisementDetailPojo();
            BeanUtils.copyProperties(advertisementDetailModel,advertisementDetailPojo);
            if(advertisementDetailModel.getLinkMarkup()!=0){
                int number=advertisementDetailModel.getAdvertisementDetailLink().indexOf("?");
                if(number!=-1){
                    advertisementDetailPojo.setLinkParameter(advertisementDetailModel.getAdvertisementDetailLink().substring(number+1));
                }
            }
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String mallImagePath = sysPropertiesUtils.getStringValue("advertisementImgPath");
            String ossPath = backOssRootPath+mallImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(AdvertisementDetailBusiServiceImpl.class, "AdvertisementDetail add read local file "+storeFilePath+ File.separatorChar+advertisementDetailModel.getAdvertisementDetailImg());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,advertisementDetailModel.getAdvertisementDetailImg());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            advertisementDetailPojo.setAdvertisementDetailImg(key);
            return addAdvertisementDetail(advertisementDetailPojo);
        }catch (Exception e){
            LoggerUtil.error(SysFloorBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateInfo(AdvertisementDetailModel advertisementDetailModel) throws BusinessException {
        try {
            AdvertisementDetailPojo advertisementDetailPojo=new AdvertisementDetailPojo();
            AdvertisementDetailPojo dbPojo=new AdvertisementDetailPojo();
            dbPojo.setId(advertisementDetailModel.getId());
            dbPojo.setDelFlag((short)0);
            dbPojo=advertisementDetailInfoServiceImpl.selectOne(dbPojo);
            BeanUtils.copyProperties(advertisementDetailModel,advertisementDetailPojo);
            if(advertisementDetailModel.getLinkMarkup()!=0){
                int number=advertisementDetailModel.getAdvertisementDetailLink().indexOf("?");
                if(number!=-1){
                    advertisementDetailPojo.setLinkParameter(advertisementDetailModel.getAdvertisementDetailLink().substring(number+1));
                }else {
                    advertisementDetailPojo.setLinkParameter("");
                }
            }else{
                advertisementDetailPojo.setAdvertisementDetailLink("");
                advertisementDetailPojo.setLinkParameter("");
            }
            if(CommonUtil.isNotEmpty(advertisementDetailPojo.getAdvertisementDetailImg())&&
                    !dbPojo.getAdvertisementDetailImg().equals(advertisementDetailPojo.getAdvertisementDetailImg())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String mallImagePath = sysPropertiesUtils.getStringValue("advertisementImgPath");

                String ossPath = backOssRootPath+mallImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(AdvertisementDetailBusiServiceImpl.class, "AdvertisementDetail update read local file "+storeFilePath+ File.separatorChar+advertisementDetailModel.getAdvertisementDetailImg());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,advertisementDetailModel.getAdvertisementDetailImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                advertisementDetailPojo.setAdvertisementDetailImg(key);
                return updateByPrimaryKeySelective(advertisementDetailPojo);
            }else{
                return updateByPrimaryKeySelective(advertisementDetailPojo);
            }

        }catch (Exception e){
            LoggerUtil.error(AdvertisementDetailBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }
}