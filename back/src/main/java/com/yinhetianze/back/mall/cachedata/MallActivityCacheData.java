package com.yinhetianze.back.mall.cachedata;

import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.systemservice.mall.service.info.MallActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallActivityCacheData extends RedisCacheData {

    @Autowired
    private MallActivityInfoService mallActivityInfoServiceImpl;

    @Override
    public List<MallActivityModel> cacheData() throws Exception {
        MallActivityModel mallActivityModel = new MallActivityModel();
        List<MallActivityModel>  list = mallActivityInfoServiceImpl.selectMallActivityList(mallActivityModel);
        return list;
    }
}
