package com.yinhetianze.back.index.pc.cachedata;

import com.yinhetianze.back.index.mobile.cachedata.MobileIndexCacheData;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.cachedata.EhCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.SysFloorPojo;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.service.info.SysBannerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PcIndexCacheData extends EhCacheData<Map<String,Object>> {

    @Autowired
    private SysBannerInfoService sysBannerInfoServiceImpl;

    @Autowired
    private SysFloorDetailInfoService sysFloorDetailInfoServiceImpl;

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    public Map<String,Object>cacheData() throws Exception{
        Map<String,Object> mobileIndex = new HashMap<String,Object>();
        //banner 轮播图
        SysBannerModel sysBannerModel=new SysBannerModel();
        sysBannerModel.setBannerType((short)1);
        List<Map> pcbanner = sysBannerInfoServiceImpl.selectForMobileIndex(sysBannerModel);
        mobileIndex.put("pcbanner",pcbanner);

        //新款推荐
        SysFloorPojo pcnewsPojo=new SysFloorPojo();
        SysFloorDetailModel sysFloorDetailModel = new SysFloorDetailModel();
        int pcnewsId = 24;
        sysFloorDetailModel.setFloorId(pcnewsId);
        sysFloorDetailModel.setIsAll(4);
        List<Map> pcnews = sysFloorDetailInfoServiceImpl.selectForPcIndex(sysFloorDetailModel);
        pcnewsPojo.setId(pcnewsId);
        pcnewsPojo=sysFloorInfoServiceImpl.selectOne(pcnewsPojo);
        mobileIndex.put("pcnewsId",pcnewsId);
        mobileIndex.put("pcnewsName",pcnewsPojo.getFloorName());
        mobileIndex.put("pcnewsUri",pcnewsPojo.getPhotoUrl());
        mobileIndex.put("pcnews",pcnews);


        //积分赠送
        Map<String,Object> pcintegral=new HashMap<String,Object>();
        SysFloorPojo pcintegralPojo=new SysFloorPojo();
        int pcintegralId = 25;
        pcintegralPojo.setId(pcintegralId);
        pcintegralPojo=sysFloorInfoServiceImpl.selectOne(pcintegralPojo);
        sysFloorDetailModel.setFloorId(pcintegralId);
        sysFloorDetailModel.setIsAll(7);
        List<Map> pcintegrals = sysFloorDetailInfoServiceImpl.selectForPcIndex(sysFloorDetailModel);
        List<Map> tfintegral=new ArrayList<Map>();
        List<Map> ptintegral=new ArrayList<Map>();
        for(int i=0;i<pcintegrals.size();i++){
            if(i<4){
                tfintegral.add(pcintegrals.get(i));
            }else{
                ptintegral.add(pcintegrals.get(i));
            }
        }
        pcintegral.put("tfintegral",tfintegral);
        pcintegral.put("ptintegral",ptintegral);
        mobileIndex.put("pcintegralId",pcintegralId);
        mobileIndex.put("pcintegralName",pcintegralPojo.getFloorName());
        mobileIndex.put("pcintegraUri",pcintegralPojo.getPhotoUrl());
        mobileIndex.put("pcintegral",pcintegral);


        //官方自营
        Map<String,Object> pcofficial=new HashMap<String,Object>();
        SysFloorPojo pcoffPojo=new SysFloorPojo();
        int pcofficialId =22;
        pcoffPojo.setId(pcofficialId);
        pcoffPojo=sysFloorInfoServiceImpl.selectOne(pcoffPojo);
        sysFloorDetailModel.setFloorId(pcofficialId);
        sysFloorDetailModel.setIsAll(10);
        List<Map> pcofficials = sysFloorDetailInfoServiceImpl.selectForPcIndex(sysFloorDetailModel);
        List<Map> picials=new ArrayList<Map>();
        List<Map> pcoff=new ArrayList<Map>();
       for(int i=0;i<pcofficials.size();i++){
           if(i<6){
               picials.add(pcofficials.get(i));
           }else{
               pcoff.add(pcofficials.get(i));
           }
       }
        pcofficial.put("pcoff",pcoff);
        pcofficial.put("picials",picials);
        mobileIndex.put("pcofficialId",pcofficialId);
        mobileIndex.put("pcofficialName",pcoffPojo.getFloorName());
        mobileIndex.put("pcofficialUri",pcoffPojo.getPhotoUrl());
        mobileIndex.put("pcofficial",pcofficial);


        //爆款热销
        Map<String,Object> pchotSell=new HashMap<String,Object>();
        SysFloorPojo pchotPojo=new SysFloorPojo();
        int pchotSellId = 23;
        pchotPojo.setId(pchotSellId);
        pchotPojo=sysFloorInfoServiceImpl.selectOne(pchotPojo);
        sysFloorDetailModel.setFloorId(pchotSellId);
        sysFloorDetailModel.setIsAll(6);
        List<Map> pchotSells = sysFloorDetailInfoServiceImpl.selectForPcIndex(sysFloorDetailModel);
        List<Map> Sell=new ArrayList<Map>();
        List<Map> pSells=new ArrayList<Map>();
        for(int i=0;i<pchotSells.size();i++){
            if(i<3){
                Sell.add(pchotSells.get(i));
            }else{
                pSells.add(pchotSells.get(i));
            }
        }
        pchotSell.put("Sell",Sell);
        pchotSell.put("pSells",pSells);
        mobileIndex.put("pchotSellId",pchotSellId);
        mobileIndex.put("pchotName",pchotPojo.getFloorName());
        mobileIndex.put("pchotUri",pchotPojo.getPhotoUrl());
        mobileIndex.put("pchotSell",pchotSell);


        if (CommonUtil.isNotEmpty(mobileIndex))
        {
            ResponseData responseData = new ResponseData();
            responseData.setData(mobileIndex);
            responseData.setResultInfo(new Result());

            // 将缓存数据推送到oss
            try
            {
                String fileName = sysPropertiesUtils.getStringValue("pcHomeDataName");
                ByteArrayInputStream bais = new ByteArrayInputStream(CommonUtil.objectToJsonString(responseData).getBytes("utf-8"));
                if(fileName == null || fileName.isEmpty()){
                    throw new Exception("pcHomeDataName 配置有误!  fileName:"+fileName);
                }
                Map<String,Object> metaMap = new HashMap<>();
                metaMap.put("contentType","text/plain");
                ossFileUpload.fileUpload(bais, "json", fileName,metaMap);
                //ossFileUpload.fileUpload(bais, "json", "pc_home_page_data.json");
            }
            catch (Exception e)
            {
                LoggerUtil.error(MobileIndexCacheData.class, e);
                throw new Exception();
            }
        }
        return mobileIndex;
    }
}
