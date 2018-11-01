package com.yinhetianze.business.shopbrand.service.busi.impl;

import com.yinhetianze.business.brand.service.BrandBusiService;
import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.business.shopbrand.mapper.busi.ShopBrandBusiMapper;
import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.business.shopbrand.service.busi.ShopBrandBusiService;
import com.yinhetianze.business.shopbrand.service.info.ShopBrandInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional(rollbackFor = Exception.class)
public class ShopBrandBusiServiceImpl implements ShopBrandBusiService
{
    @Autowired
    private ShopBrandBusiMapper shopBrandBusiMapper;

    @Autowired
    private ShopBrandInfoService shopBrandInfoServiceImpl;

    @Autowired
    private BrandBusiService brandBusiServiceImpl;

    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public int addInfo(BusiShopBrandModel busiShopBrandModel) throws BusinessException {

        //选择已有系统品牌 直接建立店铺品牌关系
        //若不存在id 则是店铺新增品牌
        BusiShopBrandPojo busiShopBrandPojo = new BusiShopBrandPojo();
        busiShopBrandPojo.setShopId(busiShopBrandModel.getShopId());
        busiShopBrandPojo.setIsShow(busiShopBrandModel.getIsShow());
        busiShopBrandPojo.setSort(busiShopBrandModel.getSort());
        if(busiShopBrandModel.getAddSelf() == 1 && CommonUtil.isNotEmpty(busiShopBrandModel.getBrandId())){
            busiShopBrandPojo.setBrandId(busiShopBrandModel.getBrandId());
            shopBrandBusiMapper.insertSelective(busiShopBrandPojo);
        }else {
            try{

                BrandPojo brandPojo = new BrandPojo();
                brandPojo.setBrandName(busiShopBrandModel.getBrandName());
                brandPojo.setDelFlag((short)0);
                brandPojo = brandInfoServiceImpl.findBrand(brandPojo);
                if(brandPojo != null)
                    throw new BusinessException("品牌已存在!");

                brandPojo = new BrandPojo();
                brandPojo.setBrandType((short) 1);
                brandPojo.setBrandName(busiShopBrandModel.getBrandName());
                brandPojo.setTitle(busiShopBrandModel.getBrandName());

                brandPojo.setFirstWord("0");
                brandPojo.setSort(20);
                brandPojo.setIsShow((short) 1);

                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                //String fileUploadPath = "D:\\testFile";
                String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
                String shopBrandImagePath = sysPropertiesUtils.getStringValue("shopBrandImagePath");
                String ossPath = shopOssRootPath+shopBrandImagePath;

                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(ShopBrandBusiServiceImpl.class, "shopBrand add read local file "+storeFilePath+File.separatorChar+ busiShopBrandModel.getShopBrandImg());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath, busiShopBrandModel.getShopBrandImg());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;

                brandPojo.setBrandShopImg(key);
                int result = brandBusiServiceImpl.addBrand(brandPojo);
                if (result <= 0) {
                    return 0;
                }
                busiShopBrandPojo.setBrandId(brandPojo.getId());
                shopBrandBusiMapper.insertSelective(busiShopBrandPojo);
            }catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    throw new BusinessException("品牌已存在!");
                }else if(e instanceof BusinessException){
                    throw (BusinessException)e;
                }
                LoggerUtil.error(ShopBrandBusiServiceImpl.class,e.getMessage());
                throw new BusinessException("品牌添加失败!");
            }
        }
        return 1;
    }

    @Override
    public int updateByPrimaryKeySelective(BusiShopBrandPojo busiShopBrandPojo) {
        return shopBrandBusiMapper.updateByPrimaryKeySelective(busiShopBrandPojo);
    }

    @Override
    public int updateInfo(BusiShopBrandModel busiShopBrandModel) throws BusinessException {


        BusiShopBrandPojo busiShopBrandPojo = new BusiShopBrandPojo();
        busiShopBrandPojo.setShopId(busiShopBrandModel.getShopId());
        busiShopBrandPojo.setId(busiShopBrandModel.getId());


        busiShopBrandPojo=shopBrandInfoServiceImpl.selectOne(busiShopBrandPojo);
        if(busiShopBrandPojo == null){
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        //TODO
        BusiShopBrandPojo temp = new BusiShopBrandPojo();
        BeanUtils.copyProperties(busiShopBrandModel,temp);
        if(CommonUtil.isNotEmpty(busiShopBrandModel.getShopBrandImg())){
            if(!busiShopBrandPojo.getShopBrandImg().equals(busiShopBrandModel.getShopBrandImg())){
                try{
                    String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                    //String fileUploadPath = "D:\\testFile";
                    String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
                    String shopBrandImagePath = sysPropertiesUtils.getStringValue("shopBrandImagePath");
                    String ossPath = shopOssRootPath+shopBrandImagePath;

                    String storeFilePath = fileUploadPath +ossPath;
                    LoggerUtil.info(ShopBrandBusiServiceImpl.class, "shopBrand add read local file "+storeFilePath+File.separatorChar+ busiShopBrandModel.getShopBrandImg());
                    //读取本地文件
                    File file = FileUtil.loadFile(storeFilePath, busiShopBrandModel.getShopBrandImg());
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        return 0;

                    temp.setShopBrandImg(key);
                }catch(Exception e){

                }
            }
        }
        return 1;
    }
}