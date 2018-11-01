package com.yinhetianze.business.message.service.busi;

import com.yinhetianze.business.message.model.BusiMessageDetailListModel;
import com.yinhetianze.business.message.model.BusiMessageDetailModel;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;

public interface MessageDetailBusiService
{
    int addInfo(BusiMessageDetailPojo busiMessageDetailPojo);

    int addListInfo(BusiMessageDetailListModel busiMessageDetailListModel);
}