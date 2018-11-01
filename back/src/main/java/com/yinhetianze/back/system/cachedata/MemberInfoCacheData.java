package com.yinhetianze.back.system.cachedata;

import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.systemservice.system.service.info.SysMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberInfoCacheData extends RedisCacheData
{
    @Autowired
    private SysMemberInfoService sysMemberInfoServiceImpl;

    @Override
    public List<Map<String,Object>> cacheData() throws Exception
    {
        return sysMemberInfoServiceImpl.selectMemberInfo();
    }
}
