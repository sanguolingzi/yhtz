package com.yinhetianze.back.index.mobile.cachedata;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import com.yinhetianze.core.cachedata.EhCacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.pojo.back.SysFloorPojo;
import com.yinhetianze.systemservice.mall.model.*;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorDetailInfoService;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorInfoService;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import com.yinhetianze.systemservice.mall.service.info.SysFloorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobileFloorCacheData extends EhCacheData<Map<String,Object>> {

    @Autowired
    private SysFloorDetailInfoService sysFloorDetailInfoServiceImpl;

    @Autowired
    private SysFloorInfoService sysFloorInfoServiceImpl;


    @Autowired
    private MobileFloorDetailInfoService mobileFloorDetailInfoServiceImpl;

    @Autowired
    private MobileFloorInfoService mobileFloorInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    public Map<String,Object> cacheData() throws Exception
    {
        Map<String,Object> mobileIndexFloor = new HashMap<String,Object>();
        List floorMobileList=new ArrayList();
        SysFloorPojo sysFloorPojo=new SysFloorPojo();
        sysFloorPojo.setIsShow((short)0);
        sysFloorPojo.setFloorType((short)0);
        sysFloorPojo.setDelFlag((short)0);
        List<SysFloorPojo> floorlist=sysFloorInfoServiceImpl.selectFloorList();
        SysFloorPojo floorPojo;
        SysFloorDetailModel sysFloorDetailModel;
        FloorMobileIndexModel floorMobileIndexModel;
        for (int i=0;i<floorlist.size();i++){
            floorPojo=new SysFloorPojo();
            floorPojo.setId(floorlist.get(i).getId());
            floorMobileIndexModel=sysFloorInfoServiceImpl.selectFloorOne(floorPojo);
            if(floorMobileIndexModel.getLinkParameter()!=null && floorMobileIndexModel.getLinkParameter()!=""){
                floorMobileIndexModel.setLinkParameter(splitParameter(floorMobileIndexModel.getLinkParameter()));
            }
            sysFloorDetailModel=new SysFloorDetailModel();
            sysFloorDetailModel.setFloorId(floorPojo.getId());
            sysFloorDetailModel.setIsAll(6);
            floorMobileIndexModel.setFloorDetail(sysFloorDetailInfoServiceImpl.selectForMobileIndex(sysFloorDetailModel));
            floorMobileList.add(floorMobileIndexModel);
        }
        mobileIndexFloor.put("floorMobileList",floorMobileList);
        MobileFloorPojo mobileFloorPojo =new MobileFloorPojo();
        List mobileFloorList=new ArrayList();
        mobileFloorPojo.setDelFlag((short)0);
        mobileFloorPojo.setIsShow((short)0);
        MobileFloorPojo mfPojo;
        MobileFloorIndexModel mobileFloorIndexModel;
        MobileFloorDetailPojo mobileFloorDetailPojo;
        List<MobileFloorPojo> mobileFloorPojoList=mobileFloorInfoServiceImpl.selectList(mobileFloorPojo);
        for (int i=0;i<mobileFloorPojoList.size();i++){
            mfPojo=new MobileFloorPojo();
            mfPojo.setId(mobileFloorPojoList.get(i).getId());
            mobileFloorIndexModel=mobileFloorInfoServiceImpl.selectMobileFloorOne(mfPojo);
            if(mobileFloorIndexModel.getLinkParameter()!=null && mobileFloorIndexModel.getLinkParameter()!=""){
                mobileFloorIndexModel.setLinkParameter(splitParameter(mobileFloorIndexModel.getLinkParameter()));
            }
            mobileFloorDetailPojo=new MobileFloorDetailPojo();
            mobileFloorDetailPojo.setMobileFloorId(mobileFloorIndexModel.getId());
            List<Map>list=mobileFloorDetailInfoServiceImpl.selectMobileFloorIndex(mobileFloorDetailPojo);
            for (int k=0;k<list.size();k++){
                if(list.get(k).get("mobileFloorDetailLinkParameter")!=null && list.get(k).get("mobileFloorDetailLinkParameter")!=""){
                    ((Map)list.get(k)).put("mobileFloorDetailLinkParameter",splitParameter(list.get(k).get("mobileFloorDetailLinkParameter").toString()));
                }
            }

            mobileFloorIndexModel.setMobileFloor(list);
            mobileFloorList.add(mobileFloorIndexModel);
        }
        mobileIndexFloor.put("mobileFloorList",mobileFloorList);

        if (CommonUtil.isNotEmpty(mobileIndexFloor))
        {
            ResponseData responseData = new ResponseData();
            responseData.setData(mobileIndexFloor);
            responseData.setResultInfo(new Result());
            // 将缓存数据推送到oss
            try
            {

                String fileName = sysPropertiesUtils.getStringValue("mobileHomeDataFloorName");
                ByteArrayInputStream bais = new ByteArrayInputStream(CommonUtil.objectToJsonString(responseData).getBytes("utf-8"));
                //ossFileUpload.fileUpload(bais, "json", "mobile_home_page_data.json");
                if(fileName == null || fileName.isEmpty()){
                    throw new Exception("mobileHomeDataFloorName 配置有误!  fileName:"+fileName);
                }
                Map<String,Object> metaMap = new HashMap<>();
                metaMap.put("contentType","text/plain");
                ossFileUpload.fileUpload(bais, "json", fileName,metaMap);
            }
            catch (Exception e)
            {
                LoggerUtil.error(MobileFloorCacheData.class, e);
                throw new Exception();
            }
        }

        return mobileIndexFloor;
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
