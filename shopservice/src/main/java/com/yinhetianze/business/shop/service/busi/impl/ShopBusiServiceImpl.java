package com.yinhetianze.business.shop.service.busi.impl;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.business.shop.mapper.busi.ShopBusiMapper;
import com.yinhetianze.business.shop.model.ShopCompanyPageModel;
import com.yinhetianze.business.shop.service.busi.ShopBankrollBusiService;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.business.shop.service.busi.ShopCompanyBusiService;
import com.yinhetianze.business.shop.service.info.ShopCompanyInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopaudit.service.busi.ShopAuditBusiService;
import com.yinhetianze.business.shopaudit.service.info.ShopAuditInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import com.yinhetianze.pojo.shop.BusiShopCompanyPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.BusiSysShopAuditPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class ShopBusiServiceImpl implements ShopBusiService
{
    @Autowired
    private ShopBusiMapper shopBusiMapper;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private ShopBankrollBusiService shopBankrollBusiServiceImpl;

    @Autowired
    private MessageBusiService messageBusiServiceImpl;

    @Autowired
    private ShopAuditBusiService shopAuditBusiServiceImpl;

    @Autowired
    private ShopAuditInfoService shopAuditInfoServiceImpl;

    @Autowired
    private ShopCompanyInfoService shopCompanyInfoServiceImpl;

    @Autowired
    private ShopCompanyBusiService shopCompanyBusiServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public int insert(BusiShopPojo record) {
        return shopBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiShopPojo record) {
        return shopBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiShopPojo record) {
        return shopBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiShopPojo record) {
        return shopBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int addShopInfo(BusiShopPojo record) throws BusinessException {

        BusiShopPojo dbEntity = shopInfoServiceImpl.selectByCustomerId(record.getCustomerId());
        if(dbEntity != null){
            throw new BusinessException("店铺信息已存在!");
        }

        //上传图片
        try {
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            //String fileUploadPath = "D:\\testFile";
            String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
            String shopLogoImagePath = sysPropertiesUtils.getStringValue("shopLogoImagePath");
            String ossPath = shopOssRootPath+shopLogoImagePath;

            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(ShopBusiServiceImpl.class, "addShopInfo add read local file "+storeFilePath+File.separatorChar+record.getShopLogo());
            //读取本地文件
            //店铺logo
            File file = FileUtil.loadFile(storeFilePath,record.getShopLogo());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            record.setShopLogo(key);

        } catch (Exception e) {
            LoggerUtil.error(ShopBusiServiceImpl.class, e.getMessage());
            return 0;
        }
        dbEntity = new BusiShopPojo();

        BeanUtils.copyProperties(record,dbEntity);

        dbEntity.setCustomerId(record.getCustomerId());
        dbEntity.setAuditStatus((short)2);

        insertSelective(dbEntity);

        //新增店铺 完善之前新增的企业信息
        BusiShopCompanyPojo busiShopCompanyPojo = new BusiShopCompanyPojo();
        busiShopCompanyPojo.setId(dbEntity.getCompanyinfoId());
        busiShopCompanyPojo.setCustomerId(record.getCustomerId());
        busiShopCompanyPojo.setDelFlag((short)1);
        //校验店铺和企业信息是否属于同一个用户
        if(shopCompanyInfoServiceImpl.selectByCondition(busiShopCompanyPojo) == null)
            throw new BusinessException("BC0050",ResponseConstant.RESULT_DESCRIPTION_FAILED);


        busiShopCompanyPojo.setDelFlag((short)0);
        shopCompanyBusiServiceImpl.updateByPrimaryKeySelective(busiShopCompanyPojo);


        record.setId(dbEntity.getId());

        //添加账户
        BusiShopBankrollPojo busiShopBankrollPojo = new BusiShopBankrollPojo();
        busiShopBankrollPojo.setShopId(dbEntity.getId());
        shopBankrollBusiServiceImpl.add(busiShopBankrollPojo);

        //添加 消息关系表
        BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
        busiMessagePojo.setId(dbEntity.getId());
        messageBusiServiceImpl.addInfo(busiMessagePojo);

        //添加待审核记录
        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setCustomerId(dbEntity.getCustomerId());
        busiSysShopAuditPojo.setAuditType((short)2);
        List<BusiSysShopAuditPojo> list =  shopAuditInfoServiceImpl.select(busiSysShopAuditPojo);

        if(CommonUtil.isEmpty(list)){
            LoggerUtil.error(ShopBusiServiceImpl.class,"新增店铺未找到对应的综合审核记录");
            throw new BusinessException("BC0050",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }


        busiSysShopAuditPojo = list.get(0);
        try{
            JSONObject contentJson = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditContent(),JSONObject.class);
            setContent(contentJson,record);

            JSONObject contentResult = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditResultJson(),JSONObject.class);
            setContentResult(contentResult);

            BusiSysShopAuditPojo temp = new BusiSysShopAuditPojo();
            temp.setId(busiSysShopAuditPojo.getId());
            temp.setAuditContent(contentJson.toJSONString());
            temp.setAuditResultJson(contentResult.toJSONString());
            temp.setAuditStep((short)3);
            shopAuditBusiServiceImpl.updateInfo(temp);
        }catch(Exception e){
            LoggerUtil.error(ShopBusiServiceImpl.class,"新增店铺综合审核记录添加审核内容出错!  "+e.toString());
            throw new BusinessException("BC0050",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }


    @Override
    public int updateShopInfo(BusiShopPojo record) throws BusinessException {


        BusiShopPojo temp = new BusiShopPojo();
        temp.setId(record.getId());
        temp = shopInfoServiceImpl.selectOne(temp);
        if(temp == null){
            return 0;
        }

        //查询是否存在店铺审核类型的 待审核记录
        BusiSysShopAuditPojo busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setRelationId(temp.getId());
        busiSysShopAuditPojo.setAuditType((short)1);
        busiSysShopAuditPojo.setAuditStatus((short)2);
        busiSysShopAuditPojo = shopAuditInfoServiceImpl.selectOne(busiSysShopAuditPojo);
        if(busiSysShopAuditPojo!=null){
            throw new BusinessException("存在待审核记录,请等待审核结果!");
        }


        try{
            //比较shop logo
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            //String fileUploadPath = "D:\\testFile";
            String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");



            if(!temp.getShopLogo().equals(record.getShopLogo())
                    && CommonUtil.isNotEmpty(record.getShopLogo())){

                //提交的图片路径中若包含路径名称 则不需要重新上传图片
                //针对 店铺审核过程中 第一次上传图片 审核不通过  第二次使用前一此的图片直接提交 导致的问题
                if(!(File.separatorChar+record.getShopLogo()).startsWith(shopOssRootPath)){

                    String shopLogoImagePath = sysPropertiesUtils.getStringValue("shopLogoImagePath");
                    String ossPath = shopOssRootPath+shopLogoImagePath;
                    String storeFilePath = fileUploadPath +ossPath;

                    LoggerUtil.info(ShopBusiServiceImpl.class, "updateShopInfo add read local file "+storeFilePath+File.separatorChar+record.getShopLogo());
                    //读取本地文件
                    //店铺logo
                    File file = FileUtil.loadFile(storeFilePath,record.getShopLogo());
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        return 0;
                    record.setShopLogo(key);
                }
            }

            //比较主图
            if(CommonUtil.isNotEmpty(record.getShopMainPhoto())){
                if(CommonUtil.isEmpty(temp.getShopMainPhoto()) || (!temp.getShopMainPhoto().equals(record.getShopMainPhoto()))){
                    String shopMainImagePath = sysPropertiesUtils.getStringValue("shopMainImagePath");
                    String ossPath = shopOssRootPath+shopMainImagePath;

                    String storeFilePath = fileUploadPath +ossPath;
                    LoggerUtil.info(ShopBusiServiceImpl.class, "updateShopInfo add read local file "+storeFilePath+File.separatorChar+record.getShopMainPhoto());
                    //读取本地文件
                    //店铺logo
                    File file = FileUtil.loadFile(storeFilePath,record.getShopMainPhoto());
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        return 0;
                    record.setShopMainPhoto(key);
                }
            }

            //比较Wap图片
            if(CommonUtil.isNotEmpty(record.getShopWapPhoto())){
                if(CommonUtil.isEmpty(temp.getShopWapPhoto()) || (!temp.getShopWapPhoto().equals(record.getShopWapPhoto()))){
                    String shopWapImagePath = sysPropertiesUtils.getStringValue("shopWapImagePath");
                    String ossPath = shopOssRootPath+shopWapImagePath;

                    String storeFilePath = fileUploadPath +ossPath;
                    LoggerUtil.info(ShopBusiServiceImpl.class, "updateShopInfo add read local file "+storeFilePath+File.separatorChar+record.getShopWapPhoto());
                    //读取本地文件
                    //店铺logo
                    File file = FileUtil.loadFile(storeFilePath,record.getShopWapPhoto());
                    //上传oss
                    String key = ossFileUpload.fileUpload(file, ossPath);
                    if(key == null)
                        return 0;
                    record.setShopWapPhoto(key);
                }
            }

        }catch(Exception e){
            LoggerUtil.error(ShopBusiServiceImpl.class,"修改店铺上传图片失败");
            return 0;
        }

        //若无待审核记录 则添加
        //首先查找 该店铺是否有企业店铺综合审核记录  若有 则修改  若没有 认为是单独新增一个店铺审核  新增一条 店铺审核信息
        busiSysShopAuditPojo = new BusiSysShopAuditPojo();
        busiSysShopAuditPojo.setRelationId(temp.getCompanyinfoId());
        busiSysShopAuditPojo.setAuditType((short)2);
        busiSysShopAuditPojo.setAuditStatus((short)2);
        busiSysShopAuditPojo = shopAuditInfoServiceImpl.selectOne(busiSysShopAuditPojo);
        if(busiSysShopAuditPojo != null){
            try{
                JSONObject contentJson = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditContent(),JSONObject.class);
                setContent(contentJson,record);

                JSONObject contentResult = CommonUtil.readFromString(busiSysShopAuditPojo.getAuditResultJson(),JSONObject.class);
                setContentResult(contentResult);

                BusiSysShopAuditPojo busiSysShopAuditPojoTemp = new BusiSysShopAuditPojo();
                busiSysShopAuditPojoTemp.setId(busiSysShopAuditPojo.getId());
                busiSysShopAuditPojoTemp.setAuditContent(contentJson.toJSONString());
                busiSysShopAuditPojoTemp.setAuditResultJson(contentResult.toJSONString());
                shopAuditBusiServiceImpl.updateInfo(busiSysShopAuditPojoTemp);
            }catch (Exception e){

            }
        }else{
            busiSysShopAuditPojo = new BusiSysShopAuditPojo();
            busiSysShopAuditPojo.setAuditType((short)1);
            busiSysShopAuditPojo.setRelationId(temp.getId());
            busiSysShopAuditPojo.setAuditStatus((short)2);
            busiSysShopAuditPojo.setCustomerId(temp.getCustomerId());

            JSONObject contentJson = new JSONObject();
            JSONObject contentResult = new JSONObject();


            setContent(contentJson,record);

            contentJson.put("shopWapPhoto",record.getShopWapPhoto());
            contentJson.put("postageFree",record.getPostageFree());

            busiSysShopAuditPojo.setAuditContent(contentJson.toJSONString());

            contentResult.put("shopWapPhotoResult",false);
            contentResult.put("postageFreeResult",false);
            setContentResult(contentResult);
            busiSysShopAuditPojo.setAuditResultJson(contentResult.toJSONString());
            busiSysShopAuditPojo.setAuditStep((short)2);
            shopAuditBusiServiceImpl.addInfo(busiSysShopAuditPojo);
        }

        temp.setAuditStatus((short)2);
        updateByPrimaryKeySelective(temp);

        return 1;
    }

    @Override
    public int addShopVisitCount(Integer id) {
        return shopBusiMapper.addShopVisitCount(id);
    }

    public void setContent(JSONObject contentJson, BusiShopPojo record){
        contentJson.put("shopId",record.getId());
        contentJson.put("shopLogo",record.getShopLogo());
        contentJson.put("phoneShow",record.getPhoneShow());
        contentJson.put("shopType",record.getShopType());
        contentJson.put("shopMainProduct",record.getShopMainProduct());
        contentJson.put("shopMemo",record.getShopMemo());
        contentJson.put("shopPhone",record.getShopPhone());
        contentJson.put("shopName",record.getShopName());
        contentJson.put("shopMainPhoto",record.getShopMainPhoto());
    }

    public void setContentResult(JSONObject contentResult){
        contentResult.put("shopLogoResult",false);
        contentResult.put("shopTypeResult",false);
        contentResult.put("shopMainProductResult",false);
        contentResult.put("shopMainPhotoResult",false);
        contentResult.put("shopMemoResult",false);
        contentResult.put("shopPhoneResult",false);
        contentResult.put("shopNameResult",false);
    }

    @Override
    public int updateShopCompanyInfo(ShopCompanyPageModel shopCompanyPageModel) throws BusinessException {
        try {
            BusiShopCompanyPojo busiShopCompanyPojo=new BusiShopCompanyPojo();
            busiShopCompanyPojo.setId(shopCompanyPageModel.getCompanyinfoId());
            busiShopCompanyPojo.setBusinessLicense(shopCompanyPageModel.getBusinessLicense());
            busiShopCompanyPojo.setRegionLocation(shopCompanyPageModel.getRegionLocation());
            busiShopCompanyPojo.setCity(shopCompanyPageModel.getCity());
            busiShopCompanyPojo.setAreaCounty(shopCompanyPageModel.getAreaCounty());
            busiShopCompanyPojo.setAddress(shopCompanyPageModel.getAddress());
            BusiShopCompanyPojo dbShopCompanyPojo=new BusiShopCompanyPojo();
            dbShopCompanyPojo.setId(shopCompanyPageModel.getCompanyinfoId());
            dbShopCompanyPojo.setAuditStatus((short)0);
            dbShopCompanyPojo=shopCompanyInfoServiceImpl.selectOne(dbShopCompanyPojo);
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String shopOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
            if(CommonUtil.isNotEmpty(busiShopCompanyPojo.getBusinessLicense()) && !dbShopCompanyPojo.getBusinessLicense().equals(busiShopCompanyPojo.getBusinessLicense())){
                String goodsImagePath = sysPropertiesUtils.getStringValue("companylicImagePath");
                String ossPath = goodsImagePath;
                String storeFilePath = fileUploadPath+shopOssRootPath +ossPath;
                LoggerUtil.info(ShopBusiServiceImpl.class, "OneArea update read local file "+storeFilePath+ File.separatorChar+shopCompanyPageModel.getBusinessLicense());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,shopCompanyPageModel.getBusinessLicense());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null){
                    LoggerUtil.error(ShopBusiServiceImpl.class,"获取企业营业执照失败");
                    throw new BusinessException("获取企业营业执照失败!");
                }
                busiShopCompanyPojo.setBusinessLicense(key);
            }
            int i=shopCompanyBusiServiceImpl.updateByPrimaryKeySelective(busiShopCompanyPojo);
            if(i<=0){
                LoggerUtil.error(ShopBusiServiceImpl.class,"修改企业信息失败");
                throw new BusinessException("修改企业信息失败!");
            }
            BusiShopPojo busiShopPojo=new BusiShopPojo();
            busiShopPojo.setId(shopCompanyPageModel.getShopId());
            busiShopPojo.setShopName(shopCompanyPageModel.getShopName());
            busiShopPojo.setShopLogo(shopCompanyPageModel.getShopLogo());
            busiShopPojo.setShopMainPhoto(shopCompanyPageModel.getShopMainPhoto());
            busiShopPojo.setShopWapPhoto(shopCompanyPageModel.getShopWapPhoto());
            busiShopPojo.setShopDecorate(shopCompanyPageModel.getShopDecorate());
            busiShopPojo.setShopMemo(shopCompanyPageModel.getShopMemo());
            BusiShopPojo dbShopPojo=new BusiShopPojo();
            dbShopPojo.setId(shopCompanyPageModel.getShopId());
            dbShopPojo.setAuditStatus((short)0);
            dbShopPojo=shopInfoServiceImpl.selectOne(dbShopPojo);
            if(CommonUtil.isNotEmpty(busiShopPojo.getShopLogo()) && !dbShopPojo.getShopLogo().equals(busiShopPojo.getShopLogo())){
                String goodsImagePath = sysPropertiesUtils.getStringValue("shopLogoImagePath");
                String ossPath = goodsImagePath;
                String storeFilePath = fileUploadPath+shopOssRootPath +ossPath;
                LoggerUtil.info(ShopBusiServiceImpl.class, "OneArea update read local file "+storeFilePath+ File.separatorChar+shopCompanyPageModel.getShopLogo());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,shopCompanyPageModel.getShopLogo());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null){
                    LoggerUtil.error(ShopBusiServiceImpl.class,"获取店铺Logo图失败");
                    throw new BusinessException("获取店铺Logo图失败!");
                }
                busiShopPojo.setShopLogo(key);
            }
            if(CommonUtil.isNotEmpty(busiShopPojo.getShopWapPhoto()) && !dbShopPojo.getShopWapPhoto().equals(busiShopPojo.getShopWapPhoto())){
                String goodsImagePath = sysPropertiesUtils.getStringValue("shopWapImagePath");
                String ossPath = goodsImagePath;
                String storeFilePath = fileUploadPath+shopOssRootPath +ossPath;
                LoggerUtil.info(ShopBusiServiceImpl.class, "OneArea update read local file "+storeFilePath+ File.separatorChar+shopCompanyPageModel.getShopWapPhoto());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,shopCompanyPageModel.getShopWapPhoto());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null){
                    LoggerUtil.error(ShopBusiServiceImpl.class,"获取店铺Wap图失败");
                    throw new BusinessException("获取店铺Wap图失败!");
                }
                busiShopPojo.setShopWapPhoto(key);
            }
            if(CommonUtil.isNotEmpty(busiShopPojo.getShopMainPhoto()) && !dbShopPojo.getShopMainPhoto().equals(busiShopPojo.getShopMainPhoto())){
                String goodsImagePath = sysPropertiesUtils.getStringValue("shopMainImagePath");
                String ossPath = goodsImagePath;
                String storeFilePath = fileUploadPath+shopOssRootPath +ossPath;
                LoggerUtil.info(ShopBusiServiceImpl.class, "OneArea update read local file "+storeFilePath+ File.separatorChar+shopCompanyPageModel.getShopMainPhoto());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,shopCompanyPageModel.getShopMainPhoto());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null){
                    LoggerUtil.error(ShopBusiServiceImpl.class,"获取店铺主图失败");
                    throw new BusinessException("获取店铺主图失败!");
                }
                busiShopPojo.setShopMainPhoto(key);
            }
            int result=updateByPrimaryKeySelective(busiShopPojo);
            if(result<=0){
                LoggerUtil.error(ShopBusiServiceImpl.class,"修改店铺信息失败");
                throw new BusinessException("修改店铺信息失败!");
            }
        }catch (Exception e){
            LoggerUtil.error(ShopBusiServiceImpl.class,e.getMessage());
            throw  new BusinessException("修改店铺或企业信息失败");
        }
        return 1;
    }
}