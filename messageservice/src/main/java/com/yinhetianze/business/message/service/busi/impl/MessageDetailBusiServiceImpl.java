package com.yinhetianze.business.message.service.busi.impl;

import com.yinhetianze.business.message.mapper.busi.MessageDetailBusiMapper;
import com.yinhetianze.business.message.model.BusiMessageDetailListModel;
import com.yinhetianze.business.message.model.BusiMessageDetailModel;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageDetailBusiServiceImpl implements MessageDetailBusiService
{
    @Autowired
    private MessageDetailBusiMapper busiMessageDetailMapper;

    @Override
    public int addInfo(BusiMessageDetailPojo busiMessageDetailPojo) {
        return busiMessageDetailMapper.insertSelective(busiMessageDetailPojo);
    }

    @Override
    public int addListInfo(BusiMessageDetailListModel busiMessageDetailListModel) {
        for(BusiMessageDetailModel busiMessageDetailModel:busiMessageDetailListModel.getList()){
            BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
            BeanUtils.copyProperties(busiMessageDetailModel,busiMessageDetailPojo);
            addInfo(busiMessageDetailPojo);
        }
        return 1;
    }
}