package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiWechatModel;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 获取微信授权url
 */

@Component
public class GetWechatLoginUrlExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiWechatModel busiWechatModel =  (BusiWechatModel)model;

        String _redirect_url = busiWechatModel.getRedirectUrl();
        // 添加_redirect_url不为空时进行urldecode操作
        if (CommonUtil.isNotEmpty(_redirect_url))
        {
            try
            {
                _redirect_url = URLDecoder.decode(_redirect_url, CommonConstant.CHARSET_UTF8);
            }
            catch (Exception e)
            {
                LoggerUtil.error(GetWechatLoginUrlExecutor.class, e);
            }
        }

        Short idType = busiWechatModel.getIdType();
        String appId="";
        String authUrl = sysPropertiesUtils.getStringValue("authUrl");
        if(idType == 1){
             appId = sysPropertiesUtils.getStringValue("appId");

        }else if(idType == 2){
             appId = sysPropertiesUtils.getStringValue("openAppId");
        }


        StringBuilder sb = new StringBuilder(authUrl);
        String redirect_url = _redirect_url;
        if(idType == 1 && CommonUtil.isEmpty(_redirect_url)){
            redirect_url = sysPropertiesUtils.getStringValue("wapUrl");
        }
        sb.append("?appid="+appId)
                .append("&redirect_uri=").append( URLEncoder.encode(redirect_url))
                .append("&response_type=code")
                .append("&scope=snsapi_userinfo")
                .append("&state=STATE")
                .append("&connect_redirect=1#wechat_redirect");
        return sb.toString();
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {

        BusiWechatModel busiWechatModel =  (BusiWechatModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiWechatModel.getIdType())){
            errorMessage.rejectNull("idType",null,"idType");
            return errorMessage;
        }else if(busiWechatModel.getIdType() != 1 && busiWechatModel.getIdType() != 2){
            errorMessage.rejectErrorMessage("idType","idType数据异常","idType数据异常");
            return errorMessage;
        }
        return null;
    }
}
