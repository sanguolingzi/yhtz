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
import java.util.HashMap;
import java.util.Map;

@Component
public class MobileIndexExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private CacheData<Map<String, Object>> mobileIndexCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        Map<String,Object> mobileIndex = new HashMap<String,Object>();
        //猜你喜欢
        Map cacheData = mobileIndexCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(cacheData))
        {
            mobileIndex.putAll(cacheData);
        }

        return mobileIndex;
    }

}
