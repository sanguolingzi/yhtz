package com.yinhetianze.systemservice.mall.mapper.info;

import com.yinhetianze.systemservice.mall.model.NoticeModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.back.NoticePojo;

import java.util.List;

public interface NoticeInfoMapper extends InfoMapper<NoticePojo> {

    List<NoticeModel> selectForCustomerMessage(NoticeModel noticeModel);
}