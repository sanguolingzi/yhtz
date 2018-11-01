package com.yinhetianze.back.index.mobile.cachedata;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.pojo.back.SysFloorPojo;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.info.ChannelInfoService;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.service.info.SysBannerInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.cachedata.EhCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobileIndexCacheData extends EhCacheData<Map<String,Object>>
{
    @Autowired
    private SysBannerInfoService sysBannerInfoServiceImpl;

    @Autowired
    private ChannelInfoService channelInfoServiceImpl;


    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private CacheData<List> noticeCacheData;

    @Override
    public Map<String,Object> cacheData() throws Exception
    {
        Map<String,Object> mobileIndex = new HashMap<String,Object>();

        //banner 轮播图
        SysBannerModel sysBannerModel=new SysBannerModel();
        sysBannerModel.setBannerType((short)0);
        List<Map> banner = sysBannerInfoServiceImpl.selectForMobileIndex(sysBannerModel);
        for(int i=0;i<banner.size();i++){
            if(banner.get(i).get("linkParameter")!=null && banner.get(i).get("linkParameter")!=""){
                ((Map)banner.get(i)).put("linkParameter",splitParameter(banner.get(i).get("linkParameter").toString()));
            }
        }
        mobileIndex.put("banner",banner);


        //频道
        List<Map> channel = channelInfoServiceImpl.selectForMobileIndex(null);
        for(int i=0;i<channel.size();i++){
            if(channel.get(i).get("linkParameter")!=null && channel.get(i).get("linkParameter")!=""){
                ((Map)channel.get(i)).put("linkParameter",splitParameter(channel.get(i).get("linkParameter").toString()));
            }
        }
        mobileIndex.put("channel",channel);

        List list   = noticeCacheData.getCacheData();
        if(list != null && !list.isEmpty()){
            int length = list.size();
            if(length>5){
                list = list.subList(0,5);
            }else{
                list = list.subList(0,length);
            }
            mobileIndex.put("notice",list);
        }

        if (CommonUtil.isNotEmpty(mobileIndex))
        {
            ResponseData responseData = new ResponseData();
            responseData.setData(mobileIndex);
            responseData.setResultInfo(new Result());

            // 将缓存数据推送到oss
            try
            {

                String fileName = sysPropertiesUtils.getStringValue("mobileHomeDataName");
                ByteArrayInputStream bais = new ByteArrayInputStream(CommonUtil.objectToJsonString(responseData).getBytes("utf-8"));
                //ossFileUpload.fileUpload(bais, "json", "mobile_home_page_data.json");
                if(fileName == null || fileName.isEmpty()){
                    throw new Exception("mobileHomeDataName 配置有误!  fileName:"+fileName);
                }


                Map<String,Object> metaMap = new HashMap<>();
                metaMap.put("contentType","text/plain");
                ossFileUpload.fileUpload(bais, "json", fileName,metaMap);
            }
            catch (Exception e)
            {
                LoggerUtil.error(MobileIndexCacheData.class, e);
                throw new Exception();
            }
        }

        return mobileIndex;
    }

    private    String  splitParameter(String parameter){
        List<String> list=new ArrayList<String>();
        int number=0;
        for(int i=0;i<parameter.length();i++){
            number=parameter.indexOf("&");
            if(number==-1){
                list.add(parameter);
                break;
            }else{
                list.add(parameter.substring(0,number));
                parameter=parameter.substring(number+1);
            }
        }
        String linkParameter="";
        for (int k=0;k<list.size();k++){
            if(k==(list.size()-1)){
                linkParameter=linkParameter+list.get(k).substring(list.get(k).indexOf("=")+1);
            }else{
                linkParameter=linkParameter+list.get(k).substring(list.get(k).indexOf("=")+1)+",";
            }
        }
        return linkParameter;
    }
}
