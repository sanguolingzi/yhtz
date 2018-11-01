package com.yinhetianze.business.shop.service.busi.impl;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.shop.mapper.busi.ShopCompanyBusiMapper;
import com.yinhetianze.business.shop.model.BusiShopCompanyModel;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.business.shopaudit.service.busi.ShopAuditBusiService;
import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopCompanyBusiServiceImpl implements ShopCompanyBusiService {
    @Autowired
    private ShopCompanyBusiMapper shopCompanyBusiMapper;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private ShopAuditBusiService shopAuditBusiServiceImpl;

    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public int updateByPrimaryKeySelective(BusiShopCompanyPojo record) {
        return shopCompanyBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiShopCompanyPojo record) {
        return shopCompanyBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int insert(BusiShopCompanyPojo record) {
        return shopCompanyBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiShopCompanyPojo record) {
        return shopCompanyBusiMapper.insertSelective(record);
    }


    @Override
    public int addInfo(BusiShopCompanyModel busiShopCompanyModel) throws BusinessException {

        BusiShopCompanyPojo busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectByCustomerId(busiShopCompanyModel.getCustomerId());

        if(busiShopCompanyPojo != null){
            throw new BusinessException("企业信息已存在!");
        }

        busiShopCompanyPojo  = new BusiShopCompanyPojo();
        BeanUtils.copyProperties(busiShopCompanyModel,busiShopCompanyPojo);
        //上传图片
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            //String fileUploadPath = "D:\\testFile";
            String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
            String companylicImagePath = sysPropertiesUtils.getStringValue("companylicImagePath");
            String ossPath = shopOssRootPath+companylicImagePath;

            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany add read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getBusinessLicense());
            //读取本地文件
            //营业执照
            File file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getBusinessLicense());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            busiShopCompanyPojo.setBusinessLicense(key);

            String idCardImagePath = sysPropertiesUtils.getStringValue("idCardImagePath");
            ossPath = shopOssRootPath+idCardImagePath;
            storeFilePath = fileUploadPath +ossPath;
            //身份证反面
            LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany add read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getIdCardN());

            file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getIdCardN());
            key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            busiShopCompanyPojo.setIdCardN(key);

            //身份证正面
            LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany add read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getIdCardP());
            file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getIdCardP());
            key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            busiShopCompanyPojo.setIdCardP(key);

            //门头照
            String companyPhotoImagePath = sysPropertiesUtils.getStringValue("companyPhotoImagePath");
            ossPath = shopOssRootPath+companyPhotoImagePath;
            storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany add read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getCompanyPhoto());
            file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getCompanyPhoto());
            key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            busiShopCompanyPojo.setCompanyPhoto(key);
        } catch (Exception e) {
            LoggerUtil.error(ShopCompanyBusiServiceImpl.class, e.getMessage());
            return 0;
        }


        BusiShopCompanyPojo dbEntity = new BusiShopCompanyPojo();

        //新增企业直接插入记录
        BeanUtils.copyProperties(busiShopCompanyPojo,dbEntity);

        //dbEntity.setCustomerId(busiShopCompanyPojo.getCustomerId());
        dbEntity.setAuditStatus((short)2);

        //TODO 用户添加完入驻企业信息 默认为删除状态  待完善店铺信息后 才更新企业信息状态 防止入驻信息添加到一半 刷新 导致信息不完善
        dbEntity.setDelFlag((short)1);
        insertSelective(dbEntity);


        //start 添加待审核记录
        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setCustomerId(dbEntity.getCustomerId());
        busiSysShopAuditPojo.setAuditStatus((short)2);
        busiSysShopAuditPojo.setAuditType((short)2);
        busiSysShopAuditPojo.setRelationId(dbEntity.getId());
        busiSysShopAuditPojo.setDoUpdate((short)0);
        busiSysShopAuditPojo.setAuditStep((short)1);

        JSONObject content = new JSONObject();
        setContent(content,busiShopCompanyPojo);

        busiSysShopAuditPojo.setAuditContent(content.toJSONString());
        JSONObject contentResult = new JSONObject();
        setContentResult(contentResult);
        busiSysShopAuditPojo.setAuditResultJson(contentResult.toJSONString());
        shopAuditBusiServiceImpl.addInfo(busiSysShopAuditPojo);
        //end
        return dbEntity.getId();
    }

    @Override
    public int updateInfo(BusiShopCompanyModel busiShopCompanyModel)  throws BusinessException {
        BusiShopCompanyPojo busiShopCompanyPojo  = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setId(busiShopCompanyModel.getId());
        busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectOne(busiShopCompanyPojo);

        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setCustomerId(busiShopCompanyModel.getCustomerId());
        busiSysShopAuditPojo.setRelationId(busiShopCompanyPojo.getId());
        busiSysShopAuditPojo.setAuditType((short)2);
        busiSysShopAuditPojo.setAuditStatus((short)2);
        busiSysShopAuditPojo = shopAuditInfoServiceImpl.selectOne(busiSysShopAuditPojo);

        try{
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            //String fileUploadPath = "D:\\testFile";
            String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
            //比较营业执照
            if(CommonUtil.isNotEmpty(busiShopCompanyModel.getBusinessLicense())
                    && !busiShopCompanyPojo.getBusinessLicense().equals(busiShopCompanyModel.getBusinessLicense())){

                String companylicImagePath = sysPropertiesUtils.getStringValue("companylicImagePath");
                String ossPath = shopOssRootPath+companylicImagePath;

                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany update read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getBusinessLicense());
                //读取本地文件
                //营业执照
                File file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getBusinessLicense());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                busiShopCompanyModel.setBusinessLicense(key);
            }

            //比较身份证反面
            if(CommonUtil.isNotEmpty(busiShopCompanyModel.getIdCardP())
                    && !busiShopCompanyPojo.getIdCardP().equals(busiShopCompanyModel.getIdCardP())){
                String idCardImagePath = sysPropertiesUtils.getStringValue("idCardImagePath");
                String ossPath = shopOssRootPath+idCardImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany update read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getIdCardP());
                //身份证
                File file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getIdCardN());
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                busiShopCompanyModel.setIdCardN(key);
            }

            //比较身份证正面
            if(CommonUtil.isNotEmpty(busiShopCompanyModel.getIdCardN())
                    && !busiShopCompanyPojo.getIdCardN().equals(busiShopCompanyModel.getIdCardN())){
                String idCardImagePath = sysPropertiesUtils.getStringValue("idCardImagePath");
                String ossPath = shopOssRootPath+idCardImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany update read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getIdCardN());
                File file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getIdCardP());
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                busiShopCompanyModel.setIdCardP(key);

            }

            //比较企业门头照
            if(CommonUtil.isNotEmpty(busiShopCompanyModel.getCompanyPhoto())
                    && !busiShopCompanyPojo.getCompanyPhoto().equals(busiShopCompanyModel.getCompanyPhoto())){

                String companyPhotoImagePath = sysPropertiesUtils.getStringValue("companyPhotoImagePath");
                String ossPath = shopOssRootPath+companyPhotoImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(ShopCompanyBusiServiceImpl.class, "shopcompany update read local file "+storeFilePath+File.separatorChar+busiShopCompanyModel.getCompanyPhoto());
                File file = FileUtil.loadFile(storeFilePath,busiShopCompanyModel.getCompanyPhoto());
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                busiShopCompanyModel.setCompanyPhoto(key);
            }

            BeanUtils.copyProperties(busiShopCompanyModel,busiShopCompanyPojo);


            JSONObject content = new JSONObject();//CommonUtil.readFromString(busiSysShopAuditPojo.getAuditResultJson(),JSONObject.class);
            JSONObject contentResult = new JSONObject();//CommonUtil.readFromString(busiSysShopAuditPojo.getAuditContent(),JSONObject.class);
            setContent(content,busiShopCompanyPojo);

            setContentResult(contentResult);

            if(busiSysShopAuditPojo != null){
                BusiSysShopAuditPojo temp = new BusiSysShopAuditPojo();
                temp.setId(busiSysShopAuditPojo.getId());
                temp.setAuditContent(content.toJSONString());
                temp.setAuditResultJson(contentResult.toJSONString());
                shopAuditBusiServiceImpl.updateInfo(temp);
            }else{
                busiSysShopAuditPojo = new BusiSysShopAuditPojo();
                busiSysShopAuditPojo.setCustomerId(busiShopCompanyPojo.getCustomerId());
                busiSysShopAuditPojo.setAuditStatus((short)2);
                busiSysShopAuditPojo.setAuditType((short)2);
                busiSysShopAuditPojo.setRelationId(busiShopCompanyPojo.getId());
                busiSysShopAuditPojo.setAuditContent(content.toJSONString());
                busiSysShopAuditPojo.setAuditResultJson(contentResult.toJSONString());
                shopAuditBusiServiceImpl.addInfo(busiSysShopAuditPojo);
            }

            BusiShopCompanyPojo dbEntity = new BusiShopCompanyPojo();
            dbEntity.setId(busiShopCompanyPojo.getId());
            dbEntity.setAuditStatus((short)2);
            shopCompanyBusiMapper.updateByPrimaryKeySelective(dbEntity);
            shopAuditBusiServiceImpl.updateDoUpdate(busiShopCompanyPojo.getCustomerId(),busiShopCompanyPojo.getId());
        }catch(Exception e){
            LoggerUtil.error(ShopCompanyBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }

    public void setContent(JSONObject content,BusiShopCompanyPojo busiShopCompanyPojo){
        content.put("businessLicense",busiShopCompanyPojo.getBusinessLicense());
        content.put("licenseCode",busiShopCompanyPojo.getLicenseCode());
        content.put("idCard",busiShopCompanyPojo.getIdCard());
        content.put("idCardP",busiShopCompanyPojo.getIdCardP());
        content.put("idCardN",busiShopCompanyPojo.getIdCardN());
        content.put("companyPhoto",busiShopCompanyPojo.getCompanyPhoto());
        content.put("legalOwner",busiShopCompanyPojo.getLegalOwner());
        content.put("companyName",busiShopCompanyPojo.getCompanyName());
        content.put("address",busiShopCompanyPojo.getAddress());
        content.put("regionLocation",busiShopCompanyPojo.getRegionLocation());
        content.put("city",busiShopCompanyPojo.getCity());
        content.put("areaCounty",busiShopCompanyPojo.getAreaCounty());
    }
    public void setContentResult(JSONObject contentResult){
        contentResult.put("businessLicenseResult",false);
        contentResult.put("licenseCodeResult",false);
        contentResult.put("idCardResult",false);
        contentResult.put("idCardPhotoResult",false);
        contentResult.put("companyPhotoResult",false);
        contentResult.put("legalOwnerResult",false);
        contentResult.put("companyNameResult",false);
        contentResult.put("addressResult",false);
        contentResult.put("placeResult",false);
    }

}