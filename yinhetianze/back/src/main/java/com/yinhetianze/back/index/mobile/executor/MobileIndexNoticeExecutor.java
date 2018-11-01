package com.yinhetianze.back.index.mobile.executor;

import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * 查询商城公告
 */

@Component
public class MobileIndexNoticeExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CacheData<List> noticeCacheData;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        List list   = noticeCacheData.getCacheData();
        if(list != null
                && !list.isEmpty()){

            int length = list.size();

            if(length>5){
                list = list.subList(0,5);
            }else{
                list = list.subList(0,length);
            }
            PageData pageData = new PageData(list,Long.parseLong(list.size()+""));
            return pageData;
        }
        PageData pageData = new PageData();
        return pageData;
    }
}
