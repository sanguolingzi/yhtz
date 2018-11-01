package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.List;

@Component
public class RefreshNoticeCacheExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CacheData<List<Map>> noticeCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        if(params!=null && params.length>0 && CommonUtil.isNotEmpty(params[0])){
            if(params[0].equals("refresh")){
                noticeCacheData.refreshCache();
            }
        }
        return "success";
    }

}
