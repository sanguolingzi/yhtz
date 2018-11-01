package com.yinhetianze.back.mall.cachedata;

import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.systemservice.mall.service.info.DetonatingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class DetonatingCacheData extends RedisCacheData {

    @Autowired
    private DetonatingInfoService detonatingInfoServiceImpl;

    @Override
    public List cacheData() throws Exception{
        List<Map>list=detonatingInfoServiceImpl.selectDetonatingList();
        return list;
    }
}
