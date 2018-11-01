package com.yinhetianze.security.custom.userdetails.impl;

import com.yinhetianze.core.utils.*;
import com.yinhetianze.security.custom.userdetails.UserDetails;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * 用户详情信息操作接口
 * 用户详情信息通过redis管理
 */
public class RedisUserDetailsService implements UserDetailsService
{
    private RedisManager redisManager;

    // 改为配置
    @Value("${security.timeout:30}")
    private Long sessionTimeout;

    public RedisUserDetailsService(RedisManager redisManager)
    {
        this.redisManager = redisManager;
    }

    @Override
    public UserDetails getUserDetails(String userId)
    {
        UserDetails userDetails = redisManager.getSerializeObject(userId, UserDetails.class);
        return userDetails;
    }

    @Override
    public void saveUserDetails(UserDetails userDetails)
    {
        if (CommonUtil.isNotEmpty(userDetails))
        {
//            redisManager.setSerializeObject(userDetails.getUserId(), userDetails, RedisManager.SESSION_CACHE_TIME);
            redisManager.setSerializeObject(userDetails.getUserId(), userDetails, sessionTimeout * 60 * 1000); // 毫秒级
            LoggerUtil.info(RedisUserDetailsService.class, "用户[{}]登陆成功，用户标志为：[{}]", new Object[]{userDetails.getUsername(), userDetails.getUserId()});
        }
        else
        {
            LoggerUtil.info(RedisUserDetailsService.class, "用户[{}]登陆失败，用户标志为：[{}]", new Object[]{userDetails.getUsername(), userDetails.getUserId()});
        }
    }

    @Override
    public void deleteUserDetails(String userId)
    {
        if (CommonUtil.isNotEmpty(userId))
        {
            UserDetails user = redisManager.getSerializeObject(userId, UserDetails.class, true);
            if (CommonUtil.isNotEmpty(user))
            {
                LoggerUtil.info(RedisUserDetailsService.class, "删除用户[{}]信息成功，用户[{}]退出", new Object[]{userId, user.getUsername()});
            }
            else
            {
                LoggerUtil.info(RedisUserDetailsService.class, "删除用户信息未成功，用户[{}]不存在", new Object[]{user.getUserId()});
            }
        }
    }

    @Override
    public void updateUserDetails(UserDetails userDetails)
    {
        if (CommonUtil.isNotEmpty(userDetails))
        {
            UserDetails user = getUserDetails(userDetails.getUserId());
            if (CommonUtil.isNotEmpty(user))
            {
                redisManager.setSerializeObject(userDetails.getUserId(), userDetails, RedisManager.SESSION_CACHE_TIME);
                LoggerUtil.info(RedisUserDetailsService.class, "登陆用户[{}]信息更新，用户ID为：{}", new Object[]{userDetails.getUsername(), userDetails.getUserId()});
            }
            else
            {
                LoggerUtil.info(RedisUserDetailsService.class, "用户[{}]信息未更新成功", new Object[]{userDetails.getUsername()});
            }
        }
    }

    //存储游戏端生成的key
    @Override
    public void saveGameKey(String key, Map keyMap){
        if (CommonUtil.isNotEmpty(keyMap) && CommonUtil.isNotEmpty(key))
        {
            redisManager.setValue(key, keyMap,  sessionTimeout * 60 * 1000); // 毫秒级
            LoggerUtil.info(RedisUserDetailsService.class, "游戏ID[{}]key存储成功，标志为：[{}]", new Object[]{keyMap.get("gameId"), key});
        }
        else
        {
            LoggerUtil.info(RedisUserDetailsService.class, "游戏ID[{}]key存储失败，标志为：[{}]", new Object[]{keyMap.get("gameId"), key});
        }
    }

    @Override
    public Map getGameToken(String gameToken){
       Map gameTokenMp= (Map) redisManager.getValue(gameToken);
       if(CommonUtil.isNotEmpty(gameTokenMp)){
           //生成新的gameToken进行替换原先gameToken
           String key = MD5CoderUtil.encode(gameTokenMp.get("gameId")+String.valueOf(System.nanoTime())+ SecurityCode.getSecurityCode());
           saveGameKey(key,gameTokenMp);
           //移除原来gameToken
           redisManager.deleteValue(gameToken);
           gameTokenMp.put("gameToken",key);
       }
        return gameTokenMp;

    }

}
