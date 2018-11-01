package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.BusiSysMemberInfoPojo;
import com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl;
import com.yinhetianze.systemservice.system.mapper.busi.SysMemberBusiMapper;
import com.yinhetianze.systemservice.system.model.SysMemberInfoModel;
import com.yinhetianze.systemservice.system.service.busi.SysMemberBusiService;
import com.yinhetianze.systemservice.system.service.info.impl.SysMemberInfoServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysMemberBusiServiceImpl implements SysMemberBusiService
{
    @Autowired
    private SysMemberBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysMemberInfoServiceImpl sysMemberInfoServiceImpl;

    @Override
    public int addParentSysMember(SysMemberInfoModel sysMemberInfoModel) throws BusinessException{
        /*
        File imgFile = null;
        File bannerFile = null;
        try {
            BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
            BeanUtils.copyProperties(sysMemberInfoModel, busiSysMemberInfoPojo);

            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String memberImg = sysPropertiesUtils.getStringValue("memberImg");

            String ossPath = backOssRootPath+memberImg;
            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "SysMemberInfo add read memberImg local file "+storeFilePath+File.separatorChar+sysMemberInfoModel.getMemberImg());
            //读取本地文件
            imgFile = FileUtil.loadFile(storeFilePath,sysMemberInfoModel.getMemberImg());
            //上传oss
            String key = ossFileUpload.fileUpload(imgFile, ossPath);
            if(key == null)
                return 0;
            busiSysMemberInfoPojo.setMemberImg(key);


            String memberBanner = sysPropertiesUtils.getStringValue("memberBanner");
            ossPath = backOssRootPath+memberBanner;
            storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "SysMemberInfo add memberBanner read local file "+storeFilePath+File.separatorChar+sysMemberInfoModel.getMemberBanner());
            //读取本地文件
            bannerFile = FileUtil.loadFile(storeFilePath,sysMemberInfoModel.getMemberBanner());
            //上传oss
            key = ossFileUpload.fileUpload(bannerFile, ossPath);
            if(key == null)
                return 0;
            busiSysMemberInfoPojo.setMemberBanner(key);


            busiSysMemberInfoPojo.setParentId(0);
            int result = mapper.insertSelective(busiSysMemberInfoPojo);
            if(result > 0){
                imgFile.delete();
                bannerFile.delete();
                return 1;
            }
            return 0;
        } catch (Exception e) {
            LoggerUtil.error(SysMemberBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        */
        BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
        BeanUtils.copyProperties(sysMemberInfoModel, busiSysMemberInfoPojo);
        busiSysMemberInfoPojo.setParentId(0);
        int result = mapper.insertSelective(busiSysMemberInfoPojo);
        return result;
    }

    @Override
    public int addChildSysMember(SysMemberInfoModel sysMemberInfoModel) throws BusinessException {
        File imgFile = null;
        try {
            BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
            BeanUtils.copyProperties(sysMemberInfoModel, busiSysMemberInfoPojo);

            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String memberImg = sysPropertiesUtils.getStringValue("memberImg");

            String ossPath = backOssRootPath+memberImg;
            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(MallActivityBusiServiceImpl.class, "SysMemberInfo add read memberImg local file "+storeFilePath+File.separatorChar+sysMemberInfoModel.getMemberImg());
            //读取本地文件
            imgFile = FileUtil.loadFile(storeFilePath,sysMemberInfoModel.getMemberImg());
            //上传oss
            String key = ossFileUpload.fileUpload(imgFile, ossPath);
            if(key == null)
                return 0;
            busiSysMemberInfoPojo.setMemberImg(key);

            int result = mapper.insertSelective(busiSysMemberInfoPojo);
            if(result > 0){
                imgFile.delete();
                return 1;
            }
            return 0;
        } catch (Exception e) {
            LoggerUtil.error(SysMemberBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateParentSysMember(SysMemberInfoModel sysMemberInfoModel)  throws BusinessException {
        /*
        File imgFile = null;
        File bannerFile = null;
        try{
            BusiSysMemberInfoPojo dbPojo = new BusiSysMemberInfoPojo();
            dbPojo.setId(sysMemberInfoModel.getId());
            dbPojo =  sysMemberInfoServiceImpl.selectOne(dbPojo);

            BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
            BeanUtils.copyProperties(sysMemberInfoModel,busiSysMemberInfoPojo);

            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");

            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(CommonUtil.isNotEmpty(busiSysMemberInfoPojo.getMemberImg())
                    && !dbPojo.getMemberImg().equals(busiSysMemberInfoPojo.getMemberImg())){


                String memberImg = sysPropertiesUtils.getStringValue("memberImg");

                String ossPath = backOssRootPath+memberImg;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "updateParentSysMember update memberImg read local file "+storeFilePath+File.separatorChar+sysMemberInfoModel.getMemberImg());
                imgFile = FileUtil.loadFile(storeFilePath,sysMemberInfoModel.getMemberImg());
                //上传oss
                String key = ossFileUpload.fileUpload(imgFile, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                busiSysMemberInfoPojo.setMemberImg(key);
            }

            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(CommonUtil.isNotEmpty(busiSysMemberInfoPojo.getMemberBanner())
                    && !dbPojo.getMemberBanner().equals(busiSysMemberInfoPojo.getMemberBanner())){

                String memberBanner = sysPropertiesUtils.getStringValue("memberBanner");

                String ossPath = backOssRootPath+memberBanner;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "updateParentSysMember update memberBanner read local file "+storeFilePath+File.separatorChar+sysMemberInfoModel.getMemberBanner());
                bannerFile = FileUtil.loadFile(storeFilePath,sysMemberInfoModel.getMemberBanner());
                //上传oss
                String key = ossFileUpload.fileUpload(bannerFile, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                busiSysMemberInfoPojo.setMemberBanner(key);
            }
            busiSysMemberInfoPojo.setUpdateTime(new Date());
            int result  = mapper.updateByPrimaryKeySelective(busiSysMemberInfoPojo);
            if(result > 0){
                if(imgFile != null){
                    imgFile.delete();
                }

                if(bannerFile != null){
                    bannerFile.delete();
                }
                return 1;
            }
            return 0;
        }catch(Exception e){
            LoggerUtil.error(SysMemberBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        */
        BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
        BeanUtils.copyProperties(sysMemberInfoModel,busiSysMemberInfoPojo);
        busiSysMemberInfoPojo.setUpdateTime(new Date());
        int result  = mapper.updateByPrimaryKeySelective(busiSysMemberInfoPojo);
        return result;
    }

    @Override
    public int updateChildSysMember(SysMemberInfoModel sysMemberInfoModel)  throws BusinessException  {
        File imgFile = null;
        try{
            BusiSysMemberInfoPojo dbPojo = new BusiSysMemberInfoPojo();
            dbPojo.setId(sysMemberInfoModel.getId());
            dbPojo =  sysMemberInfoServiceImpl.selectOne(dbPojo);

            BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
            BeanUtils.copyProperties(sysMemberInfoModel,busiSysMemberInfoPojo);

            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");

            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(CommonUtil.isNotEmpty(busiSysMemberInfoPojo.getMemberImg())
                    && !dbPojo.getMemberImg().equals(busiSysMemberInfoPojo.getMemberImg())){
                String memberImg = sysPropertiesUtils.getStringValue("memberImg");
                String ossPath = backOssRootPath+memberImg;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(MallActivityBusiServiceImpl.class, "updateParentSysMember update memberImg read local file "+storeFilePath+File.separatorChar+sysMemberInfoModel.getMemberImg());
                imgFile = FileUtil.loadFile(storeFilePath,sysMemberInfoModel.getMemberImg());
                //上传oss
                String key = ossFileUpload.fileUpload(imgFile, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                busiSysMemberInfoPojo.setMemberImg(key);
            }

            busiSysMemberInfoPojo.setUpdateTime(new Date());
            int result  = mapper.updateByPrimaryKeySelective(busiSysMemberInfoPojo);
            if(result > 0){
                if(imgFile != null){
                    imgFile.delete();
                }
                return 1;
            }
            return 0;
        }catch(Exception e){
            LoggerUtil.error(SysMemberBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int deleteParentSysMember(String ids) {
        BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
        busiSysMemberInfoPojo.setId(Integer.parseInt(ids));
        busiSysMemberInfoPojo.setDelFlag((short) 1);
        int result =  mapper.updateByPrimaryKeySelective(busiSysMemberInfoPojo);
        if(result <= 0){
            return 0;
        }

        SysMemberInfoModel sysMemberInfoModel = new SysMemberInfoModel();
        sysMemberInfoModel.setParentId(busiSysMemberInfoPojo.getId());
        List<SysMemberInfoModel> list =  sysMemberInfoServiceImpl.selectChildSystemMember(sysMemberInfoModel);
        if(CommonUtil.isNotEmpty(list)){
            for(SysMemberInfoModel model : list){
                busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
                busiSysMemberInfoPojo.setId(model.getId());
                busiSysMemberInfoPojo.setDelFlag((short) 1);
                mapper.updateByPrimaryKeySelective(busiSysMemberInfoPojo);
            }
        }
        return 1;
    }

    @Override
    public int deleteChildSysMember(String ids)  throws BusinessException {

        String[] idsArr = ids.split("\\,");
        if (idsArr == null || idsArr.length == 0) {
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for (String id : idsArr) {
            BusiSysMemberInfoPojo busiSysMemberInfoPojo = new BusiSysMemberInfoPojo();
            busiSysMemberInfoPojo.setId(Integer.parseInt(id));
            busiSysMemberInfoPojo.setDelFlag((short) 1);
            int result =  mapper.updateByPrimaryKeySelective(busiSysMemberInfoPojo);
            if (result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}