package com.yinhetianze.business.message.service.info.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.message.mapper.info.MessageDetailInfoMapper;
import com.yinhetianze.business.message.model.BusiMessageCenterModel;
import com.yinhetianze.business.message.model.BusiMessageDetailModel;
import com.yinhetianze.business.message.model.BusiMessageDetailPageModel;
import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.business.message.service.info.MessageDetailInfoService;
import com.yinhetianze.business.message.service.info.MessageInfoService;
import com.yinhetianze.business.message.util.MessageDetailInfoEnum;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageDetailInfoServiceImpl implements MessageDetailInfoService
{
    @Autowired
    private MessageDetailInfoMapper busiMessageDetailInfoMapper;

    @Autowired
    private MessageInfoService messageInfoServiceImpl;

    @Autowired
    private MessageBusiService messageBusiServiceImpl;

    @Override
    public List<BusiMessageDetailModel> selecMessagetList(BusiMessageDetailPageModel busiMessageDetailPageModel) {
        return busiMessageDetailInfoMapper.selecMessagetList(busiMessageDetailPageModel);
    }

    @Override
    public PageInfo<BusiMessageDetailModel> selecMessagetPageInfo(BusiMessageDetailPageModel busiMessageDetailPageModel) throws BusinessException
    {
        BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
        busiMessagePojo.setId(busiMessageDetailPageModel.getMessageId());

        busiMessagePojo = messageInfoServiceImpl.selectOne(busiMessagePojo);

        if(busiMessagePojo!=null){
            if(busiMessageDetailPageModel.getmType() == 0){
                busiMessagePojo.setLogisticsTime(new Date());
                messageBusiServiceImpl.updateInfo(busiMessagePojo);
            }else if(busiMessageDetailPageModel.getmType() == 1){
                busiMessagePojo.setInfoTime(new Date());
                messageBusiServiceImpl.updateInfo(busiMessagePojo);
            }
        }

        PageHelper.startPage(busiMessageDetailPageModel.getCurrentPage(),busiMessageDetailPageModel.getPageSize());
        PageInfo<BusiMessageDetailModel> pageInfo = new PageInfo<BusiMessageDetailModel>(selecMessagetList(busiMessageDetailPageModel));
        return pageInfo;
    }

    @Override
    public Map<String,Object> selectMessageCenterInfo(BusiMessageCenterModel busiMessageCenterModel) throws BusinessException{
        Map<String,Object> m = new HashMap<String,Object>();
        BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
        busiMessagePojo.setId(busiMessageCenterModel.getMessageId());

        busiMessagePojo = messageInfoServiceImpl.selectOne(busiMessagePojo);

        //初始化查询参数
        BusiMessageDetailPageModel busiMessageDetailPageModel = new BusiMessageDetailPageModel();
        busiMessageDetailPageModel.setMessageId(busiMessageCenterModel.getMessageId());

        //通知消息
        BusiMessageCenterModel info = new BusiMessageCenterModel(busiMessageCenterModel.getMessageId(),
                MessageDetailInfoEnum.INFO.getValue(),MessageDetailInfoEnum.INFO.getDesc());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        busiMessageDetailPageModel.setmType(info.getmType());
        busiMessageDetailPageModel.setCreateTime(busiMessagePojo.getInfoTime());
        List<BusiMessageDetailModel> tempList = busiMessageDetailInfoMapper.selecMessagetList(busiMessageDetailPageModel);
        if(CommonUtil.isNotEmpty(tempList)){
            info.setMessageCount(tempList.size());
            BusiMessageDetailModel temp = tempList.get(0);
            info.setmTitle(temp.getmTitle());
            info.setCreateTime(sdf.format(temp.getCreateTime()));
        }
        m.put("notify",info);

        //物流通知
        BusiMessageCenterModel logistic = new BusiMessageCenterModel(busiMessageCenterModel.getMessageId(),
                MessageDetailInfoEnum.LOGISTIC.getValue(),MessageDetailInfoEnum.LOGISTIC.getDesc());

        busiMessageDetailPageModel.setmType(logistic.getmType());
        busiMessageDetailPageModel.setCreateTime(busiMessagePojo.getLogisticsTime());
        tempList = busiMessageDetailInfoMapper.selecMessagetList(busiMessageDetailPageModel);
        if(CommonUtil.isNotEmpty(tempList)){
            logistic.setMessageCount(tempList.size());
            BusiMessageDetailModel temp = tempList.get(0);
            logistic.setmTitle(temp.getmTitle());
            logistic.setCreateTime(sdf.format(temp.getCreateTime()));
        }
        m.put("logistic",logistic);

        //在线客服
        //BusiMessageCenterModel onlineService = new BusiMessageCenterModel(busiMessageCenterModel.getMessageId(),
        //        MessageDetailInfoEnum.ONLINESERVICE.getValue(),MessageDetailInfoEnum.ONLINESERVICE.getDesc());
        //list.add(onlineService);
        return m;
    }
}