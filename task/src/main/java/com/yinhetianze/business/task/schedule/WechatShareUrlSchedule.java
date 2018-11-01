package com.yinhetianze.business.task.schedule;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信分享刷新
 */
public class WechatShareUrlSchedule extends InterruptableJob{

    /**
     * 定时任务启动时候  sysPropertiesUtils 还未被spring容器加载 故 定时任务时间太频繁  刚开始的几次 sysPropertiesUtils 为null
     */
    private static SysPropertiesUtils sysPropertiesUtils;

    public WechatShareUrlSchedule(){
        if(sysPropertiesUtils == null){
            SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
            if(sysPropertiesUtils != null)
                this.sysPropertiesUtils = sysPropertiesUtils;
        }


    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{

        try{
            //微信获取token
            String tokenUrl=sysProperties("tokenUrl");
            //微信获取token
            String ticketUrl=sysProperties("ticketUrl");
            //获取redis对象
            RedisManager redisManager= (RedisManager)ApplicationContextFactory.getBean("getRedisManager");
            //redisManager.deleteValue("accessTokenObj");
            /*Map resultMap = new HashMap();
            Integer expiresIn=1800;
            resultMap.put("access_token","12_KoIrV9ISAdzjcq1U6Oxu7lWcNRZptuOeP5hljmBHXzXuCdB-yAbuuvaEPk6GQMTika4attzMHripLCVd5nG1kMM3fWCaA7HnCjZOnjfD5RnfN40AcZtUFV49Xw2VJETnf8SpgxEyZmoGXnfZWJSiAFAQDM");
            resultMap.put("expires_in",expiresIn);
            resultMap.put("ticket","HoagFKDcsGMVCIY2vOjf9p40_3bpIn9ac4weQhCyBD7bEVdzNu1abO2L3XU5b9xXomRm6xe-Fx-DefwARyl8hw");
            redisManager.setValue("accessTokenObj",resultMap,Long.valueOf(expiresIn*1000));*/
            Map accessTokenObj = (Map) redisManager.getValue("accessTokenObj");
            Long accessTokenTime= redisManager.getRedisTemplate().getExpire("accessTokenObj", TimeUnit.SECONDS);
            if(accessTokenObj == null || accessTokenTime<=1800){
                //获取appId
                String appId=sysProperties("appId");
                //公众号 APP_SECRET
                String appSecret=sysProperties("appSecret");
                wechatShare(appId,appSecret,tokenUrl,ticketUrl,redisManager,"accessTokenObj");
                LoggerUtil.info(WechatShareUrlSchedule.class,"refresh h5 accessToken");
            }

            Map appAccessTokenObj = (Map) redisManager.getValue("appAccessTokenObj");
            Long appAccessTokenTime= redisManager.getRedisTemplate().getExpire("appAccessTokenObj", TimeUnit.SECONDS);
            if(appAccessTokenObj == null || appAccessTokenTime<=1800){
                String openAppId=sysProperties("openAppId");
                String openAppSecret=sysProperties("openAppSecret");
                wechatShare(openAppId,openAppSecret,tokenUrl,ticketUrl,redisManager,"appAccessTokenObj");
                LoggerUtil.info(WechatShareUrlSchedule.class,"refresh app accessToken");
            }
            LoggerUtil.info(WechatShareUrlSchedule.class,"end task to excute accessToken");


        }catch(Exception e){
            LoggerUtil.error(WechatShareUrlSchedule.class,e.getMessage());
        }

    }

    public String sysProperties(String properties) throws BusinessException{
        if(sysPropertiesUtils != null){
            return sysPropertiesUtils.getStringValue(properties);
        }
        throw new BusinessException(" sysPropertiesUtils is not init well ");



        //获取系统参数信息
        //SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");

        /*
        BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        busiSysSyspropertiesPojo.setpName(properties);
        BusiSysSyspropertiesPojo sysSysproperties=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo);
        if(CommonUtil.isEmpty(sysSysproperties)){
            LoggerUtil.error(WechatShareUrlSchedule.class,"配置文件获取失败");
            throw new BusinessException("配置文件获取失败");
        }
        return sysSysproperties.getpValue();
        */
    }

    public void wechatShare(String appId, String appSecret, String tokenUrl, String ticketUrl,RedisManager redisManager,String objKey){
        try{

            StringBuilder sb = new StringBuilder(tokenUrl);
            sb.append("&appid="+appId)
                    .append("&secret=").append(appSecret);
            //获取accessToken
            Map accessTokenResponse = HttpClientUtil.doGetRequest(sb.toString(),null,null);
            //result:{200={"errcode":40164,"errmsg":"invalid ip 113.246.152.212, not in whitelist hint: [uUqCLA06843612]"}}
            //key:{"access_token":"11_kMOCBbp0ohJ7hPLdV2qF5sj0CKiEZ8SSozTVpXHO3pLdxqrE9v70oxcQ1VRU-tpTs-cWerRknOOw-2F70m5GAvcS_uUol6teZ6nc-9gHfzop6nGobh8_Lq2LiECXB8Ca3ufvzBpvdRl20rUzUTSjAHAAZY","expires_in":7198}
            LoggerUtil.info(WechatShareUrlSchedule.class,"accessTokenResponse:"+accessTokenResponse);
            Object key = accessTokenResponse.keySet().iterator().next();
            Object result = accessTokenResponse.get(key);
            if(result!= null){
                if(result.toString().contains("errcode")){
                    LoggerUtil.error(WechatShareUrlSchedule.class,result.toString());
                }else{
                    //access_token map
                    Map<String,Object> resultMap =CommonUtil.readFromString(result.toString(),Map.class);

                    String url = ticketUrl+"&access_token=" + resultMap.get("access_token").toString();
                    Map ticketResponse = HttpClientUtil.doGetRequest(url,null,null);
                    LoggerUtil.info(WechatShareUrlSchedule.class,"ticketResponse:"+ticketResponse);
                    //getticket{200={"errcode":0,"errmsg":"ok","ticket":"HoagFKDcsGMVCIY2vOjf9p40_3bpIn9ac4weQhCyBD4w6pGlv-wXkXx8kGVYYdbtzzO0cS__7eKk2x-jSgXB_w","expires_in":7200}}
                    Object tKey = ticketResponse.keySet().iterator().next();
                    Object tResult = ticketResponse.get(tKey);

                    //ticket map
                    Map<String,Object> tresultMap =CommonUtil.readFromString(tResult.toString(),Map.class);
                    String errcode = tresultMap.get("errcode").toString();
                    if(errcode.equals("0")){
                        resultMap.put("ticket",tresultMap.get("ticket").toString());

                        //获取时间 单位s
                        Integer expiresIn = Integer.parseInt(resultMap.get("expires_in").toString());

                        redisManager.setValue(objKey,resultMap,Long.valueOf(expiresIn*1000));
                        redisManager.getRedisTemplate().expire(objKey,expiresIn,TimeUnit.SECONDS);
                    }else{
                        LoggerUtil.error(WechatShareUrlSchedule.class,tKey.toString());
                    }
                }
            }
        }catch (Exception e){
            LoggerUtil.error(WechatShareUrlSchedule.class,e.getMessage());
        }

    }
}


