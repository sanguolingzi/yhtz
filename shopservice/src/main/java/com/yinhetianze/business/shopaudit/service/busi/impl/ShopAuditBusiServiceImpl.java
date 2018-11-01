package com.yinhetianze.business.shopaudit.service.busi.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopaudit.mapper.busi.ShopAuditBusiMapper;
import com.yinhetianze.business.shopaudit.model.BusiSysShopAuditModel;
import com.yinhetianze.business.shopaudit.service.busi.ShopAuditBusiService;
import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopAuditBusiServiceImpl implements ShopAuditBusiService
{
    @Autowired
    private ShopAuditBusiMapper shopAuditBusiMapper;

    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;


    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private ShopBusiService shopBusiServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public int updateInfo(BusiSysShopAuditPojo busiSysShopAudit) {
        return shopAuditBusiMapper.updateByPrimaryKeySelective(busiSysShopAudit);
    }

    @Override
    public int updateInfo(BusiSysShopAuditModel busiSysShopAuditModel) throws BusinessException{
        BusiSysShopAuditPojo busiSysShopAuditPojo  = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setId(busiSysShopAuditModel.getId());

        busiSysShopAuditPojo = shopAuditInfoServiceImpl.selectOne(busiSysShopAuditPojo);
        if(busiSysShopAuditPojo == null )
             return 0;
        BusiShopCompanyPojo busiShopCompanyPojo = shopCompanyInfoServiceImpl.selectByCustomerId(busiSysShopAuditPojo.getCustomerId());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(busiSysShopAuditPojo.getCustomerId());

        if(busiShopCompanyPojo == null || busiShopPojo == null )
            return 0;


        BusiSysShopAuditPojo auditTemp = new BusiSysShopAuditPojo();
        auditTemp.setId(busiSysShopAuditPojo.getId());
        auditTemp.setAuditStatus(busiSysShopAuditModel.getAuditStatus());
        //审核不通过  该条记录为可编辑状态
        if(1 == busiSysShopAuditModel.getAuditStatus()){
            auditTemp.setDoUpdate((short)1);
        }else{
            auditTemp.setDoUpdate((short)0);
        }

        auditTemp.setAuditResultJson(busiSysShopAuditModel.getAuditResultJson());
        auditTemp.setUpdateUser(busiSysShopAuditModel.getUpdateUser());
        auditTemp.setUpdateTime(new java.util.Date());
        auditTemp.setReason(busiSysShopAuditModel.getReason());
        updateInfo(auditTemp);

        if(busiSysShopAuditPojo.getAuditType() == 2){//综合审核 需要对企业 店铺 均设置状态
            //企业信息
            BusiShopCompanyPojo busiShopCompanyTemp = new BusiShopCompanyPojo();
            busiShopCompanyTemp.setId(busiShopCompanyPojo.getId());
            busiShopCompanyTemp.setAuditStatus(busiSysShopAuditModel.getAuditStatus());

            //店铺信息
            BusiShopPojo busiShopTemp = new BusiShopPojo();
            busiShopTemp.setId(busiShopPojo.getId());
            busiShopTemp.setAuditStatus(busiSysShopAuditModel.getAuditStatus());
            //审核通过 需要更新企业和店铺信息
            if(busiSysShopAuditModel.getAuditStatus() == 0){
                try{
                    JSONObject content = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditContent(), JSONObject.class);

                    busiShopCompanyTemp.setBusinessLicense(content.getString("businessLicense"));
                    busiShopCompanyTemp.setLicenseCode(content.getString("licenseCode"));
                    busiShopCompanyTemp.setLegalOwner(content.getString("legalOwner"));
                    busiShopCompanyTemp.setCompanyPhoto(content.getString("companyPhoto"));
                    busiShopCompanyTemp.setCompanyName(content.getString("companyName"));
                    busiShopCompanyTemp.setIdCardN(content.getString("idCardN"));
                    busiShopCompanyTemp.setIdCardP(content.getString("idCardP"));
                    busiShopCompanyTemp.setIdCard(content.getString("idCard"));
                    busiShopCompanyTemp.setRegionLocation(content.getString("regionLocation"));
                    busiShopCompanyTemp.setCity(content.getString("city"));
                    busiShopCompanyTemp.setAreaCounty(content.getString("areaCounty"));
                    busiShopCompanyTemp.setAddress(content.getString("address"));

                    busiShopTemp.setShopLogo(content.getString("shopLogo"));
                    busiShopTemp.setShopType(content.getShort("shopType"));
                    busiShopTemp.setShopMainProduct(content.getString("shopMainProduct"));
                    busiShopTemp.setShopMemo(content.getString("shopMemo"));
                    busiShopTemp.setShopName(content.getString("shopName"));
                    busiShopTemp.setShopPhone(content.getString("shopPhone"));
                    busiShopTemp.setPhoneShow(content.getShort("phoneShow"));

                    //若没有店铺推广二维码 则生成
                    if(busiShopTemp.getShopQrcode() == null){
                        String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                        //String fileUploadPath = "d:\\testFile";
                        String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
                        String shopSpreadQrcode = sysPropertiesUtils.getStringValue("shopSpreadQrcode");
                        String fileName = CommonUtil.getSerialnumber()+".png";

                        String dir = fileUploadPath+shopOssRootPath+shopSpreadQrcode;
                        String finalPath = dir+ File.separator+fileName;
                        String wapUrl = sysPropertiesUtils.getStringValue("wapUrl");
                        byte[] b = QrcodeUtils.createQrcode(wapUrl+"shopIndex?shopId="+busiShopTemp.getId(),null);
                        java.io.File fileDir = new  java.io.File(dir);
                        if(!fileDir.exists()){
                            fileDir.mkdirs();
                        }
                        java.io.File qrcodeFile = new  java.io.File(finalPath);
                        FileOutputStream fos = new FileOutputStream(qrcodeFile);
                        fos.write(b);

                        String key = ossFileUpload.fileUpload(qrcodeFile,shopOssRootPath+shopSpreadQrcode);
                        if(key == null){
                            throw new Exception(" oss 店铺推广二维码文件上传失败!"+finalPath);
                        }else{
                            busiShopTemp.setShopQrcode(key);
                        }
                    }

                }catch(Exception e){
                    LoggerUtil.error(ShopAuditBusiServiceImpl.class,e.getMessage());
                    throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
                }
            }

            shopCompanyBusiServiceImpl.updateByPrimaryKeySelective(busiShopCompanyTemp);
            shopBusiServiceImpl.updateByPrimaryKeySelective(busiShopTemp);

        }else if(busiSysShopAuditPojo.getAuditType() == 1){//店铺审核
            try{
                BusiShopPojo busiShopTemp = new BusiShopPojo();
                busiShopTemp.setId(busiShopPojo.getId());
                busiShopTemp.setAuditStatus(busiSysShopAuditModel.getAuditStatus());

                if(busiSysShopAuditModel.getAuditStatus() == 0){

                    JSONObject content = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditContent(), JSONObject.class);
                    busiShopTemp.setShopLogo(content.getString("shopLogo"));
                    busiShopTemp.setShopType(content.getShort("shopType"));
                    busiShopTemp.setShopMainPhoto(content.getString("shopMainPhoto"));
                    busiShopTemp.setShopMainProduct(content.getString("shopMainProduct"));
                    busiShopTemp.setShopWapPhoto(content.getString("shopWapPhoto"));
                    busiShopTemp.setShopMemo(content.getString("shopMemo"));
                    busiShopTemp.setShopName(content.getString("shopName"));
                    busiShopTemp.setShopPhone(content.getString("shopPhone"));
                    busiShopTemp.setPhoneShow(content.getShort("phoneShow"));
                    busiShopTemp.setPostageFree(content.getBigDecimal("postageFree"));
                }
                shopBusiServiceImpl.updateByPrimaryKeySelective(busiShopTemp);
            }catch(Exception e){
                LoggerUtil.error(ShopAuditBusiServiceImpl.class,e.getMessage());
                throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }
        return 1;
    }

    @Override
    public int addInfo(BusiSysShopAuditPojo busiSysShopAudit) {
        return shopAuditBusiMapper.insertSelective(busiSysShopAudit);
    }

    @Override
    public int updateDoUpdate(Integer customerId,Integer relationId) {
        return shopAuditBusiMapper.updateDoUpdate(customerId,relationId);
    }
}