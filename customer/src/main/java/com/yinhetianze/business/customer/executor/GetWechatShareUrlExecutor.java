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
import com.yinhetianze.core.utils.RedisManager;
import me.chanjar.weixin.common.util.RandomUtils;
import me.chanjar.weixin.common.util.crypto.SHA1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取微信授权url
 */

@Component
public class GetWechatShareUrlExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiWechatModel busiWechatModel  = (BusiWechatModel)model;
        try{
            Map<String,Object> returnMap = new HashMap<>();
            String ticket = null;
            String appId = null;
            if(busiWechatModel.getIdType() == 1){
                Object accessTokenObj = redisManager.getValue("accessTokenObj");
                if(accessTokenObj == null)
                    return returnMap;

                appId = sysPropertiesUtils.getStringValue("appId");
                ticket = ((Map)accessTokenObj).get("ticket").toString();
            }else if(busiWechatModel.getIdType() == 2){

                Object appAccessTokenObj = redisManager.getValue("appAccessTokenObj");
                if(appAccessTokenObj == null)
                    return returnMap;

                appId = sysPropertiesUtils.getStringValue("openAppId");
                ticket = ((Map)appAccessTokenObj).get("ticket").toString();
            }

            long timestamp = System.currentTimeMillis() / 1000L;
            String noncestr = RandomUtils.getRandomStr();
            String url=busiWechatModel.getUrl();
            if(CommonUtil.isNotEmpty(url)){
                try
                    {
                        url = URLDecoder.decode(url, CommonConstant.CHARSET_UTF8);
                    }
                catch (Exception e)
                    {
                        LoggerUtil.error(GetWechatShareUrlExecutor.class, e);
                    }
            }
            String signature = SHA1.genWithAmple(new String[]{"jsapi_ticket=" + ticket, "noncestr=" + noncestr, "timestamp=" + timestamp, "url=" + url});

            returnMap.put("appId",appId);
            returnMap.put("nonceStr",noncestr);
            returnMap.put("timestamp",timestamp);
            returnMap.put("signature",signature);
            return returnMap;
        }catch(Exception e){
            LoggerUtil.error(GetWechatShareUrlExecutor.class,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiWechatModel busiWechatModel  = (BusiWechatModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiWechatModel.getUrl())){
            errorMessage.rejectNull("url",null,"url");
            return errorMessage;
        }

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
