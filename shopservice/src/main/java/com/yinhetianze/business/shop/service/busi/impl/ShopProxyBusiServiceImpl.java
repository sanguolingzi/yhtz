package com.yinhetianze.business.shop.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.back.BusiSysOptorPojo;
import com.yinhetianze.pojo.back.BusiSysRolePojo;
import com.yinhetianze.pojo.back.BusiSysUserRolePojo;
import com.yinhetianze.pojo.back.ShopProxyPojo;
import com.yinhetianze.systemservice.role.service.busi.SysUserRoleBusiService;
import com.yinhetianze.systemservice.role.service.info.SysRoleInfoService;
import com.yinhetianze.business.shop.mapper.busi.ShopProxyBusiMapper;
import com.yinhetianze.business.shop.model.ShopProxyModel;
import com.yinhetianze.business.shop.service.busi.ShopProxyBusiService;
import com.yinhetianze.systemservice.user.service.busi.SysOptorBusiService;
import com.yinhetianze.business.shop.service.info.ShopProxyInfoService;
import com.yinhetianze.systemservice.user.service.info.SysOptorInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ShopProxyBusiServiceImpl implements ShopProxyBusiService
{
    @Autowired
    private ShopProxyBusiMapper mapper;

    @Autowired
    private ShopProxyInfoService shopProxyInfoServiceImpl;

    @Autowired
    private RedisManager redisManager;


    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;
    @Autowired
    private OSSFileUpload ossFileUpload;



    @Autowired
    private SysOptorBusiService sysOptorBusiServiceImpl;

    @Autowired
    private SysOptorInfoService sysOptorInfoServiceImpl;

    @Autowired
    private SysUserRoleBusiService sysUserRoleBusiServiceImpl;

    @Autowired
    private SysRoleInfoService sysRoleInfoServiceImpl;

    @Override
    public int insertSelective(ShopProxyPojo shopProxyPojo) {
        return mapper.insertSelective(shopProxyPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopProxyPojo shopProxyPojo) {
        return mapper.updateByPrimaryKeySelective(shopProxyPojo);
    }


    @Override
    public int addInfo(ShopProxyModel shopProxyModel) throws BusinessException {
        try {
            BusiSysUserRolePojo busiSysUserRolePojo=new BusiSysUserRolePojo();
            BusiSysOptorPojo busiSysOptorPojo=new BusiSysOptorPojo();
            busiSysOptorPojo.setAccount(shopProxyModel.getShopAccount());
            busiSysOptorPojo.setOptorName(shopProxyModel.getShopName());
            busiSysOptorPojo.setPhone(shopProxyModel.getContactPhone());
            busiSysOptorPojo.setCreateUser(shopProxyModel.getCreateUser());
            if(shopProxyModel.getShopAccount().length()>6){
                busiSysOptorPojo.setLoginPassword((shopProxyModel.getShopAccount()).substring(shopProxyModel.getShopAccount().length()-6));
            }else{
                busiSysOptorPojo.setLoginPassword(shopProxyModel.getShopAccount());
            }
            int result =sysOptorBusiServiceImpl.insertSelective(busiSysOptorPojo);
            if(result<=0){
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }else{
                busiSysUserRolePojo.setOptorId(busiSysOptorPojo.getId());
            }
            BusiSysRolePojo busiSysRolePojo=new BusiSysRolePojo();
            busiSysRolePojo.setRoleName("代理店铺");
            busiSysRolePojo=sysRoleInfoServiceImpl.selectOne(busiSysRolePojo);
            if(CommonUtil.isNotEmpty(busiSysRolePojo)){
                busiSysUserRolePojo.setRoleId(busiSysRolePojo.getId());
            }else{
                LoggerUtil.info(ShopProxyBusiServiceImpl.class,"查询不到代理店铺角色");
            }
            int i= sysUserRoleBusiServiceImpl.addInfo(busiSysUserRolePojo);
            if(i<=0){
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
            ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
            BeanUtils.copyProperties(shopProxyModel,shopProxyPojo);
            shopProxyPojo.setOptorId(busiSysOptorPojo.getId());
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
            String mallImagePath = sysPropertiesUtils.getStringValue("shopLogoImagePath");
            String ossPath = backOssRootPath+mallImagePath;
            String storeFilePath = fileUploadPath +ossPath;
            LoggerUtil.info(ShopProxyBusiServiceImpl.class, "backstageShopProxy add read local file "+storeFilePath+ File.separatorChar+shopProxyModel.getShopLogo());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,shopProxyModel.getShopLogo());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                 throw new BusinessException("获取图片地址失败");
            shopProxyPojo.setShopLogo(key);
            int j=this.insertSelective(shopProxyPojo);
            if(j<=0)
                throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }catch (Exception e){
            if (e instanceof DuplicateKeyException) {
                throw new BusinessException("该账号已存在!");
            }
            LoggerUtil.error(ShopProxyBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return  1;
    }

    @Override
    public int updateInfo(ShopProxyModel shopProxyModel) throws BusinessException {
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        BeanUtils.copyProperties(shopProxyModel,shopProxyPojo);
        ShopProxyPojo dbPojo=new ShopProxyPojo();
        dbPojo.setId(shopProxyModel.getId());
        dbPojo.setDelFlag((short)0);
        dbPojo=shopProxyInfoServiceImpl.selectOne(dbPojo);
        try {
            if(CommonUtil.isNotEmpty(shopProxyPojo.getShopLogo()) && !dbPojo.getShopLogo().equals(shopProxyPojo.getShopLogo())){
                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("shopOssRootPath");
                String mallImagePath = sysPropertiesUtils.getStringValue("shopLogoImagePath");
                String ossPath = backOssRootPath+mallImagePath;
                String storeFilePath = fileUploadPath +ossPath;
                LoggerUtil.info(ShopProxyBusiServiceImpl.class, "backstageShopProxy update read local file "+storeFilePath+ File.separatorChar+shopProxyModel.getShopLogo());
                //读取本地文件
                File file = FileUtil.loadFile(storeFilePath,shopProxyModel.getShopLogo());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                if(key == null)
                    return 0;
                shopProxyPojo.setShopLogo(key);
            }
        }catch (Exception e){
            LoggerUtil.error(ShopProxyBusiServiceImpl.class,e.getMessage());
        }
        int result=this.updateByPrimaryKeySelective(shopProxyPojo);
        if(result<=0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return 1;
    }

    @Override
    public int addMessage(ShopProxyModel shopProxyModel) throws BusinessException {
        ShopProxyPojo shopProxyPojo=new ShopProxyPojo();
        shopProxyPojo.setId(shopProxyModel.getId());
        shopProxyPojo.setDelFlag((short)0);
        shopProxyPojo=shopProxyInfoServiceImpl.selectOne(shopProxyPojo);
        BusiSysOptorPojo dbSysOptorPojo=new BusiSysOptorPojo();
        dbSysOptorPojo.setAccount(shopProxyPojo.getShopAccount());
        dbSysOptorPojo=sysOptorInfoServiceImpl.select(dbSysOptorPojo);
        BusiSysOptorPojo busiSysOptorPojo=new BusiSysOptorPojo();
        busiSysOptorPojo.setId(dbSysOptorPojo.getId());
        String passWord="666666";
        busiSysOptorPojo.setLoginPassword(MD5CoderUtil.encode(MD5CoderUtil.encode(passWord)));
        try {
            int i= sysOptorBusiServiceImpl.updateByPrimaryKeySelective(busiSysOptorPojo);
            if(i<=0)
                throw new BusinessException("修改默认密码失败");
            Map map = new HashMap();
            map.put("account",shopProxyPojo.getShopAccount());
            map.put("password",passWord);
            if(!SendSmsUtil.sendSms(SendSmsUtil.sms_template_accountNotification,shopProxyPojo.getShopAccount(),map)){
                throw new BusinessException("短信发送失败!");
            }
            shopProxyPojo.setIsMessage((short)0);
            int result=updateByPrimaryKeySelective(shopProxyPojo);
            if(result<=0){
                throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }catch (Exception e){
            LoggerUtil.error(ShopProxyBusiServiceImpl.class,e.toString());
            throw new BusinessException("短信发送错误!");
        }
        return 1;
    }
}