package com.yinhetianze.business.message.model;

import com.yinhetianze.core.business.httprequest.BasicModel;

import java.util.List;

public class BusiMessageDetailListModel extends BasicModel{

    List<BusiMessageDetailModel> list;

    public List<BusiMessageDetailModel> getList() {
        return list;
    }

    public void setList(List<BusiMessageDetailModel> list) {
        this.list = list;
    }
}