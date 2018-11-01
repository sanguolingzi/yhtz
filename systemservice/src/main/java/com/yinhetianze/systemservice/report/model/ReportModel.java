package com.yinhetianze.systemservice.report.model;

import com.yinhetianze.core.business.httprequest.PageModel;

public class ReportModel extends PageModel{


    private String startDate;

    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
