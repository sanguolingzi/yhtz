package com.yinhetianze.back.mall.cachedata;

import com.yinhetianze.core.cachedata.RedisCacheData;
import com.yinhetianze.pojo.back.NoticePojo;
import com.yinhetianze.systemservice.mall.service.info.NoticeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoticeCacheData extends RedisCacheData {

    @Autowired
    private NoticeInfoService noticeInfoServiceImpl;

    @Override
    public List cacheData() throws Exception {
        NoticePojo noticePojo = new NoticePojo();
        noticePojo.setDelFlag((short)0);
        List<Map> list  = noticeInfoServiceImpl.selectForMobileIdex(noticePojo);
        return list;
    }
}
