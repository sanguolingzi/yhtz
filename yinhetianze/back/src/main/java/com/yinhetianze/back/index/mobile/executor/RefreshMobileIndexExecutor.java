package com.yinhetianze.back.index.mobile.executor;

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

@Component
public class RefreshMobileIndexExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CacheData<Map<String, Object>> mobileIndexCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        if(params!=null && params.length>0 && CommonUtil.isNotEmpty(params[0])){
            if(params[0].equals("refresh")){
                mobileIndexCacheData.refreshCache();
            }
        }
        return "success";
    }

}
