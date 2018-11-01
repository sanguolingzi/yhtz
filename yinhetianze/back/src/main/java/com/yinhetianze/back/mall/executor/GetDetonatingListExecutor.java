package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.mall.model.DetonatingModel;
import com.yinhetianze.systemservice.mall.service.info.DetonatingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class GetDetonatingListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private CacheData<List<Map>> detonatingCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        List<Map> list=detonatingCacheData.getCacheData();
        DetonatingModel detonatingModel=(DetonatingModel)model;
        PageData pageData = new PageData();
        if(detonatingModel.getCurrentPage()!=0 ||  detonatingModel.getPageSize()!=0){
            int currentPage=detonatingModel.getCurrentPage();
            currentPage=currentPage<=0?1:currentPage;
            int pageSize=detonatingModel.getPageSize();
            int listSize = (list!=null)?list.size():0;
            if(listSize == 0){
                return pageData;
            }
            int startIndex = (currentPage-1)*pageSize;
            if(startIndex > listSize){
                return pageData;
            }
            int endIndex = currentPage*pageSize;
            if(listSize>endIndex){
                list=list.subList(startIndex,endIndex);
            }else{
                list=list.subList(startIndex,listSize);
            }
        }
        pageData.setListData(list);
        pageData.setTotalRecord(Long.parseLong((detonatingCacheData.getCacheData()!=null?detonatingCacheData.getCacheData().size():0)+""));
        return pageData;
    }
}
