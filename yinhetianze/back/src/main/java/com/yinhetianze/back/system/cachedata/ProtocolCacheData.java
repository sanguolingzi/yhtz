package com.yinhetianze.back.system.cachedata;

import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.core.cachedata.EhCacheData;
import com.yinhetianze.pojo.back.ProtocolPojo;
import com.yinhetianze.systemservice.system.service.info.ProtocolInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProtocolCacheData extends EhCacheData<Map<String,Object>>
{
    @Autowired
    private ProtocolInfoService protocolInfoServiceImpl;

    @Override
    public Map<String,Object> cacheData() throws Exception
    {
        Map<String,Object> protocolMap = new HashMap<String,Object>();

        ProtocolPojo protocolPojo = new ProtocolPojo();
        protocolPojo.setDelFlag((short)0);
        protocolPojo.setIsShow((short)1);
        List<ProtocolPojo> list = protocolInfoServiceImpl.selectList(protocolPojo);

        for(ProtocolPojo temp : list){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",temp.getType());//协议类型
            jsonObject.put("content",temp.getProtocolContent());
            jsonObject.put("createTime",temp.getCreateTime());
            protocolMap.put(temp.getProtocolTitle(),jsonObject);
        }
        return protocolMap;
    }
}
