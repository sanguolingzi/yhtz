package com.yinhetianze.core.business.httpresponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果通用数据结构
 * @param <T>
 */
public class PageData<T>
{
    /**
     * 总共记录数
     */
    private Long totalRecord;

    /**
     * 分页数据
     */
    private List<T> listData;

    public PageData()
    {
        totalRecord = 0l;
        listData = new ArrayList<T>();
    }

    public PageData(List<T> listData)
    {
        this.listData = listData;
        this.totalRecord = -1l;
    }

    public PageData(List<T> listData, Long totalRecord)
    {
        this.listData = listData;
        this.totalRecord = totalRecord;
    }

    public Long getTotalRecord()
    {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord)
    {
        this.totalRecord = totalRecord;
    }

    public List<T> getListData()
    {
        return listData;
    }

    public void setListData(List<T> listData)
    {
        this.listData = listData;
    }
}
