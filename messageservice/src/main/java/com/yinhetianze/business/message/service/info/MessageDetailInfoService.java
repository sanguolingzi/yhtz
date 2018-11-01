package com.yinhetianze.business.message.service.info;

import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.message.model.BusiMessageCenterModel;
import com.yinhetianze.business.message.model.BusiMessageDetailModel;
import com.yinhetianze.business.message.model.BusiMessageDetailPageModel;
import com.yinhetianze.core.business.BusinessException;

import java.util.List;
import java.util.Map;

public interface MessageDetailInfoService
{
    List<BusiMessageDetailModel> selecMessagetList(BusiMessageDetailPageModel busiMessageDetailPageModel);

    Map<String,Object> selectMessageCenterInfo(BusiMessageCenterModel busiMessageCenterModel) throws BusinessException;

    /**
     * 根据消息类型 查询某一类型的消息列表   通知公告/通知活动 由于是系统后台配置 需要调用远程服务
     * @param busiMessageDetailPageModel
     * @return
     */
    PageInfo<BusiMessageDetailModel> selecMessagetPageInfo(BusiMessageDetailPageModel busiMessageDetailPageModel)
    throws BusinessException;

}