package com.yinhetianze.systemservice.system.model;

import com.yinhetianze.core.business.httprequest.BasicModel;


public class DeleteModel extends BasicModel {

    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}