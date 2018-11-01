package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.mall.mapper.busi.MallActivityBusiMapper;
import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.systemservice.mall.service.busi.MallActivityBusiService;
import com.yinhetianze.systemservice.mall.service.info.MallActivityInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.MallActivityPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class MallActivityBusiServiceImpl implements MallActivityBusiService {
    @Autowired
    private MallActivityBusiMapper mapper;

    @Autowired
    private MallActivityInfoService mallActivityInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public int insertSelective(MallActivityPojo mallActivityPojo) {
        return mapper.insertSelective(mallActivityPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(MallActivityPojo mallActivityPojo) {
        mallActivityPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(mallActivityPojo);
    }


    @Override
    public int addInfo(MallActivityModel mallActivityModel) throws BusinessException {
        try {
            MallActivityPojo mallActivityPojo = new MallActivityPojo();
            BeanUtils.copyProperties(mallActivityModel, mallActivityPojo);
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String mallImagePath = sysPropertiesUtils.getStringValue("mallImagePath");
            String ossPath = backOssRootPath+mallImagePath;

            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive add read local file "+storeFilePath+File.separatorChar+mallActivityModel.getActivityimage());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,mallActivityModel.getActivityimage());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            //删除本地文件
            //file.delete();
            mallActivityPojo.setActivityimage(key);
            return insertSelective(mallActivityPojo);
        } catch (Exception e) {
            LoggerUtil.error(MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }


    @Override
    public int updateInfo(MallActivityModel mallActivityModel) throws BusinessException {
        try{
            MallActivityPojo dbPojo = new MallActivityPojo();
            dbPojo.setId(mallActivityModel.getId());
            dbPojo =  mallActivityInfoServiceImpl.selectOne(dbPojo);

            MallActivityPojo mallActivityPojo = new MallActivityPojo();
            BeanUtils.copyProperties(mallActivityModel,mallActivityPojo);

            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(CommonUtil.isNotEmpty(mallActivityPojo.getActivityimage())
                && !dbPojo.getActivityimage().equals(mallActivityPojo.getActivityimage())){
                //删除原图片
                //ossFileUpload.deleteFile(dbPojo.getActivityimage());

                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String mallImagePath = sysPropertiesUtils.getStringValue("mallImagePath");

                String ossPath = backOssRootPath+mallImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "mallactive update read local file "+storeFilePath+File.separatorChar+mallActivityModel.getActivityimage());
                File file = FileUtil.loadFile(storeFilePath,mallActivityModel.getActivityimage());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                mallActivityPojo.setActivityimage(key);
                return this.updateByPrimaryKeySelective(mallActivityPojo);
            }else{
                return this.updateByPrimaryKeySelective(mallActivityPojo);
            }

        }catch(Exception e){
            LoggerUtil.error(MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if (idsArr == null || idsArr.length == 0) {
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for (String id : idsArr) {
            MallActivityPojo mallActivityPojo = new MallActivityPojo();
            mallActivityPojo.setId(Integer.parseInt(id));
            mallActivityPojo.setDelFlag((short) 1);
            int result = updateByPrimaryKeySelective(mallActivityPojo);
            if (result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}