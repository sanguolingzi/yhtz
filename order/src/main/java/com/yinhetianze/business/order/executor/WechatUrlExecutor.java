package com.yinhetianze.business.order.executor;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Service
public class WechatUrlExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        String backUrl=sysPropertiesUtils.getStringValue("authUrl");
        try {
            String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + sysPropertiesUtils.getStringValue("appId")+
                    "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                    "&response_type=code" +
                    "&scope=snsapi_base" +
                    "&state=#wechat_redirect";
            return url;
        }catch (Exception e){
            LoggerUtil.error(WechatUrlExecutor.class,"获取授权地址异常! :"+e.toString());
            throw new BusinessException("获取授权地址失败");
        }
    }
}
