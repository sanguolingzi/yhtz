package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopModel;
import com.yinhetianze.business.shop.service.busi.ShopBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * 店铺 新增店铺访问量
 */

@Component
public class AddShopVisitCountExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopBusiService shopBusiServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopModel busiShopModel = (BusiShopModel)model;

        if(CommonUtil.isNotEmpty(busiShopModel.getId())){
            String key = getIp(request)+"_"+busiShopModel.getId()+"_addShopVisit";
            //当前从0点点开始的分钟数
            //若跨天 可能存在 minute_of_day 结果小于 redis中值的情况
            //目前规则 同一个sessionid 对同一个店铺id  5分钟内只有一次有效访问
            long minute_of_day = LocalDateTime.now().getLong(ChronoField.MINUTE_OF_DAY);
            boolean result = redisManager.getRedisTemplate().opsForValue().setIfAbsent(key,minute_of_day);
            if(result){
                shopBusiServiceImpl.addShopVisitCount(busiShopModel.getId());
                redisManager.getRedisTemplate().opsForValue().set(key,minute_of_day,5, TimeUnit.MINUTES.MINUTES);
            }else{
                long data = (Long)redisManager.getRedisTemplate().opsForValue().get(key);
                if((minute_of_day-data)>5 || (minute_of_day<data)){
                    shopBusiServiceImpl.addShopVisitCount(busiShopModel.getId());
                    redisManager.getRedisTemplate().opsForValue().set(key,minute_of_day,5, TimeUnit.MINUTES.MINUTES);
                }
            }
        }
        return "success";
    }

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
