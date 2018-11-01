package com.yinhetianze.security.custom.userdetails.impl;

import com.yinhetianze.security.custom.userdetails.HttpUserDetails;

import java.util.Collection;
import java.util.Date;

import java.util.Map;

/**
 * 使用token令牌方式对应的用户登陆信息
 */
//public class TokenUser implements UserDetails
public class TokenUser implements HttpUserDetails
{

    /**
     * 登录用户id
     */
    private Integer id;

    /**
     * 绑定游戏id
     */
    private Integer gameId;
    /**
     * 登陆用户名
     */
    private final String username;

    /**
     * 登陆用户密码
     */
    private String password;

    /**
     * 用户token标志
     */
    private String token;

    /**
     * 用户拥有的权限
     */
    private Collection<String> authorizes;

    /**
     * 用户可访问的资源
     */
    private Collection<String> authorizedRes;

    /**
     * 创建时间
     */
    private final Date createTime;

    /**
     * 用户信息
     */
    private Map<String,Object> userInfo;

    /**
     * 过期时间
     */
    private Date expireTime;

    private final String ipAddress;

    private final String sessionId;

    public TokenUser(Integer id,String username, String password, String token, Collection<String> authorizes)
    {
        this(id,username, password, token, authorizes, null);
    }

    public TokenUser(Integer id,String username, String password, String token, Collection<String> authorizes,Map<String,Object> userInfo)
    {
        this(id,null,username, password, token, authorizes, null,userInfo);
    }

    public TokenUser(Integer id,Integer gameId,String username, String password, String token, Collection<String> authorizes,Map<String,Object> userInfo)
    {
        this(id,gameId,username, password, token, authorizes, null,userInfo);
    }

    public TokenUser(Integer id,Integer gameId,String username, String password, String token, Collection<String> authorizes, String ipAddress,Map<String,Object> userInfo)
    {
        this(id,gameId,username, password, token, authorizes, null, ipAddress,userInfo);
    }

    public TokenUser(Integer id,Integer gameId,String username, String password, String token, Collection<String> authorizes, Collection<String> authorizedRes, String ipAddress,Map<String,Object> userInfo)
    {
        this(id,gameId,username, password, token, authorizes, authorizedRes, ipAddress, null,userInfo);
    }

    public TokenUser(Integer id,Integer gameId,String username, String password, String token, Collection<String> authorizes, Collection<String> authorizedRes, String ipAddress, String sessionId
    ,Map<String,Object> userInfo)
    {
        this.id = id;
        this.gameId = gameId;
        this.username = username;
        this.password = password;
        this.token = token;
        this.authorizes = authorizes;
        this.authorizedRes = authorizedRes;
        this.ipAddress = ipAddress;
        this.sessionId = sessionId;
        this.createTime = new Date();
        this.userInfo = userInfo;
    }

    @Override
    public String getUserId()
    {
        return this.token;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public Collection<String> getAuthorizes()
    {
        return this.authorizes;
    }

    public Collection<String> getAuthorizedRes()
    {
        return this.authorizedRes;
    }

    public String getToken()
    {
        return this.token;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public String getIpAddress()
    {
        return ipAddress;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getUserInfo() {
        return userInfo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}
