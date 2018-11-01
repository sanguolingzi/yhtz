package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.AdvertisementDetailPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementIndexModel;
import com.yinhetianze.systemservice.mall.model.AdvertisementModel;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementDetailInfoService;
import com.yinhetianze.systemservice.mall.service.info.AdvertisementInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetAdvertisementListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private AdvertisementDetailInfoService advertisementDetailInfoServiceImpl;

    @Autowired
    private AdvertisementInfoService advertisementInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        List<AdvertisementIndexModel> advertisementIndexModelList=advertisementInfoServiceImpl.selectAdvertisementList((AdvertisementModel)model);
        if(CommonUtil.isNotEmpty(advertisementIndexModelList)){
            for (int i=0;i<advertisementIndexModelList.size();i++){
                AdvertisementDetailPojo advertisementDetailPojo=new AdvertisementDetailPojo();
                advertisementDetailPojo.setAdvertisementId(advertisementIndexModelList.get(i).getId());
                List<Map>list=advertisementDetailInfoServiceImpl.selectAdvertisementDetailList(advertisementDetailPojo);
                if(CommonUtil.isNotEmpty(list)){
                    for (int k=0;k<list.size();k++){
                        if(list.get(k).get("linkParameter")!=null && list.get(k).get("linkParameter")!=""){
                            ((Map)list.get(k)).put("linkParameter",splitParameter(list.get(k).get("linkParameter").toString()));
                        }
                    }
                    advertisementIndexModelList.get(i).setAdvertisementDetail(list);
                }
            }
        }
        return advertisementIndexModelList;
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
