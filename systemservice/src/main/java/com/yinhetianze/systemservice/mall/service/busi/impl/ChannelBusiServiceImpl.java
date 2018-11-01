package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.systemservice.mall.mapper.busi.ChannelBusiMapper;
import com.yinhetianze.systemservice.mall.model.ChannelModel;
import com.yinhetianze.systemservice.mall.service.busi.ChannelBusiService;
import com.yinhetianze.systemservice.mall.service.info.ChannelInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.ChannelPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ChannelBusiServiceImpl implements ChannelBusiService
{
    @Autowired
    private ChannelBusiMapper mapper;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private ChannelInfoService channelInfoServiceImpl;

    @Override
    public int insertSelective(ChannelPojo channelPojo){
        return mapper.insertSelective(channelPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(ChannelPojo channelPojo){
        channelPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(channelPojo);
    }

    @Override
    public int addInfo(ChannelModel channelModel) throws BusinessException {
        try {
            ChannelPojo channelPojo = new ChannelPojo();
            BeanUtils.copyProperties(channelModel, channelPojo);
            if(channelModel.getLinkMarkup()!=0){
                int number=channelModel.getChannelLink().indexOf("?");
                if(number!=-1){
                    channelPojo.setLinkParameter(channelModel.getChannelLink().substring(number+1));
                }
            }
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
            String channelImagePath = sysPropertiesUtils.getStringValue("channelImagePath");
            String ossPath = backOssRootPath+channelImagePath;

            String storeFilePath = fileUploadPath + ossPath;
            LoggerUtil.info(com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl.class, "channel add read local file "+storeFilePath+File.separatorChar+channelModel.getChannelImage());
            //读取本地文件
            File file = FileUtil.loadFile(storeFilePath,channelModel.getChannelImage());
            //上传oss
            String key = ossFileUpload.fileUpload(file, ossPath);
            if(key == null)
                return 0;
            //删除本地文件
            //file.delete();
            channelPojo.setChannelImage(key);
            return mapper.insertSelective(channelPojo);
        } catch (Exception e) {
            LoggerUtil.error(com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int updateInfo(ChannelModel channelModel) throws BusinessException {
        try{
            ChannelPojo dbPojo = new ChannelPojo();
            dbPojo.setDelFlag((short)0);
            dbPojo.setId(channelModel.getId());
            dbPojo =  channelInfoServiceImpl.selectOne(dbPojo);

            ChannelPojo channelPojo = new ChannelPojo();
            BeanUtils.copyProperties(channelModel,channelPojo);
            //0=商品详情 1=商品三级分类列表 2=搜索页面 3=商品搜索详情页面 4=公告详情
            if(channelModel.getLinkMarkup()!=0){
                int number=channelModel.getChannelLink().indexOf("?");
                if(number!=-1){
                    channelPojo.setLinkParameter(channelModel.getChannelLink().substring(number+1));
                }else{
                    channelPojo.setLinkParameter("");
                }
            }else{
                channelPojo.setChannelLink("");
                channelPojo.setLinkParameter("");
            }
            //比较图片地址是否一致 不一致则需要将页面上传的图片信息 上传至oss
            if(!dbPojo.getChannelImage().equals(channelModel.getChannelImage())){
                //删除原图片
                //ossFileUpload.deleteFile(dbPojo.getChannelImage());

                String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                String backOssRootPath = sysPropertiesUtils.getStringValue("backOssRootPath");
                String channelImagePath = sysPropertiesUtils.getStringValue("channelImagePath");

                String ossPath = backOssRootPath+channelImagePath;
                String storeFilePath = fileUploadPath + ossPath;
                LoggerUtil.info(com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl.class, "sysFloorDetail update read local file "+storeFilePath+File.separatorChar+channelModel.getChannelImage());
                File file = FileUtil.loadFile(storeFilePath,channelModel.getChannelImage());
                //上传oss
                String key = ossFileUpload.fileUpload(file, ossPath);
                //删除本地文件
                if(key == null){
                    return 0;
                }
                channelPojo.setChannelImage(key);
                return this.updateByPrimaryKeySelective(channelPojo);
            }else{
                return this.updateByPrimaryKeySelective(channelPojo);
            }

        }catch(Exception e){
            LoggerUtil.error(com.yinhetianze.systemservice.mall.service.busi.impl.MallActivityBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException{
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            ChannelPojo channelPojo = new ChannelPojo();
            channelPojo.setId(Integer.parseInt(id));
            channelPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(channelPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }


}