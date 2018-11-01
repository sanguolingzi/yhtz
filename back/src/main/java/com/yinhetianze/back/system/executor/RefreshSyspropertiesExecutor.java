package com.yinhetianze.back.system.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesCacheData;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 刷新系统缓存
 */

@Component
public class RefreshSyspropertiesExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private SysPropertiesCacheData sysPropertiesCacheData;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        sysPropertiesCacheData.refreshCache();
        return "success";
    }
}
