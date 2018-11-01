package com.yinhetianze.back.system.executor;

import com.yinhetianze.back.system.cachedata.ProtocolCacheData;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 刷新协议配置
 */
@Service
public class RefreshProtocolExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private ProtocolCacheData protocolCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> map = protocolCacheData.refreshCache();
        return "success";
    }
}
