package com.yinhetianze.systemservice.mall.service.info.impl;

import com.yinhetianze.systemservice.mall.mapper.info.NoticeInfoMapper;
import com.yinhetianze.systemservice.mall.service.info.NoticeInfoService;
import com.yinhetianze.pojo.back.NoticePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeInfoServiceImpl implements NoticeInfoService
{
    @Autowired
    private NoticeInfoMapper noticeInfoMapper;

    @Override
    public List selectList(NoticePojo noticePojo){
        return noticeInfoMapper.select(noticePojo);
    }

    @Override
    public List<Map> selectForMobileIdex(NoticePojo noticePojo) {

        List<NoticePojo> list = noticeInfoMapper.select(noticePojo);
        List<Map> returnList = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        for(NoticePojo pojo : list){
            Map m = new HashMap();
            m.put("id",pojo.getId());
            m.put("title",pojo.getTitle());
            m.put("createTime",pojo.getCreateTime().getTime());
            m.put("startTime",simpleDateFormat.format(pojo.getCreateTime()));
            m.put("content",pojo.getContent());
            returnList.add(m);
        }
        return returnList;
    }

    @Override
    public NoticePojo selectOne(NoticePojo noticePojo) {
        noticePojo.setDelFlag((short)0);
        return noticeInfoMapper.selectOne(noticePojo);
    }
}