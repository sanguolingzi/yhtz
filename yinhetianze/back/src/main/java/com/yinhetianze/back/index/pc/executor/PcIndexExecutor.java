package com.yinhetianze.back.index.pc.executor;

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
public class PcIndexExecutor  extends AbstractRestBusiExecutor {

    @Autowired
    private CacheData<Map<String, Object>> pcIndexCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        Map<String,Object> pcIndex = new HashMap<String,Object>();
        Map cacheData = pcIndexCacheData.getCacheData();
        if (CommonUtil.isNotEmpty(cacheData))
        {
            pcIndex.putAll(cacheData);
        }
        return pcIndex;
    }
}
