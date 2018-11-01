package com.yinhetianze.business.message.mapper.info;

import com.yinhetianze.business.message.model.BusiMessageDetailModel;
import com.yinhetianze.business.message.model.BusiMessageDetailPageModel;
import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;

import java.util.List;

public interface MessageDetailInfoMapper extends InfoMapper<BusiMessageDetailPojo> {

    List<BusiMessageDetailModel> selecMessagetList(BusiMessageDetailPageModel busiMessageDetailPageModel);
}